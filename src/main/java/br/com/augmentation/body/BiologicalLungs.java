package br.com.augmentation.body;

import java.util.List;

import br.com.augmentation.api.IBloodDemand;
import br.com.augmentation.api.IBodyContext;
import br.com.augmentation.api.IOrgan;
import br.com.augmentation.api.IResourceDemand;
import br.com.augmentation.api.IResourceEfficiencySource;
import br.com.augmentation.api.IResourceType;
import br.com.augmentation.resource.ResourceDemand;
import br.com.augmentation.resource.ResourceType;

public final class BiologicalLungs implements IOrgan, IResourceEfficiencySource {
    private final PhysiologyProfile profile;
    private int integrity = 100;
    private int stability = 100;
    private int stress;
    private int wear;

    public BiologicalLungs() { this(PhysiologyProfile.defaultLungProfile()); }
    public BiologicalLungs(PhysiologyProfile profile) { this.profile = profile; }

    @Override public String getId() { return "lungs"; }
    @Override public int getIntegrity() { return integrity; }
    @Override public int getStability() { return stability; }
    @Override public int getStress() { return stress; }
    @Override public int getWear() { return wear; }
    @Override public void setIntegrity(int value) { integrity = clamp(value); }
    @Override public void setStability(int value) { stability = clamp(value); }
    @Override public void setStress(int value) { stress = Math.max(0, Math.min(profile.getMaximumStress(), value)); }
    @Override public void setWear(int value) { wear = clamp(value); }
    @Override public int getMaximumStress() { return profile.getMaximumStress(); }

    @Override
    public int getResourceEfficiencyPercent(IResourceType type, float environmentalPressure) {
        if (!ResourceType.OXYGEN.getId().equals(type.getId())) return 0;
        int pressure = PhysiologyModel.pressureUnits(environmentalPressure, profile);
        int condition = PhysiologyModel.conditionPercent(integrity, stability);
        return PhysiologyModel.extractionEfficiency(pressure, condition, profile);
    }

    @Override
    public void collectResourceDemands(IBodyContext context, List<IResourceDemand> demands) {
        ResourceDemandProfile demand = profile.getOxygenDemand();
        demands.add(new ResourceDemand(
                demand.getResourceType(),
                demand.getRequestedAmount(),
                demand.getPriority()));
    }

    @Override
    public void collectBloodDemands(IBodyContext context, List<IBloodDemand> demands) {
        // Lungs are the current oxygen producer for the internal blood medium.
    }

    @Override
    public void tick(IBodyContext context) {
        ResourceDemandProfile demand = profile.getOxygenDemand();
        int oxygen = context.requestResource(demand.getResourceType(), demand.getRequestedAmount());
        context.getBloodSystem().addOxygen(oxygen);

        int pressure = PhysiologyModel.pressureUnits(context.getPressure(), profile);
        int condition = PhysiologyModel.conditionPercent(integrity, stability);
        int processing = PhysiologyModel.oxygenProcessing(pressure, condition, profile);
        context.provideFunction("oxygen_processing", processing);
        context.provideFunction("oxygen_storage", context.getBloodSystem().getOxygen());
        boolean functionSatisfied = oxygen == demand.getRequestedAmount();
        stress = PhysiologyModel.nextStress(stress, pressure, functionSatisfied, profile);
        wear = PhysiologyModel.nextWear(wear, stress, profile);
        stability = PhysiologyModel.nextStability(stability, stress, functionSatisfied, profile);
        integrity = PhysiologyModel.nextIntegrity(integrity, wear, profile);
    }

    private static int clamp(int value) {
        return Math.max(0, Math.min(100, value));
    }
}

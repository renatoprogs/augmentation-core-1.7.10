package br.com.augmentation.body;

import br.com.augmentation.api.IBodyContext;
import br.com.augmentation.api.IOrgan;
import br.com.augmentation.api.IResourceEfficiencySource;
import br.com.augmentation.api.IResourceType;
import br.com.augmentation.resource.ResourceType;

public final class BiologicalLungs implements IOrgan, IResourceEfficiencySource {
    private final PhysiologyProfile profile;
    private int integrity = 100;
    private int stability = 100;
    private int stress;

    public BiologicalLungs() { this(PhysiologyProfile.defaultProfile()); }
    public BiologicalLungs(PhysiologyProfile profile) { this.profile = profile; }

    @Override public String getId() { return "lungs"; }
    @Override public int getIntegrity() { return integrity; }
    @Override public int getStability() { return stability; }
    @Override public int getStress() { return stress; }

    @Override
    public int getResourceEfficiencyPercent(IResourceType type, float environmentalPressure) {
        if (!ResourceType.OXYGEN.getId().equals(type.getId())) return 0;
        int pressure = PhysiologyModel.pressureUnits(environmentalPressure, profile);
        int condition = PhysiologyModel.conditionPercent(integrity, stability);
        return PhysiologyModel.extractionEfficiency(pressure, condition, profile);
    }

    @Override
    public void tick(IBodyContext context) {
        int pressure = PhysiologyModel.pressureUnits(context.getPressure(), profile);
        int condition = PhysiologyModel.conditionPercent(integrity, stability);
        int processing = PhysiologyModel.oxygenProcessing(pressure, condition, profile);
        context.provideFunction("oxygen_processing", processing);
        context.provideFunction("oxygen_storage", 50);
        stress = PhysiologyModel.nextStress(stress, pressure, profile);
    }
}

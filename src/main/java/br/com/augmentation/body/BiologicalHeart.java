package br.com.augmentation.body;

import java.util.List;

import br.com.augmentation.api.IBloodDemand;
import br.com.augmentation.api.IBodyContext;
import br.com.augmentation.api.IOrgan;
import br.com.augmentation.api.IResourceDemand;

public final class BiologicalHeart implements IOrgan {
    private final PhysiologyProfile profile;
    private int integrity = 100;
    private int stability = 100;
    private int stress;
    private int wear;

    public BiologicalHeart() {
        this(PhysiologyProfile.defaultHeartProfile());
    }

    public BiologicalHeart(PhysiologyProfile profile) {
        this.profile = profile;
    }

    @Override public String getId() { return "heart"; }
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
    public void collectResourceDemands(IBodyContext context, List<IResourceDemand> demands) {
        // Heart oxygen is supplied through the internal blood medium.
    }

    @Override
    public void collectBloodDemands(IBodyContext context, List<IBloodDemand> demands) {
        ResourceDemandProfile demand = profile.getOxygenDemand();
        demands.add(new BloodDemand(getId(), demand.getRequestedAmount(), demand.getPriority()));
    }

    @Override
    public void tick(IBodyContext context) {
        ResourceDemandProfile demand = profile.getOxygenDemand();
        int oxygen = context.getBloodOxygen(getId());
        int function = oxygen == demand.getRequestedAmount()
                ? profile.getFunctionAtFullCondition()
                : profile.getFunctionAtFailure();

        context.provideFunction("energy_distribution", function);

        boolean functionSatisfied = function == profile.getFunctionAtFullCondition();
        if (functionSatisfied) {
            stress = Math.max(0, stress - profile.getStressRecovery());
        } else {
            stress = Math.min(profile.getMaximumStress(), stress + profile.getStressIncrease());
        }
        wear = PhysiologyModel.nextWear(wear, stress, profile);
        stability = PhysiologyModel.nextStability(stability, stress, functionSatisfied, profile);
        integrity = PhysiologyModel.nextIntegrity(integrity, wear, profile);
    }

    private static int clamp(int value) {
        return Math.max(0, Math.min(100, value));
    }
}

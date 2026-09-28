package br.com.augmentation.body;

import java.util.List;

import br.com.augmentation.api.IBodyContext;
import br.com.augmentation.api.IOrgan;
import br.com.augmentation.api.IResourceDemand;
import br.com.augmentation.resource.ResourceDemand;

public final class BiologicalHeart implements IOrgan {
    private final PhysiologyProfile profile;
    private int integrity = 100;
    private int stability = 100;
    private int stress;

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
    @Override public int getMaximumStress() { return profile.getMaximumStress(); }

    @Override
    public void collectResourceDemands(IBodyContext context, List<IResourceDemand> demands) {
        ResourceDemandProfile demand = profile.getOxygenDemand();
        demands.add(new ResourceDemand(demand.getResourceType(),
                demand.getRequestedAmount(), demand.getPriority()));
    }

    @Override
    public void tick(IBodyContext context) {
        ResourceDemandProfile demand = profile.getOxygenDemand();
        int oxygen = context.requestResource(demand.getResourceType(), demand.getRequestedAmount());
        int function = oxygen == demand.getRequestedAmount()
                ? profile.getFunctionAtFullCondition()
                : profile.getFunctionAtFailure();

        context.provideFunction("energy_distribution", function);

        if (function == profile.getFunctionAtFailure()) {
            stress = Math.min(profile.getMaximumStress(), stress + profile.getStressIncrease());
        } else if (stress > 0) {
            stress = Math.max(0, stress - profile.getStressRecovery());
        }
    }
}

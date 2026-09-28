package br.com.augmentation.body;

import java.util.List;

import br.com.augmentation.api.IBodyContext;
import br.com.augmentation.api.IOrgan;
import br.com.augmentation.api.IResourceDemand;
import br.com.augmentation.resource.ResourceDemand;

public final class BiologicalBrain implements IOrgan {
    private final PhysiologyProfile profile;
    private int integrity = 100;
    private int stability = 100;
    private int stress;

    public BiologicalBrain() {
        this(PhysiologyProfile.defaultProfile());
    }

    public BiologicalBrain(PhysiologyProfile profile) {
        this.profile = profile;
    }

    @Override public String getId() { return "brain"; }
    @Override public int getIntegrity() { return integrity; }
    @Override public int getStability() { return stability; }
    @Override public int getStress() { return stress; }

    @Override
    public void collectResourceDemands(IBodyContext context, List<IResourceDemand> demands) {
        ResourceDemandProfile demand = profile.getOxygenDemand();
        demands.add(new ResourceDemand(demand.getResourceType(),
                demand.getRequestedAmount(), demand.getPriority()));
    }

    @Override
    public void tick(IBodyContext context) {
        int requested = profile.getOxygenDemand().getRequestedAmount();
        int oxygen = context.requestResource(profile.getOxygenDemand().getResourceType(), requested);
        int pressure = Math.round(context.getPressure() * profile.getPressureScale());
        int function = oxygen == requested && pressure >= profile.getStressPressureThreshold() ? 100 : 0;
        context.provideFunction("neural_processing", function);
        if (function == 0) stress = Math.min(profile.getMaximumStress(), stress + profile.getStressIncrease());
        else if (stress > 0) stress = Math.max(0, stress - profile.getStressRecovery());
    }
}

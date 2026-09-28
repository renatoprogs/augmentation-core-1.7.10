package br.com.augmentation.body;

import java.util.List;

import br.com.augmentation.api.IBodyContext;
import br.com.augmentation.api.IOrgan;
import br.com.augmentation.api.IResourceDemand;
import br.com.augmentation.resource.ResourceDemand;
import br.com.augmentation.resource.ResourceType;

public final class BiologicalHeart implements IOrgan {
    private final ResourceDemandProfile oxygenDemand =
            new ResourceDemandProfile(ResourceType.OXYGEN, 1, 90);
    private int integrity = 100;
    private int stability = 100;
    private int stress;

    @Override public String getId() { return "heart"; }
    @Override public int getIntegrity() { return integrity; }
    @Override public int getStability() { return stability; }
    @Override public int getStress() { return stress; }

    @Override
    public void collectResourceDemands(IBodyContext context, List<IResourceDemand> demands) {
        demands.add(new ResourceDemand(oxygenDemand.getResourceType(),
                oxygenDemand.getRequestedAmount(), oxygenDemand.getPriority()));
    }

    @Override
    public void tick(IBodyContext context) {
        int oxygen = context.requestResource(oxygenDemand.getResourceType(),
                oxygenDemand.getRequestedAmount());
        int function = oxygen == oxygenDemand.getRequestedAmount() ? 100 : 0;
        context.provideFunction("energy_distribution", function);
        if (function == 0) stress = Math.min(1000000, stress + 1);
        else if (stress > 0) stress--;
    }
}

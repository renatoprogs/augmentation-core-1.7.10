package br.com.augmentation.resource;

import br.com.augmentation.api.IEnvironmentResourceProvider;
import br.com.augmentation.api.IResourceType;
import br.com.augmentation.api.environment.IEnvironment;

public final class EnvironmentResourceProvider implements IEnvironmentResourceProvider {
    private final IResourceType type;
    private final int maximumPerTick;
    private int efficiencyPercent = 100;
    private int available;

    public EnvironmentResourceProvider(IResourceType type, int maximumPerTick) {
        this.type = type;
        this.maximumPerTick = Math.max(0, maximumPerTick);
    }

    public void setEfficiencyPercent(int efficiencyPercent) {
        this.efficiencyPercent = Math.max(0, Math.min(100, efficiencyPercent));
    }

    @Override
    public void update(IEnvironment environment) {
        int environmental = Math.max(0, environment.getResourceAvailable(type));
        int efficient = environmental * efficiencyPercent / 100;
        available = Math.min(maximumPerTick, efficient);
    }

    @Override public IResourceType getResourceType() { return type; }
    @Override public int getPriority() { return 0; }
    @Override public int getProductionPerTick() { return available; }
}

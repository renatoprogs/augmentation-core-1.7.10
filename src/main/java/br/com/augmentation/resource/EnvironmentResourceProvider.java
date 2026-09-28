package br.com.augmentation.resource;

import br.com.augmentation.api.IEnvironmentResourceProvider;
import br.com.augmentation.api.IResourceType;
import br.com.augmentation.api.environment.IEnvironment;

public final class EnvironmentResourceProvider implements IEnvironmentResourceProvider {
    private final IResourceType type;
    private final int maximumPerTick;
    private int available;

    public EnvironmentResourceProvider(IResourceType type, int maximumPerTick) {
        this.type = type;
        this.maximumPerTick = Math.max(0, maximumPerTick);
    }

    @Override
    public void update(IEnvironment environment) {
        available = Math.min(maximumPerTick, environment.getResourceAvailable(type));
    }

    @Override public IResourceType getResourceType() { return type; }
    @Override public int getPriority() { return 0; }

    @Override
    public int getProductionPerTick() {
        return available;
    }
}
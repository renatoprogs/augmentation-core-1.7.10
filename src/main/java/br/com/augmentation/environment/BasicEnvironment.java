package br.com.augmentation.environment;

import br.com.augmentation.api.IResourceType;
import br.com.augmentation.api.environment.IEnvironment;
import br.com.augmentation.resource.ResourceType;

/** Environment adapter used by the core. Forge/world knowledge stays outside Body. */
public final class BasicEnvironment implements IEnvironment {
    private final Atmosphere atmosphere;

    public BasicEnvironment(Atmosphere atmosphere) {
        this.atmosphere = atmosphere;
    }

    @Override
    public int getResourceAvailable(IResourceType type) {
        return ResourceType.OXYGEN.getId().equals(type.getId())
                ? atmosphere.getOxygenPerTick() : 0;
    }

    @Override
    public int extractResource(IResourceType type, int amount) {
        return ResourceType.OXYGEN.getId().equals(type.getId())
                ? Math.min(Math.max(amount, 0), atmosphere.getOxygenPerTick()) : 0;
    }

    @Override public float getPressure() { return atmosphere.getPressure(); }
    @Override public float getTemperature() { return atmosphere.getTemperature(); }
}
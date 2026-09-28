package br.com.augmentation.api.environment;

import br.com.augmentation.api.IResourceType;

/** External world conditions visible to the body/resource system. */
public interface IEnvironment {
    int getResourceAvailable(IResourceType type);
    int extractResource(IResourceType type, int amount);
    float getPressure();
    float getTemperature();
}
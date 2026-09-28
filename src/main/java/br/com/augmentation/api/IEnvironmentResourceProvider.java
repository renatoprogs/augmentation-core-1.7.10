package br.com.augmentation.api;

import br.com.augmentation.api.environment.IEnvironment;

public interface IEnvironmentResourceProvider extends IResourceProvider {
    void update(IEnvironment environment);
}
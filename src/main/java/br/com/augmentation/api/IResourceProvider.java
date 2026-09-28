package br.com.augmentation.api;

public interface IResourceProvider {
    IResourceType getResourceType();
    int getProductionPerTick();
}
package br.com.augmentation.api;

public interface IResourceConsumer {
    IResourceType getResourceType();
    int getConsumptionPerTick();
}
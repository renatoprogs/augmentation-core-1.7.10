package br.com.augmentation.api;

public interface IResourceNetwork {
    void addProvider(IResourceProvider provider);
    void addConsumer(IResourceConsumer consumer);
    int request(IResourceType type, int amount, int priority);
    void produce(IResourceType type, int amount);
}
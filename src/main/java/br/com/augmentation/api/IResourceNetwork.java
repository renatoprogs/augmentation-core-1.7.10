package br.com.augmentation.api;

import java.util.List;

public interface IResourceNetwork {
    void addProvider(IResourceProvider provider);
    void addConsumer(IResourceConsumer consumer);
    int request(IResourceType type, int amount, int priority);
    void allocate(List<IResourceDemand> demands);
    void produce(IResourceType type, int amount);
}

package br.com.augmentation.api;

public interface IResourceDemand {
    IResourceType getResourceType();
    int getRequestedAmount();
    int getPriority();
    void setProvidedAmount(int amount);
    int getProvidedAmount();
    int getDeficit();
}

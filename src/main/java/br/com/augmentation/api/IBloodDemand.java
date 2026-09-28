package br.com.augmentation.api;

public interface IBloodDemand {
    String getConsumerId();
    int getOxygenAmount();
    int getPriority();
    int getProvidedOxygen();
    void setProvidedOxygen(int amount);
}

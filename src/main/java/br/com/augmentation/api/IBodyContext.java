package br.com.augmentation.api;

public interface IBodyContext {
    void provideFunction(String function, int strength);
    int requestResource(IResourceType type, int amount);
    int getResource(IResourceType type);
    IBloodSystem getBloodSystem();
    float getPressure();
    float getTemperature();
}

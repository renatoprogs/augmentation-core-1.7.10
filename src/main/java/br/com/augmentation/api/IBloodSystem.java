package br.com.augmentation.api;

public interface IBloodSystem {
    int addOxygen(int amount);
    int consumeOxygen(int amount);
    int getOxygen();
    int getOxygenCapacity();
}

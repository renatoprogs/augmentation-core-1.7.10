package br.com.augmentation.api;

import java.util.List;

public interface IBloodSystem {
    int addOxygen(int amount);
    int consumeOxygen(int amount);
    int getOxygen();
    int getOxygenCapacity();
    void allocateOxygen(List<IBloodDemand> demands);
}

package br.com.augmentation.body;

import br.com.augmentation.api.IBloodSystem;

public final class BloodSystem implements IBloodSystem {
    private final int oxygenCapacity;
    private int oxygen;

    public BloodSystem(int oxygenCapacity) {
        this.oxygenCapacity = Math.max(0, oxygenCapacity);
    }

    @Override
    public int addOxygen(int amount) {
        int accepted = Math.min(Math.max(0, amount), oxygenCapacity - oxygen);
        oxygen += accepted;
        return accepted;
    }

    @Override
    public int consumeOxygen(int amount) {
        int consumed = Math.min(Math.max(0, amount), oxygen);
        oxygen -= consumed;
        return consumed;
    }

    @Override public int getOxygen() { return oxygen; }
    @Override public int getOxygenCapacity() { return oxygenCapacity; }
}

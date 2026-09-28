package br.com.augmentation.body;

import br.com.augmentation.api.IBloodDemand;

public final class BloodDemand implements IBloodDemand {
    private final String consumerId;
    private final int oxygenAmount;
    private final int priority;
    private int providedOxygen;

    public BloodDemand(String consumerId, int oxygenAmount, int priority) {
        this.consumerId = consumerId;
        this.oxygenAmount = Math.max(0, oxygenAmount);
        this.priority = priority;
    }

    @Override public String getConsumerId() { return consumerId; }
    @Override public int getOxygenAmount() { return oxygenAmount; }
    @Override public int getPriority() { return priority; }
    @Override public int getProvidedOxygen() { return providedOxygen; }

    @Override
    public void setProvidedOxygen(int amount) {
        providedOxygen = Math.max(0, Math.min(amount, oxygenAmount));
    }
}

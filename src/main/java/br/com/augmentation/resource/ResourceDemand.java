package br.com.augmentation.resource;

import br.com.augmentation.api.IResourceDemand;
import br.com.augmentation.api.IResourceType;

public final class ResourceDemand implements IResourceDemand {
    private final IResourceType type;
    private final int requestedAmount;
    private final int priority;
    private int providedAmount;

    public ResourceDemand(IResourceType type, int requestedAmount, int priority) {
        this.type = type;
        this.requestedAmount = Math.max(0, requestedAmount);
        this.priority = priority;
    }

    @Override public IResourceType getResourceType() { return type; }
    @Override public int getRequestedAmount() { return requestedAmount; }
    @Override public int getPriority() { return priority; }

    @Override
    public void setProvidedAmount(int amount) {
        providedAmount = Math.max(0, Math.min(requestedAmount, amount));
    }

    @Override public int getProvidedAmount() { return providedAmount; }
    @Override public int getDeficit() { return requestedAmount - providedAmount; }
}

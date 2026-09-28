package br.com.augmentation.body;

import br.com.augmentation.api.IResourceType;

public final class ResourceDemandProfile {
    private final IResourceType resourceType;
    private final int requestedAmount;
    private final int priority;

    public ResourceDemandProfile(IResourceType resourceType, int requestedAmount, int priority) {
        this.resourceType = resourceType;
        this.requestedAmount = Math.max(0, requestedAmount);
        this.priority = priority;
    }

    public IResourceType getResourceType() { return resourceType; }
    public int getRequestedAmount() { return requestedAmount; }
    public int getPriority() { return priority; }
}

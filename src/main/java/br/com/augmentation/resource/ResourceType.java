package br.com.augmentation.resource;
import br.com.augmentation.api.IResourceType;
public final class ResourceType implements IResourceType {
    public static final ResourceType ENERGY = new ResourceType("energy");
    public static final ResourceType OXYGEN = new ResourceType("oxygen");
    private final String id;
    public ResourceType(String id) { this.id = id; }
    @Override public String getId() { return id; }
    @Override public String toString() { return id; }
}
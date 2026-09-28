package br.com.augmentation.resource;
import br.com.augmentation.api.IResourceStorage;
import br.com.augmentation.api.IResourceType;
public final class ResourceStorage implements IResourceStorage {
    private final IResourceType type;
    private final int capacity;
    private int amount;
    public ResourceStorage(IResourceType type, int capacity, int initialAmount) {
        if (capacity < 0) throw new IllegalArgumentException("capacity < 0");
        this.type = type;
        this.capacity = capacity;
        this.amount = clamp(initialAmount, 0, capacity);
    }
    @Override public IResourceType getType() { return type; }
    @Override public int getAmount() { return amount; }
    @Override public int getCapacity() { return capacity; }
    @Override public int insert(int requested) {
        if (requested <= 0) return 0;
        int accepted = Math.min(requested, capacity - amount);
        amount += accepted;
        return accepted;
    }
    @Override public int extract(int requested) {
        if (requested <= 0) return 0;
        int extracted = Math.min(requested, amount);
        amount -= extracted;
        return extracted;
    }
    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
}
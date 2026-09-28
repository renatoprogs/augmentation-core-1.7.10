package br.com.augmentation.resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.augmentation.api.IResourceConsumer;
import br.com.augmentation.api.IResourceDemand;
import br.com.augmentation.api.IResourceNetwork;
import br.com.augmentation.api.IResourceProvider;
import br.com.augmentation.api.IResourceStorage;
import br.com.augmentation.api.IResourceType;

public final class ResourceNetwork implements IResourceNetwork {
    private static final class Bucket {
        final IResourceStorage storage;
        Bucket(IResourceStorage storage) { this.storage = storage; }
    }

    private final List<IResourceProvider> providers = new ArrayList<IResourceProvider>();
    private final List<IResourceConsumer> consumers = new ArrayList<IResourceConsumer>();
    private final List<Bucket> storages = new ArrayList<Bucket>();

    public void addStorage(IResourceStorage storage) {
        storages.add(new Bucket(storage));
    }

    @Override public void addProvider(IResourceProvider provider) { providers.add(provider); }
    @Override public void addConsumer(IResourceConsumer consumer) { consumers.add(consumer); }

    public void tick() {
        for (IResourceProvider provider : providers) {
            int amount = provider.getProductionPerTick();
            if (amount > 0) produce(provider.getResourceType(), amount);
        }
    }

    @Override
    public void produce(IResourceType type, int amount) {
        if (amount <= 0) return;
        int remaining = amount;
        for (Bucket bucket : storages) {
            if (!bucket.storage.getType().getId().equals(type.getId())) continue;
            remaining -= bucket.storage.insert(remaining);
            if (remaining == 0) break;
        }
    }

    @Override
    public int request(IResourceType type, int amount, int priority) {
        if (amount <= 0) return 0;
        int remaining = amount;
        int extracted = 0;
        for (Bucket bucket : storages) {
            if (!bucket.storage.getType().getId().equals(type.getId())) continue;
            int got = bucket.storage.extract(remaining);
            extracted += got;
            remaining -= got;
            if (remaining == 0) break;
        }
        return extracted;
    }

    @Override
    public void allocate(List<IResourceDemand> demands) {
        if (demands == null || demands.isEmpty()) return;

        List<IResourceDemand> ordered = new ArrayList<IResourceDemand>(demands);
        Collections.sort(ordered, new Comparator<IResourceDemand>() {
            @Override public int compare(IResourceDemand a, IResourceDemand b) {
                return b.getPriority() - a.getPriority();
            }
        });

        for (IResourceDemand demand : ordered) {
            int provided = request(demand.getResourceType(),
                    demand.getRequestedAmount(), demand.getPriority());
            demand.setProvidedAmount(provided);
        }
    }

    public List<IResourceConsumer> getConsumersByPriority() {
        List<IResourceConsumer> result = new ArrayList<IResourceConsumer>(consumers);
        Collections.sort(result, new Comparator<IResourceConsumer>() {
            @Override public int compare(IResourceConsumer a, IResourceConsumer b) {
                return b.getPriority() - a.getPriority();
            }
        });
        return result;
    }
}

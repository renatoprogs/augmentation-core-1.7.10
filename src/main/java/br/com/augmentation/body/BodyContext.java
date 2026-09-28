package br.com.augmentation.body;

import java.util.HashMap;
import java.util.Map;

import br.com.augmentation.api.IBodyContext;
import br.com.augmentation.api.IBloodSystem;
import br.com.augmentation.api.IResourceDemand;
import br.com.augmentation.api.IResourceNetwork;
import br.com.augmentation.api.IResourceStorage;
import br.com.augmentation.api.IResourceType;
import br.com.augmentation.api.environment.IEnvironment;

public final class BodyContext implements IBodyContext {
    private final Map<String, Integer> functions = new HashMap<String, Integer>();
    private final Map<String, IResourceStorage> resources = new HashMap<String, IResourceStorage>();
    private final Map<String, Integer> allocated = new HashMap<String, Integer>();
    private final IEnvironment environment;
    private final IResourceNetwork network;
    private final IBloodSystem bloodSystem;

    public BodyContext(IEnvironment environment, IResourceNetwork network, IBloodSystem bloodSystem) {
        this.environment = environment;
        this.network = network;
        this.bloodSystem = bloodSystem;
    }

    public void addStorage(IResourceStorage storage) {
        resources.put(storage.getType().getId(), storage);
    }

    public void applyDemand(IResourceDemand demand) {
        String id = demand.getResourceType().getId();
        Integer old = allocated.get(id);
        int current = old == null ? 0 : old.intValue();
        allocated.put(id, Integer.valueOf(current + demand.getProvidedAmount()));
    }

    @Override public void provideFunction(String function, int strength) {
        Integer old = functions.get(function);
        if (old == null) old = Integer.valueOf(0);
        functions.put(function, Integer.valueOf(old.intValue() + strength));
    }

    @Override public int requestResource(IResourceType type, int amount) {
        Integer available = allocated.get(type.getId());
        if (available == null) return 0;
        int used = Math.min(Math.max(0, amount), available.intValue());
        allocated.put(type.getId(), Integer.valueOf(available.intValue() - used));
        return used;
    }

    @Override public int getResource(IResourceType type) {
        IResourceStorage storage = resources.get(type.getId());
        return storage == null ? 0 : storage.getAmount();
    }

    @Override public IBloodSystem getBloodSystem() { return bloodSystem; }

    @Override public float getPressure() { return environment.getPressure(); }
    @Override public float getTemperature() { return environment.getTemperature(); }

    public int getFunctionStrength(String function) {
        Integer value = functions.get(function);
        return value == null ? 0 : value.intValue();
    }
}

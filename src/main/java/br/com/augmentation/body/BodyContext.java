package br.com.augmentation.body;
import java.util.HashMap;
import java.util.Map;
import br.com.augmentation.api.IBodyContext;
import br.com.augmentation.api.IResourceStorage;
import br.com.augmentation.api.IResourceType;
import br.com.augmentation.api.environment.IEnvironment;
public final class BodyContext implements IBodyContext {
    private final Map<String, Integer> functions = new HashMap<String, Integer>();
    private final Map<String, IResourceStorage> resources = new HashMap<String, IResourceStorage>();
    private final IEnvironment environment;
    public BodyContext(IEnvironment environment) { this.environment = environment; }
    public void addStorage(IResourceStorage storage) { resources.put(storage.getType().getId(), storage); }
    @Override public void provideFunction(String function, int strength) {
        Integer old = functions.get(function);
        if (old == null) old = Integer.valueOf(0);
        functions.put(function, Integer.valueOf(old.intValue() + strength));
    }
    @Override public int requestResource(IResourceType type, int amount) {
        IResourceStorage storage = resources.get(type.getId());
        return storage == null ? 0 : storage.extract(amount);
    }
    @Override public int getResource(IResourceType type) {
        IResourceStorage storage = resources.get(type.getId());
        return storage == null ? 0 : storage.getAmount();
    }
    @Override public float getPressure() { return environment.getPressure(); }
    @Override public float getTemperature() { return environment.getTemperature(); }
    public int getFunctionStrength(String function) {
        Integer value = functions.get(function);
        return value == null ? 0 : value.intValue();
    }
}
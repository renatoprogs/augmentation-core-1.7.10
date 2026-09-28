package br.com.augmentation.body;

import java.util.HashMap;
import java.util.Map;
import br.com.augmentation.api.IBody;
import br.com.augmentation.api.IOrgan;
import br.com.augmentation.api.environment.IEnvironment;
import br.com.augmentation.resource.ResourceStorage;
import br.com.augmentation.resource.ResourceType;
import net.minecraft.nbt.NBTTagCompound;

public final class Body implements IBody {
    private final Map<String, IOrgan> organs = new HashMap<String, IOrgan>();
    private final ResourceStorage oxygen = new ResourceStorage(ResourceType.OXYGEN, 100, 100);
    private final ResourceStorage energy = new ResourceStorage(ResourceType.ENERGY, 1000, 1000);
    private int tickCount;
    public Body() {
        installOrgan(new BiologicalBrain());
        installOrgan(new BiologicalHeart());
        installOrgan(new BiologicalLungs());
    }
    @Override public IOrgan getOrgan(String id) { return organs.get(id); }
    @Override public void installOrgan(IOrgan organ) { organs.put(organ.getId(), organ); }
    @Override public void removeOrgan(String id) { organs.remove(id); }
    @Override public void tick(IEnvironment environment) {
        BodyContext context = new BodyContext(environment);
        context.addStorage(oxygen);
        context.addStorage(energy);
        int atmosphericOxygen = environment.extractResource(ResourceType.OXYGEN, 2);
        oxygen.insert(atmosphericOxygen);
        for (IOrgan organ : organs.values()) organ.tick(context);
        tickCount++;
    }
    public int getTickCount() { return tickCount; }
    public int getOxygen() { return oxygen.getAmount(); }
    public int getEnergy() { return energy.getAmount(); }
    @Override public void writeToNBT(NBTTagCompound nbt) {
        nbt.setInteger("version", 2);
        nbt.setInteger("tick_count", tickCount);
        nbt.setInteger("oxygen", oxygen.getAmount());
        nbt.setInteger("energy", energy.getAmount());
    }
    @Override public void readFromNBT(NBTTagCompound nbt) {
        tickCount = nbt.getInteger("tick_count");
        oxygen.extract(oxygen.getAmount());
        oxygen.insert(nbt.getInteger("oxygen"));
        energy.extract(energy.getAmount());
        energy.insert(nbt.getInteger("energy"));
    }
}
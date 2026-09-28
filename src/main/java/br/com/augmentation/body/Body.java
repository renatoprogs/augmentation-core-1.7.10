package br.com.augmentation.body;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import br.com.augmentation.api.IBloodDemand;
import br.com.augmentation.api.IBody;
import br.com.augmentation.api.IOrgan;
import br.com.augmentation.api.IOrganStatePersistence;
import br.com.augmentation.api.IResourceEfficiencySource;
import br.com.augmentation.api.IResourceDemand;
import br.com.augmentation.api.environment.IEnvironment;
import br.com.augmentation.resource.EnvironmentResourceProvider;
import br.com.augmentation.resource.ResourceNetwork;
import br.com.augmentation.resource.ResourceStorage;
import br.com.augmentation.resource.ResourceType;
import br.com.augmentation.forge.v1_7_10.nbt.OrganStateNBTAdapter;
import net.minecraft.nbt.NBTTagCompound;

public final class Body implements IBody {
    private final Map<String, IOrgan> organs = new LinkedHashMap<String, IOrgan>();
    private final ResourceStorage oxygen = new ResourceStorage(ResourceType.OXYGEN, 100, 100);
    private final ResourceStorage energy = new ResourceStorage(ResourceType.ENERGY, 1000, 1000);
    private final BloodSystem blood = new BloodSystem(20);
    private final EnvironmentResourceProvider oxygenProvider = new EnvironmentResourceProvider(ResourceType.OXYGEN, 2);
    private final IOrganStatePersistence organStatePersistence = new OrganStateNBTAdapter();
    private int tickCount;
    private CollapseState collapseState = CollapseState.NORMAL;

    public Body() {
        installOrgan(new BiologicalBrain());
        installOrgan(new BiologicalHeart());
        installOrgan(new BiologicalLungs());
    }

    @Override public IOrgan getOrgan(String id) { return organs.get(id); }
    @Override public void installOrgan(IOrgan organ) { organs.put(organ.getId(), organ); }
    @Override public void removeOrgan(String id) { organs.remove(id); }

    @Override
    public void tick(IEnvironment environment) {
        ResourceNetwork network = new ResourceNetwork();
        network.addStorage(oxygen);
        network.addStorage(energy);

        IOrgan lungs = organs.get("lungs");
        if (lungs instanceof IResourceEfficiencySource) {
            IResourceEfficiencySource source = (IResourceEfficiencySource) lungs;
            oxygenProvider.setEfficiencyPercent(
                    source.getResourceEfficiencyPercent(ResourceType.OXYGEN, environment.getPressure()));
        } else {
            oxygenProvider.setEfficiencyPercent(100);
        }

        oxygenProvider.update(environment);
        network.addProvider(oxygenProvider);
        network.tick();

        BodyContext context = new BodyContext(environment, network, blood);
        context.addStorage(oxygen);
        context.addStorage(energy);

        List<IResourceDemand> demands = new ArrayList<IResourceDemand>();
        for (IOrgan organ : organs.values()) {
            organ.collectResourceDemands(context, demands);
        }

        network.allocate(demands);
        for (IResourceDemand demand : demands) {
            context.applyDemand(demand);
        }

        // Phase 1: external resource conversion into the internal blood medium.
        if (lungs != null) {
            lungs.tick(context);
        }

        // Phase 2: allocate the finite internal oxygen medium by priority.
        List<IBloodDemand> bloodDemands = new ArrayList<IBloodDemand>();
        for (IOrgan organ : organs.values()) {
            organ.collectBloodDemands(context, bloodDemands);
        }

        blood.allocateOxygen(bloodDemands);
        for (IBloodDemand demand : bloodDemands) {
            context.applyBloodDemand(demand);
        }

        // Phase 3: consume allocated blood oxygen and update organ physiology.
        for (IOrgan organ : organs.values()) {
            if (organ != lungs) {
                organ.tick(context);
            }
        }

        collapseState = evaluateSystemicCollapse();
        tickCount++;
    }

    private CollapseState evaluateSystemicCollapse() {
        CollapseState result = CollapseState.NORMAL;

        for (IOrgan organ : organs.values()) {
            CollapseState state = CollapseModel.evaluate(
                    organ.getStress(),
                    organ.getStability(),
                    organ.getIntegrity(),
                    organ.getMaximumStress());

            if (state.ordinal() > result.ordinal()) {
                result = state;
            }
        }

        return result;
    }

    public int getTickCount() { return tickCount; }
    public int getOxygen() { return oxygen.getAmount(); }
    public int getEnergy() { return energy.getAmount(); }
    public int getBloodOxygen() { return blood.getOxygen(); }
    public CollapseState getCollapseState() { return collapseState; }

    @Override public void writeToNBT(NBTTagCompound nbt) {
        nbt.setInteger("version", 10);
        nbt.setInteger("tick_count", tickCount);
        nbt.setInteger("oxygen", oxygen.getAmount());
        nbt.setInteger("energy", energy.getAmount());
        nbt.setInteger("blood_oxygen", blood.getOxygen());
        nbt.setInteger("collapse_state", collapseState.ordinal());

        NBTTagCompound organsNbt = new NBTTagCompound();
        for (Map.Entry<String, IOrgan> entry : organs.entrySet()) {
            NBTTagCompound organNbt = new NBTTagCompound();
            organStatePersistence.writeState(entry.getValue(), organNbt);
            organsNbt.setTag(entry.getKey(), organNbt);
        }
        nbt.setTag("organs", organsNbt);
    }

    @Override public void readFromNBT(NBTTagCompound nbt) {
        tickCount = nbt.getInteger("tick_count");
        oxygen.extract(oxygen.getAmount());
        oxygen.insert(nbt.getInteger("oxygen"));
        energy.extract(energy.getAmount());
        energy.insert(nbt.getInteger("energy"));
        blood.consumeOxygen(blood.getOxygen());
        blood.addOxygen(nbt.getInteger("blood_oxygen"));

        if (nbt.hasKey("organs", 10)) {
            NBTTagCompound organsNbt = nbt.getCompoundTag("organs");
            for (Map.Entry<String, IOrgan> entry : organs.entrySet()) {
                if (organsNbt.hasKey(entry.getKey(), 10)) {
                    organStatePersistence.readState(entry.getValue(), organsNbt.getCompoundTag(entry.getKey()));
                }
            }
        }

        int state = nbt.getInteger("collapse_state");
        CollapseState[] states = CollapseState.values();
        collapseState = state >= 0 && state < states.length
                ? states[state]
                : CollapseState.NORMAL;
    }

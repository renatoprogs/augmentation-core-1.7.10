package br.com.augmentation.body;

import java.util.List;

import net.minecraft.nbt.NBTTagCompound;

import br.com.augmentation.api.IBloodDemand;
import br.com.augmentation.api.IBodyContext;
import br.com.augmentation.api.IOrgan;
import br.com.augmentation.api.IResourceDemand;

public final class BiologicalBrain implements IOrgan {
    private final PhysiologyProfile profile;
    private int integrity = 100;
    private int stability = 100;
    private int stress;
    private int wear;

    public BiologicalBrain() {
        this(PhysiologyProfile.defaultProfile());
    }

    public BiologicalBrain(PhysiologyProfile profile) {
        this.profile = profile;
    }

    @Override public String getId() { return "brain"; }
    @Override public int getIntegrity() { return integrity; }
    @Override public int getStability() { return stability; }
    @Override public int getStress() { return stress; }
    @Override public int getWear() { return wear; }
    @Override public int getMaximumStress() { return profile.getMaximumStress(); }

    @Override
    public void collectResourceDemands(IBodyContext context, List<IResourceDemand> demands) {
        // Brain oxygen is supplied through the internal blood medium.
    }

    @Override
    public void collectBloodDemands(IBodyContext context, List<IBloodDemand> demands) {
        ResourceDemandProfile demand = profile.getOxygenDemand();
        demands.add(new BloodDemand(getId(), demand.getRequestedAmount(), demand.getPriority()));
    }

    @Override
    public void tick(IBodyContext context) {
        ResourceDemandProfile demand = profile.getOxygenDemand();
        int oxygen = context.getBloodOxygen(getId());
        int pressure = Math.round(context.getPressure() * profile.getPressureScale());
        int function = oxygen == demand.getRequestedAmount()
                && pressure >= profile.getStressPressureThreshold()
                ? profile.getFunctionAtFullCondition()
                : profile.getFunctionAtFailure();

        context.provideFunction("neural_processing", function);

        boolean functionSatisfied = function == profile.getFunctionAtFullCondition();
        if (functionSatisfied) {
            stress = Math.max(0, stress - profile.getStressRecovery());
        } else {
            stress = Math.min(profile.getMaximumStress(), stress + profile.getStressIncrease());
        }
        wear = PhysiologyModel.nextWear(wear, stress, profile);
        stability = PhysiologyModel.nextStability(stability, stress, functionSatisfied, profile);
        integrity = PhysiologyModel.nextIntegrity(integrity, wear, profile);
    }
    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        nbt.setInteger("integrity", integrity);
        nbt.setInteger("stability", stability);
        nbt.setInteger("stress", stress);
        nbt.setInteger("wear", wear);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        integrity = Math.max(0, Math.min(100, nbt.getInteger("integrity")));
        stability = Math.max(0, Math.min(100, nbt.getInteger("stability")));
        stress = Math.max(0, Math.min(profile.getMaximumStress(), nbt.getInteger("stress")));
        wear = Math.max(0, Math.min(100, nbt.getInteger("wear")));
    }

}

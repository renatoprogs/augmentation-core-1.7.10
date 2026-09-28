package br.com.augmentation.forge.v1_7_10.nbt;

import net.minecraft.nbt.NBTTagCompound;
import br.com.augmentation.api.IOrganStatePersistence;
import br.com.augmentation.api.IPhysiologyState;

public final class OrganStateNBTAdapter implements IOrganStatePersistence {
    @Override
    public void writeState(IPhysiologyState state, NBTTagCompound nbt) {
        nbt.setInteger("integrity", state.getIntegrity());
        nbt.setInteger("stability", state.getStability());
        nbt.setInteger("stress", state.getStress());
        nbt.setInteger("wear", state.getWear());
    }

    @Override
    public void readState(IPhysiologyState state, NBTTagCompound nbt) {
        state.setIntegrity(clamp(nbt.getInteger("integrity"), 0, 100));
        state.setStability(clamp(nbt.getInteger("stability"), 0, 100));
        state.setStress(Math.max(0, nbt.getInteger("stress")));
        state.setWear(clamp(nbt.getInteger("wear"), 0, 100));
    }

    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
}

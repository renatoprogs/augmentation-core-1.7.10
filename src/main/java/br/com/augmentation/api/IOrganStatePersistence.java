package br.com.augmentation.api;

public interface IOrganStatePersistence {
    void writeState(IPhysiologyState state, net.minecraft.nbt.NBTTagCompound nbt);
    void readState(IPhysiologyState state, net.minecraft.nbt.NBTTagCompound nbt);
}

package br.com.augmentation.api;

import net.minecraft.nbt.NBTTagCompound;
import br.com.augmentation.api.environment.IEnvironment;

public interface IBody {
    IOrgan getOrgan(String id);
    void installOrgan(IOrgan organ);
    void removeOrgan(String id);
    void tick(IEnvironment environment);
    void writeToNBT(NBTTagCompound nbt);
    void readFromNBT(NBTTagCompound nbt);
}
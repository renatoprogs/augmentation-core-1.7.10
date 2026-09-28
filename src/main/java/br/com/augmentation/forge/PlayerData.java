package br.com.augmentation.forge;
import br.com.augmentation.body.Body;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
public final class PlayerData implements IExtendedEntityProperties {
    public static final String KEY = "augmentation_body";
    private final Body body = new Body();
    public Body getBody() { return body; }
    @Override public void init(Entity entity, World world) { }
    @Override public void saveNBTData(NBTTagCompound compound) {
        NBTTagCompound data = new NBTTagCompound();
        body.writeToNBT(data);
        compound.setTag(KEY, data);
    }
    @Override public void loadNBTData(NBTTagCompound compound) {
        if (compound.hasKey(KEY)) body.readFromNBT(compound.getCompoundTag(KEY));
    }
    public void copyFrom(PlayerData other) {
        NBTTagCompound data = new NBTTagCompound();
        other.body.writeToNBT(data);
        body.readFromNBT(data);
    }
}
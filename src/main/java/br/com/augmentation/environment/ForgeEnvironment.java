package br.com.augmentation.environment;

import br.com.augmentation.api.IResourceType;
import br.com.augmentation.api.environment.IEnvironment;
import br.com.augmentation.resource.ResourceType;
import net.minecraft.entity.player.EntityPlayer;

public final class ForgeEnvironment implements IEnvironment {
    private final Atmosphere atmosphere;

    public ForgeEnvironment(EntityPlayer player) {
        this.atmosphere = player.worldObj.provider.dimensionId == 0
                ? Atmosphere.normal() : Atmosphere.vacuum();
    }

    @Override public int getResourceAvailable(IResourceType type) {
        return ResourceType.OXYGEN.getId().equals(type.getId())
                ? atmosphere.getOxygenPerTick() : 0;
    }

    @Override public int extractResource(IResourceType type, int amount) {
        return ResourceType.OXYGEN.getId().equals(type.getId())
                ? Math.min(Math.max(amount, 0), atmosphere.getOxygenPerTick()) : 0;
    }

    @Override public float getPressure() { return atmosphere.getPressure(); }
    @Override public float getTemperature() { return atmosphere.getTemperature(); }
}
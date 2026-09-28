package br.com.augmentation.environment;

import br.com.augmentation.api.IResourceType;
import br.com.augmentation.api.environment.IEnvironment;
import br.com.augmentation.resource.ResourceType;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Minimal Forge 1.7.10 bridge. Dimension mapping is deliberately conservative:
 * overworld gets normal air; other dimensions currently default to vacuum until
 * a real atmosphere adapter is supplied.
 */
public final class ForgeEnvironment implements IEnvironment {
    private final Atmosphere atmosphere;

    public ForgeEnvironment(EntityPlayer player) {
        this.atmosphere = player.worldObj.provider.dimensionId == 0
                ? Atmosphere.normal() : Atmosphere.vacuum();
    }

    @Override public int getResourceAvailable(IResourceType type) {
        return new BasicEnvironment(atmosphere).getResourceAvailable(type);
    }
    @Override public int extractResource(IResourceType type, int amount) {
        return new BasicEnvironment(atmosphere).extractResource(type, amount);
    }
    @Override public float getPressure() { return atmosphere.getPressure(); }
    @Override public float getTemperature() { return atmosphere.getTemperature(); }
}
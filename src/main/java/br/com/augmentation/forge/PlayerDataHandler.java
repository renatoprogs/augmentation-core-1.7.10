package br.com.augmentation.forge;
import br.com.augmentation.environment.ForgeEnvironment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
public final class PlayerDataHandler {
    public static void register() { MinecraftForge.EVENT_BUS.register(new PlayerDataHandler()); }
    @SubscribeEvent public void onConstructing(EntityEvent.EntityConstructing event) {
        if (!(event.entity instanceof EntityPlayer)) return;
        EntityPlayer player = (EntityPlayer) event.entity;
        if (player.getExtendedProperties(PlayerData.KEY) == null)
            player.registerExtendedProperties(PlayerData.KEY, new PlayerData());
    }
    @SubscribeEvent public void onClone(PlayerEvent.Clone event) {
        PlayerData oldData = (PlayerData) event.original.getExtendedProperties(PlayerData.KEY);
        PlayerData newData = (PlayerData) event.entityPlayer.getExtendedProperties(PlayerData.KEY);
        if (oldData != null && newData != null) newData.copyFrom(oldData);
    }
    @SubscribeEvent public void onPlayerTick(net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent event) {
        if (!(event.entityLiving instanceof EntityPlayer)) return;
        EntityPlayer player = (EntityPlayer) event.entityLiving;
        PlayerData data = (PlayerData) player.getExtendedProperties(PlayerData.KEY);
        if (data != null) data.getBody().tick(new ForgeEnvironment(player));
    }
}
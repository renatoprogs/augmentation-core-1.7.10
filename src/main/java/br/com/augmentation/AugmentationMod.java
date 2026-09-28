package br.com.augmentation;

import br.com.augmentation.forge.PlayerDataHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;

@Mod(modid = "augmentation", name = "Augmentation Core", version = "0.0.4")
public final class AugmentationMod {
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        PlayerDataHandler.register();
    }
}
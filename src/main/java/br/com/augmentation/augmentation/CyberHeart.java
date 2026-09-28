package br.com.augmentation.augmentation;

import br.com.augmentation.api.IBody;
import br.com.augmentation.api.IBodyContext;
import br.com.augmentation.api.IAugmentation;
import br.com.augmentation.resource.ResourceType;

/** First experimental CyberHeart: mechanics only, not the original item/rendering layer. */
public final class CyberHeart implements IAugmentation {
    private boolean installed;
    private int charge = 100;
    private int stress;

    @Override public String getId() { return "cyberheart"; }
    @Override public boolean isInstalled() { return installed; }
    public int getCharge() { return charge; }
    public int getStress() { return stress; }

    @Override public void install(IBody body) { installed = true; }
    @Override public void remove(IBody body) { installed = false; }

    @Override
    public void tick(IBodyContext context) {
        if (!installed) return;
        int energy = context.requestResource(ResourceType.ENERGY, 2);
        if (energy < 2) {
            stress = Math.min(1000000, stress + 1);
            context.provideFunction("energy_distribution", 0);
            return;
        }
        if (charge > 0) charge--;
        context.provideFunction("energy_distribution", 120);
        if (stress > 0) stress--;
    }
}
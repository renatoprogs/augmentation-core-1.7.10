package br.com.augmentation.body;
import br.com.augmentation.api.IBodyContext;
import br.com.augmentation.api.IOrgan;
import br.com.augmentation.resource.ResourceType;
public final class BiologicalHeart implements IOrgan {
    private int integrity = 100;
    private int stability = 100;
    private int stress;
    @Override public String getId() { return "heart"; }
    @Override public int getIntegrity() { return integrity; }
    @Override public int getStability() { return stability; }
    @Override public int getStress() { return stress; }
    @Override public void tick(IBodyContext context) {
        int oxygen = context.requestResource(ResourceType.OXYGEN, 1);
        int function = oxygen == 1 ? 100 : 0;
        context.provideFunction("energy_distribution", function);
        if (function == 0) stress = Math.min(1000000, stress + 1);
        else if (stress > 0) stress--;
    }
}
package br.com.augmentation.body;
import br.com.augmentation.api.IBodyContext;
import br.com.augmentation.api.IOrgan;
import br.com.augmentation.resource.ResourceType;
public final class BiologicalBrain implements IOrgan {
    private int integrity = 100;
    private int stability = 100;
    private int stress;
    @Override public String getId() { return "brain"; }
    @Override public int getIntegrity() { return integrity; }
    @Override public int getStability() { return stability; }
    @Override public int getStress() { return stress; }
    @Override public void tick(IBodyContext context) {
        int oxygen = context.requestResource(ResourceType.OXYGEN, 1);
        int pressure = Math.round(context.getPressure() * 100.0f);
        int function = oxygen == 1 && pressure >= 20 ? 100 : 0;
        context.provideFunction("neural_processing", function);
        if (function == 0) stress = Math.min(1000000, stress + 1);
        else if (stress > 0) stress--;
    }
}
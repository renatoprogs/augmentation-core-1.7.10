package br.com.augmentation.body;
import br.com.augmentation.api.IBodyContext;
import br.com.augmentation.api.IOrgan;
public final class BiologicalLungs implements IOrgan {
    private int integrity = 100;
    private int stability = 100;
    private int stress;
    @Override public String getId() { return "lungs"; }
    @Override public int getIntegrity() { return integrity; }
    @Override public int getStability() { return stability; }
    @Override public int getStress() { return stress; }
    @Override public void tick(IBodyContext context) {
        int pressure = Math.round(context.getPressure() * 100.0f);
        int processing = Math.max(0, Math.min(100, pressure));
        context.provideFunction("oxygen_processing", processing);
        context.provideFunction("oxygen_storage", 50);
        if (pressure < 20) stress = Math.min(1000000, stress + 1);
        else if (stress > 0) stress--;
    }
}
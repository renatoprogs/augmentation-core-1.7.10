package br.com.augmentation.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import br.com.augmentation.api.IBloodDemand;
import br.com.augmentation.api.IResourceDemand;
import br.com.augmentation.api.IResourceType;
import br.com.augmentation.api.environment.IEnvironment;
import br.com.augmentation.body.BloodDemand;
import br.com.augmentation.body.BloodSystem;
import br.com.augmentation.body.Body;
import br.com.augmentation.body.CollapseModel;
import br.com.augmentation.body.CollapseState;
import br.com.augmentation.resource.ResourceDemand;
import br.com.augmentation.resource.ResourceNetwork;
import br.com.augmentation.resource.ResourceStorage;
import br.com.augmentation.resource.ResourceType;

public final class CoreInvariantTest {
    @Test
    public void resourceAllocationHonorsPriorityAndConservesStorage() {
        ResourceStorage storage = new ResourceStorage(ResourceType.OXYGEN, 10, 5);
        ResourceNetwork network = new ResourceNetwork();
        network.addStorage(storage);

        ResourceDemand low = new ResourceDemand(ResourceType.OXYGEN, 4, 10);
        ResourceDemand high = new ResourceDemand(ResourceType.OXYGEN, 4, 20);
        List<IResourceDemand> demands = new ArrayList<IResourceDemand>();
        demands.add(low);
        demands.add(high);

        network.allocate(demands);

        assertEquals(4, high.getProvidedAmount());
        assertEquals(1, low.getProvidedAmount());
        assertEquals(0, storage.getAmount());
        assertEquals(5, high.getProvidedAmount() + low.getProvidedAmount());
    }

    @Test
    public void bloodAllocationHonorsPriorityAndNeverCreatesOxygen() {
        BloodSystem blood = new BloodSystem(5);
        blood.addOxygen(5);
        IBloodDemand low = new BloodDemand("low", 4, 10);
        IBloodDemand high = new BloodDemand("high", 4, 20);
        List<IBloodDemand> demands = new ArrayList<IBloodDemand>();
        demands.add(low);
        demands.add(high);

        blood.allocateOxygen(demands);

        assertEquals(4, high.getProvidedOxygen());
        assertEquals(1, low.getProvidedOxygen());
        assertEquals(0, blood.getOxygen());
        assertTrue(high.getProvidedOxygen() + low.getProvidedOxygen() <= 5);
    }

    @Test
    public void collapseThresholdsAreMonotonicAtDefinedBoundaries() {
        assertEquals(CollapseState.NORMAL, CollapseModel.evaluate(0, 100, 100, 100));
        assertEquals(CollapseState.STRAIN, CollapseModel.evaluate(25, 100, 100, 100));
        assertEquals(CollapseState.FAILURE, CollapseModel.evaluate(60, 100, 100, 100));
        assertEquals(CollapseState.COLLAPSE, CollapseModel.evaluate(90, 100, 100, 100));
        assertEquals(CollapseState.COLLAPSE, CollapseModel.evaluate(0, 0, 100, 100));
    }

    @Test
    public void defaultBodyCompletesExternalToBloodToOrganCycle() {
        Body body = new Body();
        body.tick(new FixedEnvironment(100.0f, 20.0f));

        assertEquals(1, body.getTickCount());
        assertEquals(98, body.getOxygen());
        assertEquals(1, body.getBloodOxygen());
        assertEquals(CollapseState.NORMAL, body.getCollapseState());
    }

    private static final class FixedEnvironment implements IEnvironment {
        private final float pressure;
        private final float temperature;

        FixedEnvironment(float pressure, float temperature) {
            this.pressure = pressure;
            this.temperature = temperature;
        }

        @Override public int getResourceAvailable(IResourceType type) { return 1000; }
        @Override public int extractResource(IResourceType type, int amount) { return amount; }
        @Override public float getPressure() { return pressure; }
        @Override public float getTemperature() { return temperature; }
    }
}

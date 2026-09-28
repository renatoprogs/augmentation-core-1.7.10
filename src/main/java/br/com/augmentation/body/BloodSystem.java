package br.com.augmentation.body;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.augmentation.api.IBloodDemand;
import br.com.augmentation.api.IBloodSystem;

public final class BloodSystem implements IBloodSystem {
    private final int oxygenCapacity;
    private int oxygen;

    public BloodSystem(int oxygenCapacity) {
        this.oxygenCapacity = Math.max(0, oxygenCapacity);
    }

    @Override
    public int addOxygen(int amount) {
        int accepted = Math.min(Math.max(0, amount), oxygenCapacity - oxygen);
        oxygen += accepted;
        return accepted;
    }

    @Override
    public int consumeOxygen(int amount) {
        int consumed = Math.min(Math.max(0, amount), oxygen);
        oxygen -= consumed;
        return consumed;
    }

    @Override
    public void allocateOxygen(List<IBloodDemand> demands) {
        List<IBloodDemand> ordered = new ArrayList<IBloodDemand>(demands);
        Collections.sort(ordered, new Comparator<IBloodDemand>() {
            @Override
            public int compare(IBloodDemand left, IBloodDemand right) {
                int priority = right.getPriority() - left.getPriority();
                if (priority != 0) return priority;
                return left.getConsumerId().compareTo(right.getConsumerId());
            }
        });

        for (IBloodDemand demand : ordered) {
            int provided = consumeOxygen(demand.getOxygenAmount());
            demand.setProvidedOxygen(provided);
        }
    }

    @Override public int getOxygen() { return oxygen; }
    @Override public int getOxygenCapacity() { return oxygenCapacity; }
}

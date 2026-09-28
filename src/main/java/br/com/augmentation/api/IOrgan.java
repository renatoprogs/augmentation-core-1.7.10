package br.com.augmentation.api;

import java.util.List;

public interface IOrgan extends IPhysiologyState {
    String getId();
    int getMaximumStress();
    void collectResourceDemands(IBodyContext context, List<IResourceDemand> demands);
    void collectBloodDemands(IBodyContext context, List<IBloodDemand> demands);
    void tick(IBodyContext context);
}

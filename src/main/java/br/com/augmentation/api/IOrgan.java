package br.com.augmentation.api;

import java.util.List;

public interface IOrgan {
    String getId();
    int getIntegrity();
    int getStability();
    int getStress();
    void collectResourceDemands(IBodyContext context, List<IResourceDemand> demands);
    void tick(IBodyContext context);
}

package br.com.augmentation.api;

public interface IOrgan {
    String getId();
    int getIntegrity();
    int getStability();
    int getStress();
    void tick(IBodyContext context);
}
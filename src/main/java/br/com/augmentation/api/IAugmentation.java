package br.com.augmentation.api;

public interface IAugmentation {
    String getId();
    boolean isInstalled();
    void install(IBody body);
    void remove(IBody body);
    void tick(IBodyContext context);
}
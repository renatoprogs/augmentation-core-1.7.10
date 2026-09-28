package br.com.augmentation.api;

public interface IPhysiologyState {
    int getIntegrity();
    int getStability();
    int getStress();
    int getWear();
    void setIntegrity(int value);
    void setStability(int value);
    void setStress(int value);
    void setWear(int value);
}

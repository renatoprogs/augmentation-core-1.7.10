package br.com.augmentation.api;

public interface IResourceEfficiencySource {
    int getResourceEfficiencyPercent(IResourceType type, float environmentalPressure);
}

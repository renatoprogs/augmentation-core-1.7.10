package br.com.augmentation.api;

public interface IResourceStorage {
    IResourceType getType();
    int getAmount();
    int getCapacity();
    int insert(int amount);
    int extract(int amount);
}
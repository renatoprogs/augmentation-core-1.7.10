package br.com.augmentation.body;

public final class CollapseModel {
    private CollapseModel() { }

    public static CollapseState evaluate(int stress, int stability, int integrity, int maximumStress) {
        int maxStress = Math.max(1, maximumStress);
        int normalizedStress = Math.max(0, Math.min(100, stress * 100 / maxStress));
        int condition = PhysiologyModel.conditionPercent(integrity, stability);

        if (integrity <= 0 || stability <= 0 || normalizedStress >= 90) {
            return CollapseState.COLLAPSE;
        }
        if (condition <= 25 || normalizedStress >= 60) {
            return CollapseState.FAILURE;
        }
        if (condition <= 60 || normalizedStress >= 25) {
            return CollapseState.STRAIN;
        }
        return CollapseState.NORMAL;
    }
}

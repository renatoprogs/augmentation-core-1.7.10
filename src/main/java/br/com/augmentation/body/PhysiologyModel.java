package br.com.augmentation.body;

public final class PhysiologyModel {
    private PhysiologyModel() { }

    public static int pressureUnits(float pressure, PhysiologyProfile p) {
        return Math.max(0, Math.round(pressure * p.getPressureScale()));
    }

    public static int conditionPercent(int integrity, int stability) {
        return clamp((clamp(integrity, 0, 100) + clamp(stability, 0, 100)) / 2, 0, 100);
    }

    public static int oxygenProcessing(int pressureUnits, int conditionPercent, PhysiologyProfile p) {
        int pressure = clamp(pressureUnits, 0, p.getPressureScale());
        int condition = clamp(conditionPercent, 0, 100);
        int range = p.getProcessingAtFullCondition() - p.getProcessingAtFailure();
        int pressureContribution = pressure * range / Math.max(1, p.getPressureScale());
        return clamp(p.getProcessingAtFailure()
                + pressureContribution * condition / 100,
                p.getProcessingAtFailure(),
                p.getProcessingAtFullCondition());
    }

    public static int nextStress(int currentStress, int pressureUnits, PhysiologyProfile p) {
        return nextStress(currentStress, pressureUnits, true, p);
    }

    public static int nextStress(int currentStress, int pressureUnits,
            boolean resourceSatisfied, PhysiologyProfile p) {
        int stress = clamp(currentStress, 0, p.getMaximumStress());

        if (!resourceSatisfied || pressureUnits < p.getStressPressureThreshold()) {
            return clamp(stress + p.getStressIncrease(), 0, p.getMaximumStress());
        }

        return clamp(stress - p.getStressRecovery(), 0, p.getMaximumStress());
    }

    public static int extractionEfficiency(int pressureUnits, int conditionPercent, PhysiologyProfile p) {
        return oxygenProcessing(pressureUnits, conditionPercent, p);
    }

    public static int nextWear(int currentWear, int stress, PhysiologyProfile p) {
        int wear = clamp(currentWear, 0, 100);
        if (stress >= p.getWearStressThreshold()) {
            return clamp(wear + p.getWearIncrease(), 0, 100);
        }
        return wear;
    }

    public static int nextStability(int currentStability, int stress,
            boolean functionSatisfied, PhysiologyProfile p) {
        int stability = clamp(currentStability, 0, 100);
        if (stress >= p.getStabilityStressThreshold() || !functionSatisfied) {
            return clamp(stability - p.getStabilityDecrease(), 0, 100);
        }
        return clamp(stability + p.getStabilityRecovery(), 0, 100);
    }

    public static int nextIntegrity(int currentIntegrity, int wear, PhysiologyProfile p) {
        int integrity = clamp(currentIntegrity, 0, 100);
        int target = clamp(100 - clamp(wear, 0, 100) / p.getWearToIntegrityDivisor(), 0, 100);
        return Math.min(integrity, target);
    }

    private static int clamp(int v, int min, int max) {
        return Math.max(min, Math.min(max, v));
    }
}

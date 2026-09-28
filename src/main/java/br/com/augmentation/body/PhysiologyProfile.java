package br.com.augmentation.body;

import br.com.augmentation.resource.ResourceType;

public final class PhysiologyProfile {
    private final int pressureScale, minimumPressure, processingAtFullCondition, processingAtFailure;
    private final int stressPressureThreshold, stressIncrease, stressRecovery, maximumStress;
    private final int wearStressThreshold, wearIncrease;
    private final int stabilityStressThreshold, stabilityDecrease, stabilityRecovery;
    private final int wearToIntegrityDivisor;
    private final int functionAtFullCondition, functionAtFailure;
    private final ResourceDemandProfile oxygenDemand;

    public PhysiologyProfile(int pressureScale, int minimumPressure, int processingAtFullCondition, int processingAtFailure,
            int stressPressureThreshold, int stressIncrease, int stressRecovery, int maximumStress,
            int functionAtFullCondition, int functionAtFailure, ResourceDemandProfile oxygenDemand) {
        this(pressureScale, minimumPressure, processingAtFullCondition, processingAtFailure,
                stressPressureThreshold, stressIncrease, stressRecovery, maximumStress,
                25, 1, 60, 1, 1, 2,
                functionAtFullCondition, functionAtFailure, oxygenDemand);
    }

    public PhysiologyProfile(int pressureScale, int minimumPressure, int processingAtFullCondition, int processingAtFailure,
            int stressPressureThreshold, int stressIncrease, int stressRecovery, int maximumStress,
            int wearStressThreshold, int wearIncrease, int stabilityStressThreshold,
            int stabilityDecrease, int stabilityRecovery, int wearToIntegrityDivisor,
            int functionAtFullCondition, int functionAtFailure, ResourceDemandProfile oxygenDemand) {
        this.pressureScale=pressureScale; this.minimumPressure=minimumPressure;
        this.processingAtFullCondition=processingAtFullCondition; this.processingAtFailure=processingAtFailure;
        this.stressPressureThreshold=stressPressureThreshold; this.stressIncrease=stressIncrease;
        this.stressRecovery=stressRecovery; this.maximumStress=maximumStress;
        this.wearStressThreshold=wearStressThreshold; this.wearIncrease=wearIncrease;
        this.stabilityStressThreshold=stabilityStressThreshold;
        this.stabilityDecrease=stabilityDecrease; this.stabilityRecovery=stabilityRecovery;
        this.wearToIntegrityDivisor=Math.max(1, wearToIntegrityDivisor);
        this.functionAtFullCondition=functionAtFullCondition; this.functionAtFailure=functionAtFailure;
        this.oxygenDemand=oxygenDemand;
    }

    public int getPressureScale(){return pressureScale;} public int getMinimumPressure(){return minimumPressure;}
    public int getProcessingAtFullCondition(){return processingAtFullCondition;} public int getProcessingAtFailure(){return processingAtFailure;}
    public int getStressPressureThreshold(){return stressPressureThreshold;} public int getStressIncrease(){return stressIncrease;}
    public int getStressRecovery(){return stressRecovery;} public int getMaximumStress(){return maximumStress;}
    public int getWearStressThreshold(){return wearStressThreshold;} public int getWearIncrease(){return wearIncrease;}
    public int getStabilityStressThreshold(){return stabilityStressThreshold;} public int getStabilityDecrease(){return stabilityDecrease;}
    public int getStabilityRecovery(){return stabilityRecovery;} public int getWearToIntegrityDivisor(){return wearToIntegrityDivisor;}
    public int getFunctionAtFullCondition(){return functionAtFullCondition;} public int getFunctionAtFailure(){return functionAtFailure;}
    public ResourceDemandProfile getOxygenDemand(){return oxygenDemand;}

    public static PhysiologyProfile defaultProfile(){
        return new PhysiologyProfile(100,20,100,0,20,1,1,100,100,0,
                new ResourceDemandProfile(ResourceType.OXYGEN, 1, 100));
    }

    public static PhysiologyProfile defaultLungProfile(){
        return new PhysiologyProfile(100,20,100,0,20,1,1,100,100,0,
                new ResourceDemandProfile(ResourceType.OXYGEN, 2, 110));
    }

    public static PhysiologyProfile defaultHeartProfile(){
        return new PhysiologyProfile(100,20,100,0,20,1,1,100,100,0,
                new ResourceDemandProfile(ResourceType.OXYGEN, 1, 90));
    }
}

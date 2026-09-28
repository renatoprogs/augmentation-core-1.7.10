package br.com.augmentation.body;

import br.com.augmentation.resource.ResourceType;

public final class PhysiologyProfile {
    private final int pressureScale, minimumPressure, processingAtFullCondition, processingAtFailure;
    private final int stressPressureThreshold, stressIncrease, stressRecovery, maximumStress;
    private final int functionAtFullCondition, functionAtFailure;
    private final ResourceDemandProfile oxygenDemand;

    public PhysiologyProfile(int pressureScale, int minimumPressure, int processingAtFullCondition, int processingAtFailure,
            int stressPressureThreshold, int stressIncrease, int stressRecovery, int maximumStress,
            int functionAtFullCondition, int functionAtFailure, ResourceDemandProfile oxygenDemand) {
        this.pressureScale=pressureScale; this.minimumPressure=minimumPressure;
        this.processingAtFullCondition=processingAtFullCondition; this.processingAtFailure=processingAtFailure;
        this.stressPressureThreshold=stressPressureThreshold; this.stressIncrease=stressIncrease;
        this.stressRecovery=stressRecovery; this.maximumStress=maximumStress;
        this.functionAtFullCondition=functionAtFullCondition; this.functionAtFailure=functionAtFailure;
        this.oxygenDemand=oxygenDemand;
    }

    public int getPressureScale(){return pressureScale;} public int getMinimumPressure(){return minimumPressure;}
    public int getProcessingAtFullCondition(){return processingAtFullCondition;} public int getProcessingAtFailure(){return processingAtFailure;}
    public int getStressPressureThreshold(){return stressPressureThreshold;} public int getStressIncrease(){return stressIncrease;}
    public int getStressRecovery(){return stressRecovery;} public int getMaximumStress(){return maximumStress;}
    public int getFunctionAtFullCondition(){return functionAtFullCondition;} public int getFunctionAtFailure(){return functionAtFailure;}
    public ResourceDemandProfile getOxygenDemand(){return oxygenDemand;}

    public static PhysiologyProfile defaultProfile(){
        return new PhysiologyProfile(100,20,100,0,20,1,1,1000000,100,0,
                new ResourceDemandProfile(ResourceType.OXYGEN, 1, 100));
    }

    public static PhysiologyProfile defaultHeartProfile(){
        return new PhysiologyProfile(100,20,100,0,20,1,1,1000000,100,0,
                new ResourceDemandProfile(ResourceType.OXYGEN, 1, 90));
    }
}

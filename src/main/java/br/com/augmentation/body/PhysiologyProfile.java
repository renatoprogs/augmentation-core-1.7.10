package br.com.augmentation.body;

public final class PhysiologyProfile {
    private final int pressureScale, minimumPressure, processingAtFullCondition, processingAtFailure;
    private final int stressPressureThreshold, stressIncrease, stressRecovery, maximumStress;
    public PhysiologyProfile(int pressureScale, int minimumPressure, int processingAtFullCondition, int processingAtFailure, int stressPressureThreshold, int stressIncrease, int stressRecovery, int maximumStress) {
        this.pressureScale=pressureScale; this.minimumPressure=minimumPressure; this.processingAtFullCondition=processingAtFullCondition; this.processingAtFailure=processingAtFailure;
        this.stressPressureThreshold=stressPressureThreshold; this.stressIncrease=stressIncrease; this.stressRecovery=stressRecovery; this.maximumStress=maximumStress;
    }
    public int getPressureScale(){return pressureScale;} public int getMinimumPressure(){return minimumPressure;}
    public int getProcessingAtFullCondition(){return processingAtFullCondition;} public int getProcessingAtFailure(){return processingAtFailure;}
    public int getStressPressureThreshold(){return stressPressureThreshold;} public int getStressIncrease(){return stressIncrease;}
    public int getStressRecovery(){return stressRecovery;} public int getMaximumStress(){return maximumStress;}
    public static PhysiologyProfile defaultProfile(){return new PhysiologyProfile(100,20,100,0,20,1,1,1000000);}
}

package br.com.augmentation.body;

public final class PhysiologyModel {
    private PhysiologyModel() { }
    public static int pressureUnits(float pressure, PhysiologyProfile p){return Math.max(0,Math.round(pressure*p.getPressureScale()));}
    public static int conditionPercent(int integrity,int stability){return clamp((clamp(integrity,0,100)+clamp(stability,0,100))/2,0,100);}
    public static int oxygenProcessing(int pressureUnits,int conditionPercent,PhysiologyProfile p){
        int pressure=clamp(pressureUnits,0,p.getPressureScale()), condition=clamp(conditionPercent,0,100);
        int range=p.getProcessingAtFullCondition()-p.getProcessingAtFailure();
        int pressureContribution=pressure*range/Math.max(1,p.getPressureScale());
        return clamp(p.getProcessingAtFailure()+pressureContribution*condition/100,p.getProcessingAtFailure(),p.getProcessingAtFullCondition());
    }
    public static int nextStress(int currentStress,int pressureUnits,PhysiologyProfile p){
        int stress=clamp(currentStress,0,p.getMaximumStress());
        if(pressureUnits<p.getStressPressureThreshold()) return clamp(stress+p.getStressIncrease(),0,p.getMaximumStress());
        return clamp(stress-p.getStressRecovery(),0,p.getMaximumStress());
    }
    private static int clamp(int v,int min,int max){return Math.max(min,Math.min(max,v));}
}

package br.com.augmentation.environment;

/**
 * Small deterministic atmosphere model for the MVP.
 * oxygenFraction is intentionally abstract: it is not yet tied to real gas chemistry.
 */
public final class Atmosphere {
    private final int oxygenPerTick;
    private final float pressure;
    private final float temperature;

    public Atmosphere(int oxygenPerTick, float pressure, float temperature) {
        this.oxygenPerTick = Math.max(0, oxygenPerTick);
        this.pressure = clamp(pressure, 0.0f, 1.0f);
        this.temperature = temperature;
    }

    public int getOxygenPerTick() { return oxygenPerTick; }
    public float getPressure() { return pressure; }
    public float getTemperature() { return temperature; }

    public static Atmosphere normal() { return new Atmosphere(2, 1.0f, 20.0f); }
    public static Atmosphere thin() { return new Atmosphere(1, 0.45f, 20.0f); }
    public static Atmosphere vacuum() { return new Atmosphere(0, 0.0f, 20.0f); }

    private static float clamp(float v, float min, float max) {
        return Math.max(min, Math.min(max, v));
    }
}
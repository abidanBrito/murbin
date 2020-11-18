/*
 * Created by Francisco Javier Paños Madrona on 6/11/20 18:00
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 14/11/20 14:45
 */

package com.example.murbin.models;

public class Measure {

    /*
      Constant for ease of use in debugging the class code
     */
    private static final String TAG = Measure.class.getSimpleName();

    private int co2;
    private double noise;
    private int brightness;

    /**
     * Constructor Default
     */
    public Measure() {
        // Empty
    }

    /**
     * Constructor
     *
     * @param co2        Co2 measurement
     * @param noise      Noise measurement
     * @param brightness Brightness measurement
     */
    public Measure(int co2, double noise, int brightness) {
        this.co2 = co2;
        this.noise = noise;
        this.brightness = brightness;
    }

    public int getCo2() {
        return co2;
    }

    public void setCo2(int co2) {
        this.co2 = co2;
    }

    public double getNoise() {
        return noise;
    }

    public void setNoise(double noise) {
        this.noise = noise;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    @Override
    public String toString() {
        return "Measure{" +
                "co2=" + co2 +
                ", noise=" + noise +
                ", brightness=" + brightness +
                '}';
    }

}

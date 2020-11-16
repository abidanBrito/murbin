/*
 * Created by Francisco Javier Paños Madrona on 15/11/20 11:34
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 15/11/20 11:34
 */

package com.example.murbin.models;

public class MeasureBrightness {

    /*
      Constant for ease of use in debugging the class code
     */
    private static final String TAG = MeasureBrightness.class.getSimpleName();

    private String id;
    private String date;
    private String value;

    /**
     * Constructor Default
     */
    public MeasureBrightness() {
        // Empty
    }

    /**
     * Constructor
     *
     * @param id    Measure identifier
     * @param date  Date the measurement was taken
     * @param value Measure value
     */
    public MeasureBrightness(String id, String date, String value) {
        this.id = id;
        this.date = date;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "MeasureNoise{" +
                "id='" + id + '\'' +
                ", date='" + date + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}

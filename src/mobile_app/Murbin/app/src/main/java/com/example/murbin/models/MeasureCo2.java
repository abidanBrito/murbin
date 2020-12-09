/*
 * Created by Francisco Javier Paños Madrona on 15/11/20 11:31
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 15/11/20 11:31
 */

package com.example.murbin.models;

public class MeasureCo2 {

    private String id;
    private String date;
    private String value;

    /**
     * Constructor Default
     */
    public MeasureCo2() {
        // Empty
    }

    /**
     * Constructor
     *
     * @param id    Measure identifier
     * @param date  Date the measurement was taken
     * @param value Measure value
     */
    public MeasureCo2(String id, String date, String value) {
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

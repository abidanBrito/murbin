/*
 * Created by Francisco Javier Paños Madrona on 15/11/20 10:45
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 15/11/20 10:45
 */

package com.example.murbin.models;

public class Zone {

    /*
      Constant for ease of use in debugging the class code
     */
    private static final String TAG = Zone.class.getSimpleName();

    private String name;
    private boolean status;

    /**
     * Constructor Default
     */
    public Zone() {
        // Empty
    }

    /**
     * Constructor
     *
     * @param name   Zone name [ID]
     * @param status Status of the streetlights in the zone [On/Off]
     */
    public Zone(String name, boolean status) {
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Zone{" +
                "name='" + name + '\'' +
                ", status=" + status +
                '}';
    }
}

/*
 * Created by Francisco Javier Paños Madrona on 15/11/20 11:30
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 15/11/20 11:30
 */

package com.example.murbin.models;

public class Subzone {

    /*
      Constant for ease of use in debugging the class code
     */
    private static final String TAG = Subzone.class.getSimpleName();

    private String parentZoneID;
    private String name;
    private boolean status;

    /**
     * Constructor Default
     */
    public Subzone() {
        // Empty
    }

    /**
     * Constructor
     *
     * @param parentZoneID Name of the parent zone [parent_ID]
     * @param name         Subzone name [ID]
     * @param status       Status of the streetlights in the subzone [On/Off]
     */
    public Subzone(String parentZoneID, boolean status, String name) {
        this.parentZoneID = parentZoneID;
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
        return "Subzone{" +
                "parentZoneID='" + parentZoneID + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                '}';
    }
}

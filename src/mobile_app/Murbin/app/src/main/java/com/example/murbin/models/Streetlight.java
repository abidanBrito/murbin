/*
 * Created by Francisco Javier Paños Madrona on 15/11/20 11:29
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 15/11/20 11:29
 */

package com.example.murbin.models;

public class Streetlight {

    private String parentSubzoneID;
    private String name;
    private GeoPoint geoPoint;
    private boolean status;

    /**
     * Constructor Default
     */
    public Streetlight() {
        // Empty
    }

    /**
     * Constructor
     *
     * @param parentSubzoneID Name of the parent subzone [parent_ID]
     * @param name            Subzone name [ID]
     * @param status          Streetlight status [On/Off]
     */
    public Streetlight(String parentSubzoneID, String name, boolean status) {
        this.parentSubzoneID = parentSubzoneID;
        this.name = name;
        this.status = status;
    }

    /**
     * Constructor
     *
     * @param parentSubzoneID Name of the parent subzone [parent_ID]
     * @param name            Subzone name [ID]
     * @param geoPoint
     * @param status          Streetlight status [On/Off]
     */
    public Streetlight(String parentSubzoneID, String name, GeoPoint geoPoint, boolean status) {
        this.parentSubzoneID = parentSubzoneID;
        this.name = name;
        this.geoPoint = geoPoint;
        this.status = status;
    }

    public String getParentSubzoneID() {
        return parentSubzoneID;
    }

    public void setParentSubzoneID(String parentSubzoneID) {
        this.parentSubzoneID = parentSubzoneID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public void setGeoPoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Streetlight{" +
                "parentSubzoneID='" + parentSubzoneID + '\'' +
                ", name='" + name + '\'' +
                ", geoPoint=" + geoPoint +
                ", status=" + status +
                '}';
    }
}

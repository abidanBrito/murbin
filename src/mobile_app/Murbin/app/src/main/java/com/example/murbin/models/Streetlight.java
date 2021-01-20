/*
 * Created by Francisco Javier Paños Madrona on 15/11/20 11:29
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 15/11/20 11:29
 */

package com.example.murbin.models;

import java.util.HashMap;
import java.util.Map;

public class Streetlight {

    private String parentSubzoneID;
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
     * @param status          Streetlight status [On/Off]
     */
    public Streetlight(String parentSubzoneID, boolean status) {
        this.parentSubzoneID = parentSubzoneID;
        this.status = status;
    }

    /**
     * Constructor
     *
     * @param parentSubzoneID Name of the parent subzone [parent_ID]
     * @param geoPoint
     * @param status          Streetlight status [On/Off]
     */
    public Streetlight(String parentSubzoneID, GeoPoint geoPoint, boolean status) {
        this.parentSubzoneID = parentSubzoneID;
        this.geoPoint = geoPoint;
        this.status = status;
    }

    public String getParentSubzoneID() {
        return parentSubzoneID;
    }

    public void setParentSubzoneID(String parentSubzoneID) {
        this.parentSubzoneID = parentSubzoneID;
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
                ", geoPoint=" + geoPoint +
                ", status=" + status +
                '}';
    }

    public Map<String, Object> parseToMap() {
        Map<String, Object> streetlightMap = new HashMap<>();

        streetlightMap.put("subzone", this.getParentSubzoneID());

        return streetlightMap;
    }
}

/*
 * Created by Francisco Javier Paños Madrona on 15/11/20 11:29
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 15/11/20 11:29
 */

package com.example.murbin.models;

import java.util.HashMap;
import java.util.Map;

public class Streetlight {

    private GeoPoint location;
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
     * @param status          Streetlight status [On/Off]
     */
    public Streetlight(boolean status) {
        this.status = status;
    }

    /**
     * Constructor
     *
     * @param location        Geopoint
     * @param status          Streetlight status [On/Off]
     */
    public Streetlight(GeoPoint location, boolean status) {
        this.location = location;
        this.status = status;
    }

    public GeoPoint getGeoPoint() {
        return location;
    }

    public void setGeoPoint(GeoPoint location) {
        this.location = location;
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
                "location=" + location +
                ", status=" + status +
                '}';
    }

    public Map<String, Object> parseToMap() {
        Map<String, Object> streetlightMap = new HashMap<>();

        streetlightMap.put("location", location);

        return streetlightMap;
    }
}

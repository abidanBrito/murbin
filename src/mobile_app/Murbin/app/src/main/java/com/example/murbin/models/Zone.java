/*
 * Created by Francisco Javier Paños Madrona on 15/11/20 10:45
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 15/11/20 10:45
 */

package com.example.murbin.models;

import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Zone {

    private String name;
    private boolean status;
    private List<com.google.firebase.firestore.GeoPoint> area;

    /**
     * Constructor Default
     */
    public Zone() {
        this.area = new ArrayList<>();
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
        this.area = new ArrayList<>();
    }

    /**
     * Constructor
     *
     * @param name   Zone name [ID]
     * @param status Status of the streetlights in the zone [On/Off]
     * @param area   Area for generate polygon
     */
    public Zone(String name, boolean status, List<com.google.firebase.firestore.GeoPoint> area) {
        this.name = name;
        this.status = status;
        this.area = area;
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

    public List<com.google.firebase.firestore.GeoPoint> getArea() {
        return area;
    }

    public void setArea(List<com.google.firebase.firestore.GeoPoint> area) {
        this.area = area;
    }

    /**
     * Returns the area formatted as a list from Geopoint for map.
     *
     * @return List<GeoPoint>
     */
    public List<com.example.murbin.models.GeoPoint> getFormattedArea() {
        List<com.example.murbin.models.GeoPoint> areaFormatted = new ArrayList<>();

        for (int i = 0; i < this.area.size(); i++) {
            areaFormatted.add(new com.example.murbin.models.GeoPoint(area.get(i).getLatitude(), area.get(i).getLongitude()));

        }

        return areaFormatted;
    }

    @Override
    public String toString() {
        return "Zone{" +
                "name='" + name + '\'' +
                ", status=" + status +
                ", area=" + area.toString() +
                '}';
    }

    /**
     * Transform Zone to Map
     *
     * @return Map<String, Object>
     */
    public Map<String, Object> parseToMap() {
        Map<String, Object> zoneMap = new HashMap<>();

        zoneMap.put("name", this.getName());
        zoneMap.put("status", this.isStatus());
        zoneMap.put("area", this.getArea());
        return zoneMap;
    }
}

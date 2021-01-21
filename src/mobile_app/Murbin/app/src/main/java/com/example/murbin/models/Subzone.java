/*
 * Created by Francisco Javier Paños Madrona on 15/11/20 11:30
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 15/11/20 11:30
 */

package com.example.murbin.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Subzone {

    private String name;
    private boolean status;
    private List<com.google.firebase.firestore.GeoPoint> area;

    /**
     * Constructor Default
     */
    public Subzone() {
        this.area = new ArrayList<>();
    }

    /**
     * Constructor
     *
     * @param name   Subzone name [ID]
     * @param status Status of the streetlights in the subzone [On/Off]
     */
    public Subzone(String name, boolean status) {
        this.name = name;
        this.status = status;
        this.area = new ArrayList<>();
    }

    /**
     * Constructor
     *
     * @param name   Subzone name [ID]
     * @param status Status of the streetlights in the subzone [On/Off]
     * @param area   Area for generate polygon
     */
    public Subzone(String name, boolean status, List<com.google.firebase.firestore.GeoPoint> area) {
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
        return "Subzone{" +
                "name='" + name + '\'' +
                ", status=" + status +
                ", area=" + area.toString() +
                '}';
    }

    /**
     * Transform Subzone to Map
     *
     * @return Map<String, Object>
     */
    public Map<String, Object> parseToMap() {
        Map<String, Object> subzoneMap = new HashMap<>();

        subzoneMap.put("name", this.getName());
        subzoneMap.put("status", this.isStatus());
        subzoneMap.put("area", this.getArea());

        return subzoneMap;
    }
}

/*
 * Created by Francisco Javier Paños Madrona on 15/11/20 12:07
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 15/11/20 12:07
 */

package com.example.murbin.models;

public class GeoPoint {

    private double latitude;
    private double longitude;

    /**
     * Constructor Default
     */
    public GeoPoint() {
        // Empty
    }

    /**
     * Constructor
     *
     * @param latitude  Point latitude
     * @param longitude Point longitude
     */
    public GeoPoint(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Method to calculate the distance between two points using the Haversine algorithm
     *
     * @param geoPoint GeoPoint to compare
     * @return double
     */
    public double distance(GeoPoint geoPoint) {
        final double RADIO_TIERRA = 6371000; // en metros
        double dLat = Math.toRadians(this.latitude - geoPoint.latitude);
        double dLon = Math.toRadians(this.longitude - geoPoint.longitude);
        double lat1 = Math.toRadians(geoPoint.latitude);
        double lat2 = Math.toRadians(this.latitude);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.sin(dLon / 2) * Math.sin(dLon / 2) *
                        Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return c * RADIO_TIERRA;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "GeoPoint{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
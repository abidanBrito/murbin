/*
 * Created by Francisco Javier Paños Madrona on 17/12/20 11:08
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 17/12/20 11:08
 */

package com.example.murbin.models;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class Area {

    private List<GeoPoint> area = new ArrayList<>();

    /**
     * Constructor empty
     */
    public Area() {
        // Empty
    }

    /**
     * Constructor
     *
     * @param area List with Geo Points
     */
    public Area(List<GeoPoint> area) {
        super();
        this.area = area;
    }

    public List<GeoPoint> getArea() {
        return area;
    }

    public void setArea(List<GeoPoint> area) {
        this.area = area;
    }

    public int getSize() {
        return area.size();
    }

    @Override
    public String toString() {
        return "Area{" +
                "area=" + area +
                '}';
    }

    /**
     * Returns a GeoPoint with the coordinates of the center of the area
     *
     * @return GeoPoint
     */
    public GeoPoint getCenterArea() {
        double[] centerArea = {0.0, 0.0};

        for (int i = 0; i < area.size(); i++) {
            centerArea[0] += area.get(i).getLatitude();
            centerArea[1] += area.get(i).getLongitude();
        }

        int totalPoints = getSize();
        centerArea[0] = centerArea[0] / totalPoints;
        centerArea[1] = centerArea[1] / totalPoints;

        return new GeoPoint(centerArea[0], centerArea[1]);
    }

    /**
     * Convert each Geopoint in the area to LatLng format for GoogleMaps
     *
     * @return List<LatLng>
     */
    public List<LatLng> convertToLatLngList() {
        List<LatLng> arrayPoints = new ArrayList<>();
        LatLng pointMap;

        for (GeoPoint geoPoint : area) {
            pointMap = new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude());
            arrayPoints.add(pointMap);
        }

        return arrayPoints;
    }

    /**
     * Convert each Geopoint in the area from firebase to LatLng format for GoogleMaps
     *
     * @return List<LatLng>
     */
    public List<LatLng> convertToLatLngList(List<com.google.firebase.firestore.GeoPoint> area) {
        List<LatLng> arrayPoints = new ArrayList<>();
        LatLng pointMap;

        for (com.google.firebase.firestore.GeoPoint geoPoint : area) {
            pointMap = new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude());
            arrayPoints.add(pointMap);
        }

        return arrayPoints;
    }

    /**
     * Convert each firestore.GeoPoint format in the area to GeoPoint format
     *
     * @return List<com.google.firebase.firestore.GeoPoint>
     */
    public List<GeoPoint> convertToGeoPoint(List<com.google.firebase.firestore.GeoPoint> area) {
        List<GeoPoint> arrayPoints = new ArrayList<>();
        GeoPoint point;

        for (com.google.firebase.firestore.GeoPoint geoPoint : area) {
            point = new GeoPoint(geoPoint.getLatitude(), geoPoint.getLongitude());
            arrayPoints.add(point);
        }

        return arrayPoints;
    }

    /**
     * Convert each Geopoint in the area to firestore.GeoPoint format
     *
     * @return List<com.google.firebase.firestore.GeoPoint>
     */
    public List<com.google.firebase.firestore.GeoPoint> convertToFirestoreGeoPoint() {
        List<com.google.firebase.firestore.GeoPoint> arrayPoints = new ArrayList<>();
        com.google.firebase.firestore.GeoPoint point;

        for (GeoPoint geoPoint : area) {
            point = new com.google.firebase.firestore.GeoPoint(geoPoint.getLatitude(), geoPoint.getLongitude());
            arrayPoints.add(point);
        }

        return arrayPoints;
    }
}
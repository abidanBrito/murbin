/*
 * Created by Francisco Javier Paños Madrona on 6/11/20 18:00
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 14/11/20 14:45
 */

package com.example.murbin.presentation.zone.general.fragments;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.murbin.App;
import com.example.murbin.R;
import com.example.murbin.data.ZonesDatabaseCrud;
import com.example.murbin.models.Area;
import com.example.murbin.models.GeoPoint;
import com.example.murbin.services.GpsTrackerService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

public class GeneralFragmentMap extends Fragment implements
        OnMapReadyCallback,
        GoogleMap.OnPolygonClickListener {

    private SupportMapFragment mMapView;
    private GoogleMap mMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.general_fragment_map, container, false);

        mMapView = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.general_fragment_map_mapview);
        mMapView.getMapAsync(this);

        return view;
    }

    @Override
    public void onDestroyView() {
        if (mMapView != null)
            getChildFragmentManager().beginTransaction()
                    .remove(mMapView).commitAllowingStateLoss();
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMapView != null) {
            mMapView.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mMapView != null) {
            mMapView.onPause();
        }
    }

    @Override
    public void onDestroy() {
        if (mMapView != null) {
            mMapView.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mMapView != null) {
            mMapView.onLowMemory();
        }
    }

    @Override
    public void onPolygonClick(Polygon polygon) {
//        Log.d(App.DEFAULT_TAG, "Pulsada Zona: " + polygon.getTag().toString());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        ZonesDatabaseCrud zdc = new ZonesDatabaseCrud();
        // Retrieve the zone from firestore
        zdc.read("Gandia", zone -> {
            boolean hasLocalizationActivated = false;
            GpsTrackerService gpsTrackerService = new GpsTrackerService(getContext());

            if (App.getInstance().hasPermissions(getContext(), App.ARRAY_PERMISSIONS_LOAD_MAP)) {
//                Log.d(App.DEFAULT_TAG, "Latitude: " + gpsTrackerService.getLatitude());
//                Log.d(App.DEFAULT_TAG, "Longitude: " + gpsTrackerService.getLongitude());
                hasLocalizationActivated = true;
                if(gpsTrackerService.getLatitude() == 0.0 && gpsTrackerService.getLongitude() == 0.0){
                    hasLocalizationActivated = false;
                    gpsTrackerService.showSettingsAlert();
                }
            }

            if (zone != null) {
                if (!zone.getArea().isEmpty()) {
                    mMap = googleMap;

                    try {
                        // Customise the styling of the base map using a JSON object defined
                        // in a raw resource file.
                        boolean success = mMap.setMapStyle(
                                MapStyleOptions.loadRawResourceStyle(
                                        getContext(), R.raw.style_google_map));
                        if (!success) {
//                            Log.d(App.DEFAULT_TAG, "Error al analizar el estilo.");
                        }
                    } catch (Resources.NotFoundException e) {
//                        Log.d(App.DEFAULT_TAG, "No encuentro el estilo. Error: ", e);
                    }

                    Area area = new Area(zone.getFormattedArea());
                    GeoPoint centerZone;
                    if (!hasLocalizationActivated) {
                        centerZone = area.getCenterArea();
                    } else {
                        centerZone = new GeoPoint(gpsTrackerService.getLatitude(), gpsTrackerService.getLongitude());
                    }
                    LatLng centerPoint = new LatLng(centerZone.getLatitude(), centerZone.getLongitude());

                    Polygon polygon = mMap.addPolygon(new PolygonOptions()
                            .clickable(true)
                            .addAll(
                                    area.convertToLatLngList()
                            )
                    );
                    polygon.setTag(zone.getName());
                    polygon.setStrokeColor(R.color.white);
                    polygon.setFillColor(R.color.white);


                    // Custom Icon
                    Bitmap.Config conf = Bitmap.Config.ARGB_8888;
                    Bitmap bmp = Bitmap.createBitmap(80, 80, conf);
                    Canvas canvas1 = new Canvas(bmp);

                    // paint defines the text color, stroke width and size
                    Paint color = new Paint();
                    color.setTextSize(30);
                    color.setColor(Color.WHITE);

                    // modify canvas
                    canvas1.drawBitmap(BitmapFactory.decodeResource(getResources(),
                            R.drawable.farola), 0, 0, color);
                    canvas1.drawText("1", 30, 40, color);

                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(38.99560840090937, -0.16577659868488484))
                            .icon(BitmapDescriptorFactory.fromBitmap(bmp))
                            // Specifies the anchor to be at a particular point in the marker image.
                            .anchor(0.5f, 1)
                    );

                    // modify canvas
                    canvas1.drawText("2", 30, 40, color);

                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(38.99627134373954, -0.1657301906425137))
                            .icon(BitmapDescriptorFactory.fromBitmap(bmp))
                            // Specifies the anchor to be at a particular point in the marker image.
                            .anchor(0.5f, 1)
                    );

                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centerPoint, 12));
                    mMap.setOnPolygonClickListener(GeneralFragmentMap.this);
                } else {
                    App.getInstance().snackMessage(getActivity().findViewById(R.id.general_main_activity_container), R.color.black, "La Zona no está definida.", App.getContext());
                }
            } else {
                App.getInstance().snackMessage(getActivity().findViewById(R.id.general_main_activity_container), R.color.black, "La Zona indicada no existe.", App.getContext());
            }
        });
    }
}
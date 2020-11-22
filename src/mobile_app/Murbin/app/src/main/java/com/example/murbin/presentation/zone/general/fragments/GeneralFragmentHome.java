/*
 * Created by Francisco Javier Paños Madrona on 6/11/20 18:00
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 14/11/20 14:45
 */

package com.example.murbin.presentation.zone.general.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.murbin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class GeneralFragmentHome extends Fragment {

    /**
     * Constant for ease of use in debugging the class code
     */
    private static final String TAG = GeneralFragmentHome.class.getSimpleName();

    private ViewGroup container;
    private TextView brightness, temperature, co2, noise, humidity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.general_fragment_home, container, false);

        brightness = view.findViewById(R.id.general_fragment_home_brightness);
        temperature = view.findViewById(R.id.general_fragment_home_temperature);
        co2 = view.findViewById(R.id.general_fragment_home_co2);
        noise = view.findViewById(R.id.general_fragment_home_noise);
        humidity = view.findViewById(R.id.general_fragment_home_humidity);

        getLastMeasures();

        return view;
    }

    private void getLastMeasures() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference sensors;

        sensors = db.collection("streetlights").document("1")
                .collection("sensors");

        sensors.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    String value;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        value = document.getData().get("value").toString();
//                        Log.d(TAG, document.getId() + " => " + value);
                        setValueSensor(document.getId(), value);
                    }
                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });

    }

    private void setValueSensor(String id, String badFormatValue) {
        String value = badFormatValue.replace("\n", "").replace("\r", "");
        switch (id) {
            case "brightness":
                brightness.setText("Brillo: " + value + " %");
                break;
            case "co2":
                co2.setText("CO2: " + value + " %");
                break;
            case "noise":
                noise.setText("Ruido: " + value + " db");
                break;
            case "temperature":
                temperature.setText("Temperatura: " + value + "ºC");
                break;
            case "humidity":
                humidity.setText("Humedad: " + value + " %");
                break;
        }
    }
}
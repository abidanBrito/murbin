package com.example.rpi_controller;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivityUART extends Activity
{

    private static final String TAG = MainActivityUART.class.getSimpleName();
    ArduinoUart uart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Lista de UART disponibles: " + ArduinoUart.disponibles());
        uart = new ArduinoUart("UART0", 115200);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        run(db);
    }

    @Override protected void onDestroy() {
        super.onDestroy();
    }

    void run(FirebaseFirestore db) {
        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Log.w(TAG, "Error en sleep()", e);
            }

            uart.escribir("L");

            String s = uart.leer();
            Log.d(TAG, s);

            Map<String, Object> datos = new HashMap<>();
            datos.put("value", s);
            db.collection("streetlights").document("1")
                    .collection("sensors").document("brightness")
                    .set(datos);
        }
    }
}
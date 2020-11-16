package com.example.rpi_controller;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

public class MainActivityUART extends Activity
{

    private static final String TAG = MainActivityUART.class.getSimpleName();
    ArduinoUart uart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Lista de UART disponibles: " + ArduinoUart.disponibles());
        uart = new ArduinoUart("UART0", 115200);

        while (true) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Log.w(TAG, "Error en sleep()", e);
            }

            String s = uart.leer();
            Log.d(TAG, s);
        }
    }

    @Override protected void onDestroy() {
        super.onDestroy();
    }

}
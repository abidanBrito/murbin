package com.example.uart_demo;

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
        Log.d(TAG, "Mandado a Arduino: C");
        uart.escribir("C");

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Log.w(TAG, "Error en sleep()", e);
        }

        String s = uart.leer();
        Log.d(TAG, "Recibido de Arduino: " + s);

        Log.d(TAG, "Mandado a Arduino: L");
        uart.escribir("L");

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Log.w(TAG, "Error en sleep()", e);
        }

        s = uart.leer();
        Log.d(TAG, "Recibido de Arduino: " + s);
    }


    @Override protected void onDestroy() {
        super.onDestroy();
    }

}
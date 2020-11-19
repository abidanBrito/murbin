package com.example.rpi_controller;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.comun.Mqtt;
import com.google.firebase.firestore.FirebaseFirestore;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.HashMap;
import java.util.Map;

import static com.example.rpi_controller.MainActivity.client;

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

        try {
            Log.i(Mqtt.TAG, "Conectando al broker " + Mqtt.broker);
            client = new MqttClient(Mqtt.broker, Mqtt.clientId,
                    new MemoryPersistence());
            client.connect();
        } catch (MqttException e) {
            Log.e(Mqtt.TAG, "Error al conectar.", e);
        }


        run(db);




    }


    @Override public void onDestroy() {
        try {
            Log.i(Mqtt.TAG, "Desconectado");
            client.disconnect();
        } catch (MqttException e) {
            Log.e(Mqtt.TAG, "Error al desconectar.", e);
        }
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
        String l = uart.leer();
        Log.d(TAG, l);
        uart.escribir("S");
        String s = uart.leer();
        Log.d(TAG, s);
        uart.escribir("C");
        String c = uart.leer();
        Log.d(TAG, c);
        uart.escribir("H");
        String t = uart.leer();
        Log.d(TAG, t);
        /*
        uart.escribir("T");
        String h = uart.leer();
        Log.d(TAG, h);
        */



            try {
                MqttMessage luz = new MqttMessage(l.getBytes());
                luz.setQos(Mqtt.qos);
                luz.setRetained(false);
                client.publish(Mqtt.topicRoot+"luz", luz);

                MqttMessage co2 = new MqttMessage(c.getBytes());
                co2.setQos(Mqtt.qos);
                co2.setRetained(false);
                client.publish(Mqtt.topicRoot+"co2", co2);

    /*
                MqttMessage humedad = new MqttMessage(h.getBytes());
                humedad.setQos(Mqtt.qos);
                humedad.setRetained(false);
                client.publish(Mqtt.topicRoot+"humedad", humedad);

                MqttMessage temp = new MqttMessage(t.getBytes());
                temp.setQos(Mqtt.qos);
                temp.setRetained(false);
                client.publish(Mqtt.topicRoot+"temperatura", temp);
*/
                MqttMessage sound = new MqttMessage(s.getBytes());
                sound.setQos(Mqtt.qos);
                sound.setRetained(false);
                client.publish(Mqtt.topicRoot+"sonido", sound);
            } catch (MqttException e) {
                Log.e(Mqtt.TAG, "Error al publicar.", e);
            }



            /*Map<String, Object> datos = new HashMap<>();
            datos.put("value", s);
            db.collection("streetlights").document("1")
                    .collection("sensors").document("brightness")
                    .set(datos);*/
        }
    }

}
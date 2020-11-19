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
            String brightnessValue = getSensorData("Luminosity: ", "L");
            String soundValue = getSensorData("Noise level: ","S");
            String co2Value = getSensorData("CO2: ","C");
            String temperatureValue = getSensorData("Temperature: ", "T");
            String humidityValue = getSensorData("Humidity", "H");

            pushDataFirestore(db, brightnessValue, "brightness");
            pushDataFirestore(db, soundValue, "noise");
            pushDataFirestore(db, co2Value, "co2");
            pushDataFirestore(db, temperatureValue, "temperature");
            pushDataFirestore(db, humidityValue, "humidity");

            try {
                MqttMessage light = new MqttMessage(brightnessValue.getBytes());
                light.setQos(Mqtt.qos);
                light.setRetained(false);
                client.publish(Mqtt.topicRoot + "light", light);

                MqttMessage co2 = new MqttMessage(co2Value.getBytes());
                co2.setQos(Mqtt.qos);
                co2.setRetained(false);
                client.publish(Mqtt.topicRoot + "co2", co2);

                MqttMessage sound = new MqttMessage(soundValue.getBytes());
                sound.setQos(Mqtt.qos);
                sound.setRetained(false);
                client.publish(Mqtt.topicRoot + "sound", sound);
            } catch (MqttException e) {
                Log.e(Mqtt.TAG, "Error al publicar.", e);
            }
        }
    }

    String getSensorData(String sensorType, String data) {
        String value = "";
        try {
            uart.escribir(data);
            Thread.sleep(1600);
        } catch (InterruptedException e) {
            Log.w(TAG, "Error en sleep()", e);
        }

        value = uart.leer();
        Log.d(TAG, sensorType + value);
        return value;
    }

    void pushDataFirestore(FirebaseFirestore db, String value, String sensorDocument) {
        // Push new readings over to Firestore
        Map<String, Object> datos = new HashMap<>();
        datos.put("value", value);
        db.collection("streetlights").document("1")
                .collection("sensors").document(sensorDocument)
                .set(datos);
    }
}
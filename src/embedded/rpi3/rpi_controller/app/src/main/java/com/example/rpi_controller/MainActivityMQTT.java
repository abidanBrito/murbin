package com.example.rpi_controller;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.comun.Mqtt;
import com.google.firebase.firestore.FirebaseFirestore;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.HashMap;
import java.util.Map;

import static com.example.comun.Mqtt.TAG;
import static com.example.comun.Mqtt.qos;
import static com.example.comun.Mqtt.topicRoot;

public class MainActivityMQTT extends Activity implements MqttCallback
{
    //private static final String TAG = MainActivityUART.class.getSimpleName();
    //ArduinoUart uart;

    public static MqttClient client = null;
    FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Log.i(TAG, "Lista de UART disponibles: " + ArduinoUart.disponibles());
        //uart = new ArduinoUart("UART0", 115200);

        db = FirebaseFirestore.getInstance();

        try {
            Log.i(TAG, "Conectando al broker " + Mqtt.broker);
            client = new MqttClient(Mqtt.broker, Mqtt.clientId,
                    new MemoryPersistence());
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setKeepAliveInterval(60);
            connOpts.setWill(topicRoot+"WillTopic", "App desconectada".getBytes(), qos, false);
                    client.connect(connOpts);
        }
        catch (MqttException e) {
            Log.e(TAG, "Error al conectar.", e);
        }

        try {
            Log.i(TAG, "Suscrito a " + topicRoot+"sensors/#");
            client.subscribe(topicRoot+"sensors/#", qos);
            client.setCallback((MqttCallback) this);
        }
        catch (MqttException e) {
            Log.e(TAG, "Error al suscribir.", e);
        }
    }

    @Override public void onDestroy() {
        try {
            Log.i(TAG, "Desconectado");
            client.disconnect();
        } catch (MqttException e) {
            Log.e(TAG, "Error al desconectar.", e);
        }
        super.onDestroy();
    }

    @Override
    public void connectionLost(Throwable cause) {
        Log.d(TAG, "Conexión perdida");
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        String payload = new String(message.getPayload());
        Log.d(Mqtt.TAG, "Recibiendo: " + topic + "->" + payload);

        if(topic.contains("co2")){
            String datos = new String(message.getPayload());
            pushDataFirestore(db, datos, "co2");
        }

        if(topic.contains("brightness")){
            String datos = new String(message.getPayload());
            pushDataFirestore(db, datos, "brightness");
        }

        if(topic.contains("noise")){
            String datos = new String(message.getPayload());
            pushDataFirestore(db, datos, "noise");
        }

        if(topic.contains("humidity")){
            String datos = new String(message.getPayload());
            pushDataFirestore(db, datos, "humidity");
        }

        if(topic.contains("temperature")){
            String datos = new String(message.getPayload());
            pushDataFirestore(db, datos, "temperature");
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        Log.d(TAG, "Entrega completa");
    }

    // For debugging purposes (UART)
    /*
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
    */

    void pushDataFirestore(FirebaseFirestore db, String value, String sensorDocument) {
        // Push new readings over to Firestore
        Map<String, Object> datos = new HashMap<>();
        datos.put("value", value);
        db.collection("streetlights").document("1")
                .collection("sensors").document(sensorDocument)
                .set(datos);
    }
}
package com.example.rpi_controller;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.comun.Mqtt;
import com.google.android.things.pio.Gpio;
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

public class MainActivityUART extends Activity implements MqttCallback
{
    //private static final String TAG = MainActivityUART.class.getSimpleName();
    ArduinoUart uart;

    public static final String LED_PIN = "BCM22"; //physical pin #15
    private Gpio ledPin;
    public static MqttClient client = null;
    FirebaseFirestore db;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Lista de UART disponibles: " + ArduinoUart.disponibles());
        uart = new ArduinoUart("UART0", 115200);
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
        } catch (MqttException e) {
            Log.e(TAG, "Error al conectar.", e);
        }

        try {
            Log.i(TAG, "Suscrito a " + topicRoot+"sensores/#");
            client.subscribe(topicRoot+"sensores/#", qos);
            client.setCallback((MqttCallback) this);
        } catch (MqttException e) {
            Log.e(TAG, "Error al suscribir.", e);
        }
        //run(db);
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

        if(topic.contains("CO2")){
            String datos = new String(message.getPayload());
            pushDataFirestore(db, datos, "co2");
        }
        if(topic.contains("light")){
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

    void run(FirebaseFirestore db) {
       /* while (true) {
           /* String brightnessValue = getSensorData("Luminosity: ", "L");
            String soundValue = getSensorData("Noise level: ","S");
            String co2Value = getSensorData("CO2: ","C");
            String temperatureValue = getSensorData("Temperature: ", "T");
            String humidityValue = getSensorData("Humidity", "H");*/

            /*pushDataFirestore(db, brightnessValue, "brightness");
            pushDataFirestore(db, soundValue, "noise");
            pushDataFirestore(db, co2Value, "co2");
            pushDataFirestore(db, temperatureValue, "temperature");
            pushDataFirestore(db, humidityValue, "humidity");*/
            /*
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
            }*/
      //  }
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
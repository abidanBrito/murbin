package com.example.comun;

public class Mqtt {
    public static final String TAG = "MQTT";
    public static final String topicRoot="equipo1-2/murbin/sensores/";
    //Reemplaza equipo por el nombre de tu equipo de prácticas
    public static final int qos = 1;
    public static final String broker = "tcp://mqtt.eclipse.org:1883";
    public static final String clientId = "RaspberryMurbin";
//Reemplaza ClientId con un valor diferente
}
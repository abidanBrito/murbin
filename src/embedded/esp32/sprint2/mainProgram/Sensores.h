// ---------------------------------------------------
//
// Sensores.h
//
// ---------------------------------------------------

#ifndef SENSOR_YA_INCLUIDO
#define SENSOR_YA_INCLUIDO
#include "Arduino.h"

     //definiendo variables
     //-- sensor_pir.cpp --//
     #define sensorPIR GPIO_NUM_25 //pin sensor movimiento
     #define sensorPIR_FLAG_LEVEL ESP_INTR_FLAG_LEVEL2  //definiendo nivel de interrupcion para sensor pirs

     //-- sensor_ruido.cpp --//
     //#define sensorRUIDO GPIO_NUM_39 //pin sensor de ruido
     #define sensorRUIDO_FLAG_LEVEL ESP_INTR_FLAG_LEVEL1 //definiendo nivel de interrupcion sensor ruido
    
    //variables interrupciones
    static volatile bool movimiento=0;
    const int LED_PIR = 12;

    //SENSORES SIN WIFI//
    void sensor_LUM(int, int);
    void sensor_CO2(int, int);
    void detectar_movimiento();
    void encender_rele(int);
    
#endif

  
  //////////////////////////////////////////////////////
  /////////////         sensores         ///////////////
  //////////////////////////////////////////////////////

#ifndef SENSOR_YA_INCLUIDO
#define SENSOR_YA_INCLUIDO
#include "Arduino.h"
#include <ArduinoMqttClient.h>
#include <Adafruit_NeoPixel.h>

//Definiendo variables
  
  //--  sensor_pir.cpp  --//
  #define sensorPIR GPIO_NUM_25                      //pin sensor movimiento
  #define sensorPIR_FLAG_LEVEL ESP_INTR_FLAG_LEVEL2  //definiendo nivel de interrupcion para sensor pirs

  //--    PixelRing    --//
  #define NUMPIXELS      24
      
//Variables interrupciones
  
  static volatile bool movimiento=0;
  const int LED_PIR = 12;

  //--SENSORES SIN WIFI-//
  void sensor_LUM(int, int);
  void detectar_movimiento();
  void encender_rele(int);
//  void retro_Led(int, Adafruit_NeoPixel);
    
#endif

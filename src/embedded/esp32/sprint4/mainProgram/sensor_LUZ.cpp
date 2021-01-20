#include "Sensores.h"

  //////////////////////////////////////////////////////
  /////////////        SENSOR LUZ        ///////////////
  //////////////////////////////////////////////////////
  
void sensor_LUM(int pin, int ledPin){
  int valor = analogRead(pin);

  if (valor > 800) {
    digitalWrite(ledPin, HIGH);
    delay(800);
    digitalWrite(ledPin, LOW);
  }
  Serial.println(valor);
}

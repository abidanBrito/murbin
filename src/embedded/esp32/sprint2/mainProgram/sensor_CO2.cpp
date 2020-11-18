  #include "Sensores.h"
  /////////////////////////////////////////////////////
  /////////////// SENSOR CO2 /////////////
  //////////////////////////////////////////////////////

void sensor_CO2(int pin, int ledPin){
  int value = analogRead(pin);
  if (value < 1780) 
  {
    Serial.print("Nivel alto de CO2: ");
    digitalWrite(ledPin , HIGH);  
    delay(2500);
    digitalWrite(ledPin , LOW);
  }
  else //si la salida del sensor es 0
  {
    Serial.print("Nivel medio de C02: ");
  }
  Serial.println(value);
}

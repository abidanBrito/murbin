  #include "Sensores.h"
  /////////////////////////////////////////////////////
  /////////////// SENSOR CO2 /////////////
  //////////////////////////////////////////////////////

void sensor_CO2(int pin, int ledPin){
  int value = analogRead(pin);
  if (value < 1780) 
  {
    digitalWrite(ledPin , HIGH);  
    delay(2500);
    digitalWrite(ledPin , LOW);
  }
  Serial.println(value);
}

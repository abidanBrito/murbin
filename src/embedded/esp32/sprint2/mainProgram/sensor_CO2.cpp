  #include "Sensores.h"
  /////////////////////////////////////////////////////
  /////////////// SENSOR CO2 /////////////
  //////////////////////////////////////////////////////

void sensor_CO2(int pin, int ledPin){
  int value = analogRead(pin);
  
  if (value > 2850) {
    digitalWrite(ledPin , HIGH);  
    delay(800);
    digitalWrite(ledPin , LOW);
  }
  Serial.println(value);
}

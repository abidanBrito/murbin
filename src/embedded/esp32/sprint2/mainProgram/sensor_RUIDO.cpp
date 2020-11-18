  #include "Sensores.h"
  /////////////////////////////////////////////////////
  /////////////// SENSOR RUIDO ////////////////////////
  /////////////////////////////////////////////////////


void sensor_ruido(int pin, int ledPin)
{
  int valor = analogRead(pin);

  if(valor > 3000){
    Serial.println(" se ha superado el humbral de ruido");
    digitalWrite(ledPin, HIGH);
    delay(2000);
  }
  
}

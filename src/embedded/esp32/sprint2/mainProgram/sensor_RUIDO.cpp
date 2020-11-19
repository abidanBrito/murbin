  #include "Sensores.h"
  /////////////////////////////////////////////////////
  /////////////// SENSOR RUIDO ////////////////////////
  /////////////////////////////////////////////////////


void noise_sensor(int pin, int ledPin)
{
  int value= analogRead(pin);
  Serial.print(value);
  
  /*
  if(valor > 3000){
    Serial.println(" se ha superado el humbral de ruido");
    digitalWrite(ledPin, HIGH);
    delay(2000);
    digitalWrite(ledPin, HIGH);
  }
  */
  
}

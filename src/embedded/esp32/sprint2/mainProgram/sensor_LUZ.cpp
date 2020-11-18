  #include "Sensores.h"
  /////////////////////////////////////////////////////
  /////////////// SENSOR LUZ /////////////
  //////////////////////////////////////////////////////
void sensor_LUM(int pin, int ledPin){
  int valor = analogRead(pin);

  if (valor < 3050){
    Serial.print("Hay luz intensa: ");
  }
  else {
    if (valor > 3050 && valor < 3220){
      Serial.print("Hay luz ambiental: ");
    }
    else {
      Serial.print("No hay luz: ");
    }
    digitalWrite(ledPin, HIGH);
    delay(2500);
    digitalWrite(ledPin, LOW);
  }
     
  Serial.println(valor);
}

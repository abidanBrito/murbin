  #include "Sensores.h"
  
  /////////////////////////////////////////////////////
  /////////////// FUNCIONES SENSOR TEMP /////////////
  //////////////////////////////////////////////////////
 
void sensor_DHT(float h, float t){
   
 
   if (isnan(h) || isnan(t)) 
   {
      Serial.println("Error en la lectura de datos!!!");
      return;
   }
   
   Serial.print("Humedad: ");
   Serial.print(h);
   Serial.print(" %\t");
   Serial.print("Temperatura: ");
   Serial.print(t);
   Serial.print(" *C ");
}

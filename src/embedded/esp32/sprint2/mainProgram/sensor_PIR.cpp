  #include "Sensores.h"
  /////////////////////////////////////////////////////
  /////////////// FUNCIONES SENSOR PIR /////////////
  //////////////////////////////////////////////////////

void  IRAM_ATTR detectarMovimiento(void* arg){
        Serial.println("\n ********************");
        Serial.println("Movimiento detectado");
        Serial.println("********************");
        movimiento = 1;
}
void detectar_movimiento()
{
  // Configurar pines
  gpio_set_direction(sensorPIR, GPIO_MODE_INPUT);
  // Configurar interrupciones en pines
  gpio_set_intr_type(sensorPIR, GPIO_INTR_POSEDGE);
    // Setting pull down mode.
  gpio_set_pull_mode(sensorPIR, GPIO_PULLDOWN_ONLY);
  // install ISR service with default configuration
  gpio_install_isr_service(sensorPIR_FLAG_LEVEL);
    // attach the interrupt service routine
  gpio_isr_handler_add(sensorPIR, detectarMovimiento, NULL);
}
  
/*
void sensor_PIR(int pin, int ledPin){
  int value = digitalRead(pin);

  if(value == HIGH){
    Serial.println(value);
    delay(100);
  }else{
    Serial.println("Nada");
    delay(100);
  }
}
*/

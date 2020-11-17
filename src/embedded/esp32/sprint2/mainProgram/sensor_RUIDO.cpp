  #include "Sensores.h"
  /////////////////////////////////////////////////////
  /////////////// SENSOR RUIDO ////////////////////////
  /////////////////////////////////////////////////////

void  IRAM_ATTR detectarRuido(void* arg){
  Serial.println("Ruido detectado");

}
void detectar_ruido()
{
  // Configurar pines
//  gpio_set_direction(sensorRUIDO,GPIO_MODE_INPUT);
  // Configurar interrupciones en pines
 // gpio_set_intr_type(sensorRUIDO,GPIO_INTR_POSEDGE);
    // install ISR service with default configuration

 // gpio_install_isr_service(sensorRUIDO_FLAG_LEVEL);
    // attach the interrupt service routine
 // gpio_isr_handler_add(sensorRUIDO, detectarRuido, NULL);
}

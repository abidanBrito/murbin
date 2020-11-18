<<<<<<< HEAD
// MACROS DEFINITION
#define LED_LUM 32
#define LED_CO2 33
#define LUM_SENSOR  'L'
#define CO2_SENSOR  'C'
#define PIR_SENSOR  'P'

// CONSTANTS
const int sensorCO2 = 34;
const int sensorLUM = 35;
=======
#include "Sensores.h"
#include "DHT.h" //para sensor DHT

//definiendo pines
const int sensorCO2 = 34;    
const int sensorLUM = 35;
const int sensorRUIDO = 14;

//para sensor temperatura
#define DHTTYPE DHT11   // DHT 11
const int DHTPin = 26;
DHT dht(DHTPin, DHTTYPE);


//definiendo leds
#define LED_LUM 32
#define LED_CO2 33
#define LED_RUIDO 33

>>>>>>> develop

void setup() {
  Serial.begin(115200);
<<<<<<< HEAD
  
  // Configure GPIO pin modes
  pinMode(sensorCO2, INPUT);
  pinMode(sensorLUM, INPUT);

  // Actuators
=======
  //inicializa el sensor de temperatura.
  dht.begin();
   
  //Configurando los pines de entrada y salida
  pinMode(sensorCO2, INPUT);       // Sensor CO2
  pinMode(sensorLUM, INPUT);       // Sensor LUZ
  pinMode (sensorRUIDO, INPUT);  // Sensor de Ruido
  pinMode(DHTPin, INPUT);          // Sensor de Temperatura y Humedad
  detectar_movimiento();  //llama a la funcion para programar la interrupcion del sensor de movimiento.
 

  //configurando Leds
>>>>>>> develop
  pinMode(LED_LUM, OUTPUT);
  pinMode(LED_CO2, OUTPUT);
  pinMode(LED_PIR, OUTPUT);
  pinMode(LED_RUIDO, OUTPUT);
 
}
 
void loop() {
   
   //variable que detecta si la interrupcion ha sido producida
   if(movimiento){   
    movimiento=0;
   }else{
    digitalWrite(LED_PIR,LOW);
   }
   
   sensor_LUM(sensorLUM, LED_LUM);   
   sensor_CO2(sensorCO2, LED_CO2);
<<<<<<< HEAD
   delay(1500); 
}

void sensor_CO2(int pin, int ledPin){
  int value = analogRead(pin);
  if (value > 2750) {
    Serial.print("Nivel alto de CO2 - ");
    Serial.println(value);
    digitalWrite(ledPin , HIGH);  
    delay(1000);
    digitalWrite(ledPin , LOW);
  }
  else {
    Serial.print("Nivel medio de C02 - ");
    Serial.println(value);
  }
}
 
void sensor_LUM(int pin, int ledPin){
  int valor = analogRead(pin);
  float voltage_value = valor * 3.3 / 4095.0;

  if (valor <= 170){
    Serial.print("Nivel de luz 0 - ");
  }
  else {
    if (valor > 170 && valor <= 400){
      Serial.print("Nivel de luz 1 - ");
    }
    else if (valor > 400 && valor <= 700){
      Serial.print("Nivel de luz 2 - ");
    }
    else if (valor > 700 && valor <= 1000){
      Serial.print("Nivel de luz 3 - ");
    }
    else if (valor > 1000 && valor <= 1500){
      Serial.print("Nivel de luz 4 - ");
    }
    else if (valor > 1500 && valor <= 2100){
      Serial.print("Nivel de luz 5 - ");
    }
    else {
      Serial.print("Nivel de luz 6 - ");
    }
    Serial.println(valor);
    digitalWrite(ledPin, HIGH);
    delay(1000);
    digitalWrite(ledPin, LOW);
  }
=======
   sensor_ruido(sensorRUIDO, LED_RUIDO);
   

   // Sensor te temperatura Humedad
   float h = dht.readHumidity();
   float t = dht.readTemperature();
   sensor_DHT(h,t);
   
   delay(3000);
   
   
}

//interrupcion para movimiento
void  IRAM_ATTR detectarMovimiento(void* arg){
        movimiento=1;
    digitalWrite(LED_PIR,HIGH);
    Serial.println("\n ********************");
    Serial.println("Movimiento detectado");
    Serial.println("********************");
    
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
>>>>>>> develop
}

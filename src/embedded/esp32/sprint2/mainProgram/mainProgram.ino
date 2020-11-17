#include "Sensores.h"
#include "DHT.h" //para sensor DHT

//definiendo pines
const int sensorCO2 = 34;    
const int sensorLUM = 35;
const int pinMicrophone = 14;

//para sensor temperatura
#define DHTTYPE DHT11   // DHT 11
const int DHTPin = 26;
DHT dht(DHTPin, DHTTYPE);


//definiendo leds
#define LED_LUM 32
#define LED_CO2 33
#define LED_PIR 12

 


void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200);
  //inicializa el sensor de temperatura.
  dht.begin();
   
  //Configurando los pines de entrada y salida
  pinMode(sensorCO2, INPUT);       // Sensor CO2
  pinMode(sensorLUM, INPUT);       // Sensor LUZ
  pinMode(DHTPin, INPUT);          // Sensor de Temperatura y Humedad
  pinMode (pinMicrophone, INPUT);  // Sensor de Ruido
  
  //attachInterrupt(digitalPinToInterrupt(sensorPIR), detectarMovimiento, RISING); //Vinculamos el pin de interrupcion movimiento
  detectar_movimiento();  //llama a la funcion para programar la interrupcion del sensor de movimiento.
 // detectar_ruido();


  //configurando Leds
  pinMode(LED_LUM, OUTPUT);
  pinMode(LED_CO2, OUTPUT);
  pinMode(LED_PIR, OUTPUT);
 
}
 
void loop() {
   Serial.println(analogRead(pinMicrophone));
   //variable que detecta si la interrupcion ha sido producida
   if(movimiento){
    digitalWrite(LED_PIR,HIGH);
    delay(2000);
    digitalWrite(LED_PIR,LOW);
    movimiento--;
   }
   
   sensor_LUM(sensorLUM, LED_LUM);   
   sensor_CO2(sensorCO2, LED_CO2);

   // Sensor te temperatura Humedad
   float h = dht.readHumidity();
   float t = dht.readTemperature();
   sensor_DHT(h,t);
   delay(3000);
   
}

/*
void detectarMovimiento(){
        Serial.println("Movimiento detectado");
        //digitalWrite(LEDPin, HIGH); // LED OFF
        delay(1000);
        //digitalWrite(LEDPin, LOW); // LED OFF
}*/

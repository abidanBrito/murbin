//#include <M5Stack.h>
//PROGRAMA ACTUADORES //
 
const int sensorCO2 = 2;    
const int sensorLUM = 35;
const int PIRPin = 36; // pin de entrada (para el sensor PIR)
 
#define LED 5 //defino el led como pin 5
 
#define LUM_SENSOR  'L'       // defino el primer sensor de manera escalable
#define CO2_SENSOR  'C'       // defino el segundo sensor de manera escalable
#define PIR_SENSOR  'P'       // defino el tercer sensor de manera escalable
 
void setup() {
  // put your setup code here, to run once:
  //Serial.begin(115200);
  //Configurando los pines de entrada y salida
  pinMode(PIRPin, INPUT);  
  pinMode(sensorCO2, INPUT);       // Sensor CO2
  pinMode(sensorLUM, INPUT);       // Sensor LUZ
  pinMode(LED, OUTPUT);
  M5.begin();
  M5.Lcd.setTextSize(2);
  M5.Lcd.print("Pin GPIO value: ");
}
 
void loop() {
 
 
   if (Serial.available() > 0) {
      char command = (char) Serial.read();
      switch (command) {
         case CO2_SENSOR:
         Serial.print("Enviando informacion.. ");
            sensor_CO2(sensorCO2, LED);
            break;
         case LUM_SENSOR:
         Serial.print("Enviando informacion.. ");
          Serial.print(String(sensorLUM));
         sensor_LUM(sensorLUM, LED);
            break;
            /*
         case PIR_SENSOR:
         Serial.print("Enviando informacion.. ");
         Serial.print(String(PIRPin));
            sensor_PIR(PIRPin, LED);
            break;
            */
           
      }
   }
}
 
//programa sensor Co2
void sensor_CO2(int pin_mq, int ledPIN){
 
  boolean mq_estado = digitalRead(pin_mq);//Leemos el sensor
 
  if(mq_estado) //si la salida del sensor es 1
  {
    digitalWrite(ledPIN , LOW);    // poner el Pin en LOW
    Serial.println("Sin presencia de alcohol");
   
  }
  else //si la salida del sensor es 0
  {
    digitalWrite(ledPIN , HIGH);   // poner el Pin en HIGH
    Serial.println("Alcohol detectado");
    delay(2000);
    digitalWrite(ledPIN , LOW);
  }
}
 
//programa sensor luz
void sensor_LUM(int pin, int led){
  int valor = analogRead(pin);
  Serial.println(valor);
  //si el valor de la luz es mayor a 1000, el led se enciende y se apagara a los 2 segundos
  if(valor > 1000){
    Serial.println("Se ha dedectado luz");
    digitalWrite(led, HIGH);
    delay(2000); // Wait for 1000 millisecond(s)
    digitalWrite(led, LOW);
  }else{
    Serial.println("No Hay luz");
    digitalWrite(led, LOW);
  }
}
 
//programa sensor luz
/*
void sensor_PIR(int PIRPin, int LEDpin){
 
   val = digitalRead(PIRPin); //Lectura de datos
   if (val == HIGH) { //si está activado
     
      digitalWrite(LEDPin, HIGH);  //LED ON
      if (pirState == LOW){ //si previamente estaba apagado
     
        Serial.println("Sensor activado");
        pirState = HIGH;
      }
   } else {   //si esta desactivado
   
      digitalWrite(LEDPin, LOW); // LED OFF
      if (pirState == HIGH){  //si previamente estaba encendido
     
        Serial.println("Sensor parado");
        pirState = LOW;
      }
   }
}
*/
/*
//PROGRAMA UTILIZADO PARA M5STACK
 
 
#include <M5Stack.h>
 
const int ledPIN = 5;
int pin_mq = 2;
 
void setup() {
  Serial.begin(9600);
 // M5.begin();
 // M5.Lcd.setTextSize(2);
 // M5.Lcd.print("Pin GPIO value: ");
  pinMode(pin_mq, INPUT);
  pinMode(ledPIN , OUTPUT);
}
 
 
void loop() {
  int valor = analogRead(pin_mq);
  boolean mq_estado = digitalRead(pin_mq);//Leemos el sensor
  Serial.println(valor);
  if(valor < 3000) //si la salida del sensor es 1
  {
    //M5.Lcd.println("Sin presencia de alcohol");
    digitalWrite(ledPIN , LOW);    // poner el Pin en LOW
    Serial.println("Sin presencia de alcohol");
   
  }
  else //si la salida del sensor es 0
  {
    digitalWrite(ledPIN , HIGH);   // poner el Pin en HIGH
    Serial.println("Alcohol detectado");
  }
  delay(2000);
}
*/

const int sensorCO2 = 34;    
const int sensorLUM = 35;
// const int sensorPIR = 33; // pin de entrada (para el sensor PIR)
int pirState = LOW;           // de inicio no hay movimiento
int val = 0;                  // estado del pin
 
#define LED_LUM 32
#define LED_CO2 33
//#define LED_PIR 26
 
#define LUM_SENSOR  'L'       // defino el primer sensor de manera escalable
#define CO2_SENSOR  'C'       // defino el segundo sensor de manera escalable
#define PIR_SENSOR  'P'       // defino el tercer sensor de manera escalable
 
void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200);
  
  //Configurando los pines de entrada y salida
//  pinMode(sensorPIR, INPUT);  
  pinMode(sensorCO2, INPUT);       // Sensor CO2
  pinMode(sensorLUM, INPUT);       // Sensor LUZ
  pinMode(LED_LUM, OUTPUT);
  pinMode(LED_CO2, OUTPUT);
}
 
void loop() {
   sensor_LUM(sensorLUM, LED_LUM);   
   sensor_CO2(sensorCO2, LED_CO2);
   delay(1500);
   
   if (Serial.available() > 0) {
      char command = (char) Serial.read();
      switch (command) {
         case CO2_SENSOR:
         {
            Serial.print("-- CO2 SENSOR --");
            sensor_CO2(sensorCO2, LED_CO2);
         }
         break;
         
         case LUM_SENSOR:
         { 
            Serial.println("-- LUM SENSOR --");
            sensor_LUM(sensorLUM, LED_LUM);
         }
         break;  
         case PIR_SENSOR:
         {  
            Serial.print("-- PIR SENSOR --");
            //readPIRSensor(sensorPIR, LED_PIR);
         } 
         break;
      }
   }   
}
 
void sensor_CO2(int pin, int ledPin){
  int value = analogRead(pin);
  if (value < 1780) 
  {
    Serial.print("Nivel alto de CO2: ");
    digitalWrite(ledPin , HIGH);  
    delay(2500);
    digitalWrite(ledPin , LOW);
  }
  else //si la salida del sensor es 0
  {
    Serial.print("Nivel medio de C02: ");
  }
  Serial.println(value);
}
 
//programa sensor luz
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

void readPIRSensor(int inputPin, int ledPin) {
val = digitalRead(inputPin);
  if (val == HIGH)   //si está activado
  { 
    digitalWrite(ledPin, HIGH);  //LED ON
    delay(2000); // 
    digitalWrite(ledPin, LOW);
    if (pirState == LOW)  //si previamente estaba apagado
    {
      Serial.println("Sensor activado");
      pirState = HIGH;
    }
  } 
  else   //si esta desactivado
  {
    digitalWrite(ledPin, LOW); // LED OFF
    if (pirState == HIGH)  //si previamente estaba encendido
    {
      Serial.println("Sensor parado");
      pirState = LOW;
    }
  }
}

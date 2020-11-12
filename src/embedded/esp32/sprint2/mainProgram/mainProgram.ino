// MACROS DEFINITION
#define LED_LUM 32
#define LED_CO2 33
#define LUM_SENSOR  'L'
#define CO2_SENSOR  'C'
#define PIR_SENSOR  'P'

// CONSTANTS
const int sensorCO2 = 34;
const int sensorLUM = 35;

void setup() {
  Serial.begin(115200);
  
  // Configure GPIO pin modes
  pinMode(sensorCO2, INPUT);
  pinMode(sensorLUM, INPUT);

  // Actuators
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
      default:
          break;
      }
   }   
}

//
//
//
void sensor_CO2(int pin, int ledPin){
  int value = analogRead(pin);
  if (value < 1780) 
  {
    Serial.print("Nivel alto de CO2: ");
    digitalWrite(ledPin , HIGH);  
    delay(2500);
    digitalWrite(ledPin , LOW);
  }

  else
  {
    Serial.print("Nivel medio de C02: ");
  }
  Serial.println(value);
}
 
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

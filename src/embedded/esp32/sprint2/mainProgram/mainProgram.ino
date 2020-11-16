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
}

int sensorCO2 = 34;    
int sensorLUM = 35;
 
#define BLUE_LED     5 //defino el led como pin 5 por que ya esta integrado
#define LUM_SENSOR  'L' // defino el primer sensor de manera escalable
#define CO2_SENSOR  'C' // defino el primer sensor de manera escalable
void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200);
 
  //Configurando los pines de entrada y salida
  pinMode(sensorCO2, INPUT);       // Sensor CO2
  pinMode(sensorLUM, INPUT);       // Sensor LUZ
  pinMode(BLUE_LED, OUTPUT);
}
 
void loop() {
   if (Serial.available() > 0) {
      char command = (char) Serial.read();
      switch (command) {
         case LUM_SENSOR:
            Serial.print("Enviando informacion.. ");
            Serial.print(String(sensorLUM));
            sensor_CO2(sensorCO2, BLUE_LED);
            break;
         case CO2_SENSOR:
            Serial.print("Enviando informacion.. ");
            Serial.print(String(sensorLUM));
            sensor_LUM(sensorCO2, BLUE_LED);
            break; 
      }
   }
}
 
void sensor_CO2(int pin, int led){
  //si detecta entrada del sensor se activa
  if(digitalRead(pin) > 0){
    digitalWrite(led, HIGH);
    delay(1000); // Wait for 1000 millisecond(s)
    digitalWrite(led, LOW);
  }
}

void sensor_LUM(int pin, int led){
  //si detecta entrada del sensor se activa
  if(digitalRead(pin) > 0){
    digitalWrite(led, HIGH);
    delay(1000); // Wait for 1000 millisecond(s)
    digitalWrite(led, LOW);
  }
}

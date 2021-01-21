#include <M5Stack.h>
#include "Sensores.h"
#include "DHT.h" //para sensor DHT
#include "esp_freertos_hooks.h"
#define configUSE_IDLE_HOOK 1

/*********************************/
/*        Definiendo MQTT        */
/*********************************/

#include <ArduinoMqttClient.h>

#include <WiFiClient.h>
#include <WiFiGeneric.h>
#include <WiFiMulti.h>
#include <WiFiScan.h>

//includes udp
#include "AsyncUDP.h"
#include <TimeLib.h>

AsyncUDP udp;

#ifdef __AVR__
  #include <avr/power.h>
#endif

#if defined(ARDUINO_SAMD_MKRWIFI1010) || defined(ARDUINO_SAMD_NANO_33_IOT) || defined(ARDUINO_AVR_UNO_WIFI_REV2)
  #include <WiFiNINA.h>
#elif defined(ARDUINO_SAMD_MKR1000)
  #include <WiFi101.h>
#elif defined(ARDUINO_ESP8266_ESP12)
  #include <ESP8266WiFi.h>
#endif

char ssid[] = "Yeraysa-WLAN";        // Network SSID (name)
char pass[] = "12345678";            // Network password (use for WPA, or use as key for WEP)

WiFiClient wifiClient;
MqttClient mqttClient(wifiClient);

const char broker[] = "broker.hivemq.com";
int        port     = 1883;
const char topic[]  = "ycansam/practica/power";

const long interval = 1000;
unsigned long previousMillis = 0;

/*********************************/
/*   Definiendo Pines sensores   */
/*********************************/
const int sensorCO2 = 34;    
const int sensorLUM = 35;
const int sensorRUIDO = 4;
const int sensorRetro = 27; 
Adafruit_NeoPixel pixels = Adafruit_NeoPixel(NUMPIXELS, sensorRetro, NEO_GRB + NEO_KHZ800);


/***********************************************/
/*   Definiendo Valores Iddle Hook sensores    */
/***********************************************/
int contador_sensores = 0;

int lastVal_CO2 = 4095;
int lastVal_Noise = 4095;
int lastVal_Humidity = 100;
int lastVal_Temp = 50;


//Para sensor temperatura
#define DHTTYPE DHT11   // DHT 11
const int DHTPin = 26;
DHT dht(DHTPin, DHTTYPE);

/*********************************/
/* Definiendo semaforos sensores */
/*********************************/

//Declaracion manejadores de los semaforos binarios
SemaphoreHandle_t SCO2 = NULL;
SemaphoreHandle_t SLIGHT = NULL;
SemaphoreHandle_t SNOISE = NULL;
SemaphoreHandle_t SHUM = NULL;

//declaracion semaforo interrupcion
SemaphoreHandle_t xSemaphore_Noise = NULL;
SemaphoreHandle_t xSemaphore_PaintLeds = NULL;




void interr_task_noise(void *pvParameter)
{
  int value;
  int limit;
  while(1) {
    if(xSemaphoreTake(xSemaphore_Noise,portMAX_DELAY)){
      if (xSemaphoreTake( xSemaphore_PaintLeds, portMAX_DELAY )){
       while(analogRead(sensorRUIDO)> 3000){
        Serial.println("INTR NOISE: ");
        pixels.clear();
        Serial.print(analogRead(sensorRUIDO));
        value = analogRead(sensorRUIDO);
        limit = 4095;
        int bright_value = (value * 255) / limit;
        int pin_value = round((value * 24) / limit);
        
        for(int i=0;i<pin_value;i++)
        {
          if(bright_value >= 125) pixels.setPixelColor(i, pixels.Color(255, 0, 0));
          else pixels.setPixelColor(i, pixels.Color(0, 255, 0));
        }
        
        pixels.setBrightness(bright_value);
        
        pixels.show(); 
        vTaskDelay(250 / portTICK_PERIOD_MS);
        pixels.clear();
        pixels.setPixelColor(24, pixels.Color(0, 0, 0));
        pixels.show(); 
        vTaskDelay(250 / portTICK_PERIOD_MS);
         
      }
      pintarUltimoValor();
      xSemaphoreGive(xSemaphore_PaintLeds);
     }
    }
     
  }
}



/*********************************/
/*  Definiendo tareas sensores   */
/*********************************/

void taskCo2(void *pvParameter)
{
  String sensor = "CO2";
  while(1) {
    vTaskDelay(10000 / portTICK_PERIOD_MS);
    Serial.println("Ejecutando tarea Co2");
    lastVal_CO2 = analogRead(sensorCO2);

    int percent = (analogRead(sensorCO2) * 100) / 4095;


    
    sendMqtt(sensor,(int) percent);
    vTaskDelay(1000 / portTICK_PERIOD_MS);
    xSemaphoreGive(SCO2);
  }
}

void taskLight(void *pvParameter)
{
  String sensor = "light";
    while(1) {
      if(xSemaphoreTake(SCO2,portMAX_DELAY)){
        Serial.println("Ejecutando tarea LUM");
      
        int percent = (analogRead(sensorLUM) * 100) / 4095;
        sendMqtt(sensor,(int) percent);
        vTaskDelay(1000 / portTICK_PERIOD_MS);
        xSemaphoreGive(SLIGHT);
      }
   }
}

void taskNoise(void *pvParameter)
{
  String sensor = "noise";
  while(1) {
    if(xSemaphoreTake(SLIGHT,portMAX_DELAY)){
      Serial.println("Ejecutando tarea Noise");
      lastVal_Noise = analogRead(sensorRUIDO);
      sendMqtt(sensor, (int) analogRead(sensorRUIDO));
      vTaskDelay(1000 / portTICK_PERIOD_MS);
      xSemaphoreGive(SNOISE);
    }
  }
}

void taskHumidity(void *pvParameter)
{
  String sensor = "humidity";
  while(1) {
    if(xSemaphoreTake(SNOISE,portMAX_DELAY)){
      Serial.println("Ejecutando tarea Humedad");
      lastVal_Humidity =(int) dht.readHumidity();
      sendMqtt(sensor,(int) dht.readHumidity());
      vTaskDelay(1000 / portTICK_PERIOD_MS);
      xSemaphoreGive(SHUM);
    }
  }
}
void taskTemp(void *pvParameter)
{
  String sensor = "temperature";
  while(1) {
    if(xSemaphoreTake(SHUM,portMAX_DELAY)){
      Serial.println("Ejecutando tarea Temperatura");
      lastVal_Temp = (int) dht.readTemperature();
      sendMqtt(sensor,(int) dht.readTemperature());
      vTaskDelay(1000 / portTICK_PERIOD_MS);
    }
  }
}

void setup() 
{
  M5.begin();
  Serial.begin(115200);
  //pixels.begin(); 
  
  // Procesos MQTT
  while (!Serial) {
    ; //Espera al Serial port para conectarse
  }

  // Conexion a wifi
  Serial.print("Attempting to connect to WPA SSID: ");
  Serial.println(ssid);
  WiFi.mode(WIFI_STA);
  while (WiFi.begin(ssid, pass) != WL_CONNECTED) {
    // Si falla se retira
    Serial.println("Trying to connect..");
    delay(5000);
  }

  


  Serial.println("You're connected to the network");
  Serial.println();

  // mqttClient.setId("ArduinoClient1");

  Serial.print("Attempting to connect to the MQTT broker: ");
  Serial.println(broker);
  while(1){
    if (mqttClient.connect(broker, port)){
      break;
    }
  }
  if (!mqttClient.connect(broker, port)) {
    Serial.print("MQTT connection failed! Error code = ");
    Serial.println(mqttClient.connectError());

    while (1);
  }

  Serial.println("You're connected to the MQTT broker!");
  Serial.println();

  mqttClient.onMessage(onMqttMessage);

  Serial.print("Subscribing to topic: ");
  Serial.println(topic);
  Serial.println();

  // Suscripcion a un 'topic'
  mqttClient.subscribe(topic);

  Serial.print("Waiting for messages on topic: ");
  Serial.println(topic);
  Serial.println();

if(udp.listen(1234)) {
    Serial.print("UDP Listening on IP: ");
    Serial.println(WiFi.localIP());
    udp.onPacket([](AsyncUDPPacket packet) {
    Serial.write(packet.data(), packet.length());
    Serial.println();
    });
  }
  // Fin procesos mqtt
 
  // Inicializa el sensor de temperatura.
  dht.begin();
   
  // Configurando los pines de entrada y salida
  pinMode(sensorCO2, INPUT);       // Sensor CO2
  pinMode(sensorLUM, INPUT);       // Sensor LUZ
  pinMode(sensorRUIDO, INPUT);       // Sensor LUZ
  pinMode(DHTPin, INPUT);          // Sensor de Temperatura y Humedad
  detectar_movimiento();           // Llama a la funcion para programar la interrupcion del sensor de movimiento.

  // Configurando Leds
  pinMode(LED_PIR, OUTPUT);

  // Creacion semaforos binarios
   SCO2 = xSemaphoreCreateBinary();
   SLIGHT = xSemaphoreCreateBinary();
   SNOISE = xSemaphoreCreateBinary();
   SHUM = xSemaphoreCreateBinary();
    
  // Creacion de tareas
   xTaskCreate(&taskCo2, "tarea1 CO2", 10000, NULL, 3, NULL);
   xTaskCreate(&taskLight, "tarea2 LIGHT", 10000, NULL,3, NULL);
   xTaskCreate(&taskNoise, "tarea3 NOISE", 10000, NULL, 3, NULL);
   xTaskCreate(&taskHumidity, "tarea4 HUMIDITY", 10000, NULL,3, NULL);
   xTaskCreate(&taskTemp, "tarea5 TEMPERATURE", 10000, NULL, 3, NULL);


// se crea el semáforo binario
   xSemaphore_Noise = xSemaphoreCreateBinary();
   xSemaphore_PaintLeds = xSemaphoreCreateMutex();

// Creacion Tareas de interrupciones
   xTaskCreate(&interr_task_noise, "Interrupcion Ruido", 2048, NULL, 1, NULL);
// Idle task hook
    esp_register_freertos_idle_hook(IddleHook);
   
}
/* Funcion IdleHook */
bool IddleHook(void){
     
if(analogRead(sensorLUM) > 4000){
    xSemaphoreGive(xSemaphore_Noise);
    
  }
  return true;
}

void pintarUltimoValor(){
  int limit;
  int value;
  
  if(contador_sensores == 0){
    Serial.println("****CO2****");
   limit = 4095;
   value = lastVal_CO2;
  }
  if(contador_sensores== 1){
    Serial.println("****NOISE****");
     limit = 4095;
   value = lastVal_Noise;
  }
  if(contador_sensores== 2){
    Serial.println("****HUMIDITY****");
     limit = 100;
   value = lastVal_Humidity;
  }
  if(contador_sensores== 3){
    Serial.println("****TEMP****");
     limit = 50;
   value = lastVal_Temp;
  }
   pixels.clear();
      
      int bright_value = (value * 255) / limit;
      int pin_value = round((value * 24) / limit);
      
      for(int i=0;i<pin_value;i++)
      {
        if(bright_value >= 125) pixels.setPixelColor(i, pixels.Color(255, 0, 0));
        else pixels.setPixelColor(i, pixels.Color(0, 255, 0));
      }

      pixels.setBrightness(bright_value);
      pixels.show();   

  
}
int ISR_MQTT = 0;
void loop() 
{
  mqttClient.poll();

  unsigned long currentMillis = millis();
  
   // Variable que detecta si la interrupcion ha sido producida
   if (movimiento){   
      movimiento= 0;
      delay(3000);
   }
   else if(movimiento == 0){
    if(ISR_MQTT == 0){
       digitalWrite(LED_PIR,LOW);
    }
     
   }

   if(ISR_MQTT == 1){
    digitalWrite(LED_PIR,HIGH);
   }
   if(ISR_MQTT == 0){
    digitalWrite(LED_PIR,LOW);
   }
     
  int limit;
  int value;
  
  if(contador_sensores == 0){
    Serial.println("****CO2****");
   limit = 4095;
   value = lastVal_CO2;
   
  }
  if(contador_sensores== 1){
    Serial.println("****NOISE****");
     limit = 4095;
   value = lastVal_Noise;
  }
  if(contador_sensores== 2){
    Serial.println("****HUMIDITY****");
     limit = 100;
   value = lastVal_Humidity;
  }
  if(contador_sensores== 3){
    Serial.println("****TEMP****");
     limit = 50;
   value = lastVal_Temp;
  }

  //mensaje envio pantalla lcd udp
      char texto[100];
      sprintf (texto, "Sensor %d : valor %d " ,contador_sensores,value);
      
      //Send broadcast on port 1234
      udp.broadcastTo(texto,1234);
  if (xSemaphoreTake( xSemaphore_PaintLeds, portMAX_DELAY )){
   pixels.clear();
      
      int bright_value = (value * 255) / limit;
      int pin_value = round((value * 24) / limit);
      
      for(int i=0;i<pin_value;i++)
      {
        if(bright_value >= 125) pixels.setPixelColor(i, pixels.Color(255, 0, 0));
        else pixels.setPixelColor(i, pixels.Color(0, 255, 0));
      }

      pixels.setBrightness(bright_value);
      pixels.show(); 

      

      xSemaphoreGive(xSemaphore_PaintLeds);  
  }
  contador_sensores++;

  if(contador_sensores == 4){
    contador_sensores = 0;
  }
vTaskDelay(4000 / portTICK_PERIOD_MS);

}

void sendMqtt(String sensor,int v)
{
  String topicSend  = String("equipo1-2/murbin/sensores/")+String(sensor);

  mqttClient.poll();
  
  // avoid having delays in loop, we'll use the strategy from BlinkWithoutDelay
  // see: File -> Examples -> 02.Digital -> BlinkWithoutDelay for more info
  unsigned long currentMillis = millis();
  if (currentMillis - previousMillis >= interval) {
    // save the last time a message was sent
    previousMillis = currentMillis;

    Serial.println("Sending message to topic: ");
    Serial.println(topicSend);
    Serial.print("value: ");
    Serial.print(v);

    // send message, the Print interface can be used to set the message contents
    mqttClient.beginMessage(topicSend);
    mqttClient.println(v);
    mqttClient.endMessage();

    Serial.println(" ");
    Serial.println(" ");
  }
}

//recieving mqtt message
void onMqttMessage(int messageSize) {
 // we received a message, print out the topic and contents
  String topic = mqttClient.messageTopic();
  String payload = mqttClient.readString();
  Serial.println("incoming: " + topic + ", length: " + messageSize + " ");
  Serial.println(payload);
  if (payload == "ON") {
    Serial.println("Emcendiendo farola..");
    ISR_MQTT = 1;
    digitalWrite(LED_PIR,HIGH);
  } else if (payload == "OFF") {
    Serial.println("Apagando farola..");
    ISR_MQTT = 0;
    digitalWrite(LED_PIR,LOW);
  } else if (payload == "TOGGLE") {
    if(ISR_MQTT == 1){
      Serial.println("Apagando farola..");
      ISR_MQTT = 0;
      digitalWrite(LED_PIR,LOW);
    }else if(ISR_MQTT == 0){
      Serial.println("Emcendiendo farola..");
      ISR_MQTT = 1;
      digitalWrite(LED_PIR,HIGH);
    }
  }
  Serial.println();

  Serial.println();
}

/*********************************/
/*  Definiendo INTERRUPCION PIR  */
/*********************************/
void  IRAM_ATTR detectarMovimiento(void* arg)
{
  movimiento=1;
  digitalWrite(LED_PIR,HIGH);
  Serial.println(" -- Movimiento detectado --"); 
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

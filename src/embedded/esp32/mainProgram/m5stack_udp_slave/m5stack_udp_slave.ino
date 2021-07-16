#include "WiFi.h"
#include "AsyncUDP.h"
#define BLANCO 0XFFFF
#define NEGRO 0
#define ROJO 0xF800
#define VERDE 0x07E0
#define AZUL 0x001F
#include <M5Stack.h>

const char * ssid = "Yeraysa-WLAN";
const char * password = "12345678";
char texto[100];

boolean rec=0;
AsyncUDP udp;

void setup()
{
  M5.begin();
  M5.Lcd.setTextSize(2); //Tamaño del texto
  Serial.begin(115200);
  WiFi.mode(WIFI_AP);
  WiFi.begin(ssid, password);
  
  if (WiFi.waitForConnectResult() != WL_CONNECTED) {
    Serial.println("WiFi Failed");
    while(1) {
      delay(1000);
      }
  }
  if(udp.listen(1234)) {
    Serial.print("UDP Listening on IP: ");
    Serial.println(WiFi.localIP());
    udp.onPacket([](AsyncUDPPacket packet) {
    int i=100;
    while (i--) {*(texto+i)=*(packet.data()+i);}
    rec=1; //indica mensaje recibido
    });

     
  }
}
void loop()
{
  if (rec){
    //Send broadcast
    rec=0; //mensaje procesado
    Serial.println (texto);
    //mandar a M%Stack
    M5.Lcd.fillScreen(NEGRO); //borrar pantalla
    M5.Lcd.setCursor(0, 10); //posicion inicial del cursor
    M5.Lcd.setTextColor(BLANCO); //color del texto
    M5.Lcd.print(texto);
  }
}

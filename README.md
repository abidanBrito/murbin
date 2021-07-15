![WIP_Badge](https://img.shields.io/badge/version-0.1-blue.svg)

![Project_Logo](/img/murbin_logo.png)

## TABLE OF CONTENTS
1.  [Motivation](#motivation)
2.  [Technologies and Frameworks](#technologies-and-frameworks)
3.  [System Architecture](#system-architecture-outdated-diagram)
4.  [Functionalities and Features](#functionalities-and-features)
5.  [Documentation](#documentation)
6.  [Acknowledgements](#acknowledgements)

## MOTIVATION
This project is a team effort for our third semester class **Internet of Things** (IoT). It aims to implement **smart streetlighting** in a way that is **not only efficient**, but also provides some **added functionalities** and interaction with the user via an **Android App**.

## TECHNOLOGIES AND FRAMEWORKS
### Electronic Devices and Components
*   **Raspberry Pi 3 B**.
*   **ESP32 Devkit v1 WiFi + Bluetooth**.
*   **M5Stack** Modular Rapid Prototyping.
*   MQ-135 **Gas Sensor** Module.
*   DHT11 **Temperature and Humidity Sensor** Module.
*   KY-038 **Microphone Sound Sensor** Module.
*   HC-SR501 **PIR Sensor** Module. 
*   **Sonoff Touch** with Tasmota Firmware.
*   Operational Amplifiers.
*   Yxo Yuxinou **LED COB Chip** (220-265V/AC, 20W, 3500k/450nm/380-780nm).
*   NeoPixel Ring - **24 x 5050 RGB LED** with Integrated Drivers
*   Photodiode.
*   Resistors.
*   Relay.

### Mobile / Cloud Platforms & Protocols
*   **Android Things** embedded Operating System.
*   **Firebase**. 
*   **MQTT**.
*   **UDP**.

### Software Development 
*   Arduino IDE. 
*   Android Studio.
*   FreeRTOS.   

## SYSTEM ARCHITECTURE (OUTDATED DIAGRAM)
![System Architecture Diagram](img/system_architecture_diagram.png)
Refer to the [documentation](docs/Documento_Técnico_Diseño_v3.pdf) (spanish) for further details and schematics.

## FUNCTIONALITIES AND FEATURES 
-   [x] High **CO2 and Pollution** Detection.
-   [x] **Temperature and Humidity** Sensor.
-   [x] **Noise** Sensor.
-   [x] **Motion Detection**.
-   [x] **Luminosity** Sensor.
-   [x] **Flashing LED** alarms.
-   [x] Interactive **Mobile App** (ON/OFF switch, sensor readings, geolocation, map subzones and different user roles).
-   [x] **Light Sleep** Mode.
-   [x] **PCB** design.
-   [ ] Dimming LED.

## DOCUMENTATION
It can be found in the form of a PDF file for each and every Sprint, accordingly. Those of previous Sprints may contain errors (schematics, pins, etc.). Please, refer to the lastest version: [technical documentation](docs/Documento_Técnico_Diseño_v3.pdf) (spanish). 

## ACKNOWLEDGEMENTS
We would like to express our sincere gratitude to our **professors** for their invaluable support, feedback and encouragement throughout the development of this project, and to **Universitat Politècnica de València** for providing us with both the knowledge and the tools needed to complete it.

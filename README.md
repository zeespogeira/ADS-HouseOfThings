# ADS-HouseOfThings

## 1. Introduction

ADS-HouseOfThings is a software system designed to control 
 sensors and actuators, and to manage and automate tasks involving
  such devices. The software works with simulated devices,
  however the software was designed in a way to be easily
   extended to work in real-world applications.

## 2. Goals

The system shall:
- Support multiple devices, including multiple types of
 sensors, actuators and hubs;
- Support adding new devices easily;
- Support adding triggers and actions through a user interface;
- Allow monitorization of the system (sensors and 
actuators)  through the user interface;
- Work in simulated mode with simulated (software) devices;
- Be easy to integrate with well-known systems, such as, SMS,
 Slack, WhatsApp, and other communication systems.

## 3. Design 
This section explains the design choices of the various
 features developed in this project.

### 3.1. Discoverability 

To control sensors and actuators, we first need to have such
devices available. This is achieved through a process we 
named Discoverability, which enables the system to identify
sensors and actuators that it can use.


Considering the system works in simulated mode, sensors and
actuators available for discovery are listed in a .csv file
with the following structure:

    device_type, device_subtype, device_brand

For exemple: 

    sensor, temperature, bosch


Each .csv file corresponds to a single device. The .csv 
files are stored in a folder named Devices. 
The system will automatically detect if there are changes
to the Devices folder (e.g. when adding a new .csv file)
and create the corresponding device object.


For the creation of the devices, we
resorted to a Factory Method. This pattern was chosen
because ...


![alt text](https://github.com/zeespogeira/ADS-HouseOfThings/blob/main/documentation/images-exports/Devices%20Discovery.png?raw=true)

### 3.2. Sensor Infrastructure

#### 3.2.1. Sensor Infrastructure
![alt text](https://github.com/zeespogeira/ADS-HouseOfThings/blob/main/documentation/images-exports/infra-diagrams-infra-sensors.png?raw=true)

We built a model to support the "sensor domain". This domain represents the real world interactions of sensors within the systems. In theis model we ideintified a problem related to how would the sensors communicate to the "rest of the world" 
that a new reading was produced. In order to tackle the problem, we chose to include a component that would be responsible to aggregate all sensor readings. Once a reading is produced by the sensor, it should be stored in a single common "place". This
place would then be responsible for notifying all the interested entities.

#### 3.2.2 Actuator Infrastructure
![alt text](https://github.com/zeespogeira/ADS-HouseOfThings/blob/main/documentation/images-exports/infra-diagrams-infra-actuators.png?raw=true)


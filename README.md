# ADS-HouseOfThings

## Table of Contents
[Introduction](#Introduction)

[Goals](#Goals)

[Domain model](#Domain model)

[Design](#Design)
* [Discoverability](#Discoverability) 

<br>

## 1. Introduction

ADS-HouseOfThings is a software system designed to control 
 sensors and actuators, and to manage and automate tasks involving
  such devices. The software works with simulated devices,
  however the software was designed in a way to be easily
   extended to work in real-world applications.

## 2. Goals

The system shall:
- Support multiple devices, for instance types of
 sensors and actuators;
- Support adding new devices easily;
- Support adding triggers and actions through a user interface;
- Allow monitorization of the system (sensors and 
actuators) through the user interface;
- Work in simulated mode with simulated (software) devices;
- Be easy to integrate with well-known systems, such as, SMS,
 Slack, WhatsApp, and other communication systems.


## 3. Domain model


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

For Actuators:

    device_type, device_subtype, device_brand, space

For example: 

    Actuator, Curtain, Sony, Bedroom
    
    
    
For Sensors:

    device_type, device_subtype, device_brand, what_is_mesuring, space

For example: 

    Sensor, Thermometer, Bosch, Realfeel, Bedroom


Each .csv file corresponds to a single device. The .csv 
files are stored in a folder named Devices. 
The system will automatically detect if there are changes
to the Devices folder (e.g. when adding a new .csv file)
and create the corresponding device object.

#### 3.1.1. Used patterns

![alt text](https://github.com/zeespogeira/ADS-HouseOfThings/blob/main/documentation/images-exports/Devices%20Discovery.png?raw=true)

#### 3.1.1.1. Microkernel
- **Problem in Context**
    - Our problem was how to instantiate actuators and sensors in run-time without having a timer cheking the folder x in x seconds.
- **The Pattern**

- **Implementation**
    - For this implementation we decided to use a class called "Discovery Module". This class has a method called processEvents() that is always running in bachground to see if new files (actuators or sensors) are instantiated.

- **Consequences**
    - We're using this pattern to help with the plug & play
https://github.com/zeespogeira/ADS-HouseOfThings/blob/main/src/ads.group06.houseofthings/src/main/java/DiscoveryModule.java

### 3.2. Sensor Infrastructure
#### 3.2.1. Domain model
![alt text](https://github.com/zeespogeira/ADS-HouseOfThings/blob/main/documentation/images-exports/infra-diagrams-infra-sensors.png?raw=true)

We built a model to support the "sensor domain". This domain represents the real world interactions of sensors within the systems. In this model we identified a problem related to how would the sensors communicate to the "rest of the world"
that a new reading was produced. To tackle the problem, we chose to include a component that would be responsible to aggregate all sensor readings. Once a reading is produced by the sensor, it should be stored in a single common "place". This
place would then be responsible for notifying all the interested entities.

#### 3.2.1. Used patterns
#### 3.2.1.1. Singleton
- **Problem in Context**
    - We identified a problem related to how would the sensors communicate to the "rest of the world" when a new reading was produced. To tackle the problem, we chose to include a component, SensorReadingHub, that would be responsible to aggregate all sensor readings. Once a reading is produced by the sensor, it should be stored in a single common "place". This place would then be responsible for notifying all the interested entities
    - How can we make sure that when a Sensor publishes a reading, the reading is stored in an common object to all sensors?
- Pattern description
    - The pattern enforces the existence of a single instance of a class.
- Usage
    - By using this pattern that all the readings are stored in a single object and we can react to the "new reading" by notifying all the "sensor reading dependants". The Hub class is a singleton class.

#### 3.2.1.1. Observer
- Problem
    - How can we make sure that when a Sensor publishes a reading all the "sensor dependants" act accordingly?
- Pattern description
    - The pattern defines a mechanism to notify multiple objects about events that happen on the observed object.
- Usage
    - Using this pattern allowed us to implement a mechanism in a single component that notifies all the objects that need to act after a sensor emits a reading. The pattern is implemented in the Hub class.

### 3.3. Actuator Infrastructure
![alt text](https://github.com/zeespogeira/ADS-HouseOfThings/blob/main/documentation/images-exports/infra-diagrams-infra-actuators.png?raw=true)

Actuators are entities that need to be triggered once a condition or set of conditions are met. Both actuators and conditions are independent entities that should not know each other. Following this principle, we should create a mechanism that mediates the action of an actuator given some condition(s). We came up with the concept os an Action which is the entity responsible to check if a set of conditions are met and triggers (on/off) a set of actuator(s).

#### 3.3.1. Used patterns
#### 3.3.1.1. Mediator
. Problem 
    - How can an actuator be triggered when a set of conditions are met?
- Pattern description
    - The pattern reduces dependencies between objects. It restricts direct communication of the objects and enforces the communication only via the mediator object. 
- Usage
    - The Action object serves as the mediator between Conditions and Actuators. When an Action is notified to execute, then checks the state of all of its conditions and signals the actuators to act.

#### 3.3.1.2. Factory Method
- **Problem in Context**
    - How can we remove the complex chainning of "if conditions" needed to compare the sensor value with the Condition value?
- Pattern
    - The patter provides an object responsible for deciding the instantiation of the correct implementation.
- Usage
    - We introduced the interface IComparer that is implemented by any class responsible for performing comparisons. A ComparerFactory was added, with the responsibility to decide which of of the IComparer implementation should be instantiated. 

#### 3.3.1.3. Command Pattern
- **Problem in Context**
  - For our actuators to act, we needed some way for our method to be called without the class that calls it knowing what was within the method it was calling.
  
- **The Pattern**
    - This pattern allows the Action class to call the method act, from the actuators class, without knowing what the act truly does.
    
- **Implementation**
    - To implement this pattern we create an abstract method, act, in abstract class AbstractActuators. The actuator subclasses then implement this method as they see fit.
 Then, the class Action calls the act method.
  
- **Consequences**
    - As a consequence of this pattern, in the very least, all the types of actuators need to implement the act method.
    
    
### 3.2. Sensor Infrastructure
#### 3.3.1.2. Factory Method
- **Problem in Context**
    - How can we remove the complex chainning of "if conditions" needed to compare the sensor value with the Condition value?
- Pattern
    - The patter provides an object responsible for deciding the instantiation of the correct implementation.
- Usage
    - We introduced the interface IComparer that is implemented by any class responsible for performing comparisons. A ComparerFactory was added, with the responsibility to decide which of of the IComparer implementation should be instantiated. 

# ADS-HouseOfThings

## Table of Contents
[Introduction](#Introduction)

[Goals](#Goals)

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

The system should:
- Support multiple devices, for instance types of
 sensors and actuators;
- Support adding new devices easily;
- Support adding triggers and actions through a user interface;
- Allow monitorization of the system (sensors and 
actuators) through the user interface;
- Work in simulated mode with simulated (software) devices;
- Be easy to integrate with well-known systems, such as, SMS,
 Slack, WhatsApp, and other communication systems.

## 3. Design 
This section explains the design choices of the various
 features developed in this project.
 
### 3.1. Domain

The main components of our system are described in the image below.
- The Sensor represents a device that is able to measure some
quantifiable quality. 
- The Actuator represents a device that is able to perform 
some action.
- The Condition component represents the rules that should trigger an action.
- The DiscoveryModule component represents the ability of the system
to automatically identify new devices (plug-n-play).
- The Hub component represents a central location that aggregates
 sensor information and triggers notifications based on that information.    

![alt text](https://github.com/zeespogeira/ADS-HouseOfThings/blob/main/documentation/images-exports/diagrams-Domain.png?raw=true)


### 3.2. The first design problem: Plug-n-Play and Discoverability

To control sensors and actuators, we first need to have such devices available. 
This is achieved through a process we named Discoverability, which enables 
the system to identify devices that it can use, such as sensors and actuators.

We defined two base types of devices: sensors and actuators. These two base
types can be combined to achieve more complex configurations of devices that
include one or more sensors or actuators – these are named modules. 

An example of a module would be a combi-refrigerator with a water dispensing system,
which can be described as 3 actuators (the refrigerator, the freezer, and the 
water dispensing system) and 4 sensors (a temperature sensor in the refrigerator,
a temperature sensor in the freezer, a temperature sensor in the water dispending
system, and a proximity sensor used to light the HMI). 

Considering the system works in simulated mode, sensors and actuators available for
discovery are listed in a .csv file. Each .csv file represents a module, with 
each line of the .csv representing a device in that module. In the limit, a 
module with only one device corresponds to the device itself. 

The .csv structure is the following:

**For Actuators:**

    device_type, device_subtype, device_brand, space

Example: 

    Actuator, Curtain, Sony, Bedroom
    
<br>
    
**For Sensors**:

    device_type, device_subtype, device_brand, what_is_mesuring, space

Example: 

    Sensor, Thermometer, Bosch, Realfeel, Bedroom


Each .csv file corresponds to a module. 
The .csv files are stored in a folder named Devices. 
The system will automatically detect if there are changes to the Devices
folder (e.g. when adding a new .csv file) 
and create the corresponding device(s) object(s).

- **Problem in Context**
    - How to instantiate actuators and sensors
    in run-time without having a timer cheking the folder periodically?

- **Implementation**
    - We decided to use a class *DiscoveryModule*. This class 
    has a method nammed *processEvents()* that runs in the background 
    that evaluates if new .csv files are added to the Devices folder, and 
    instantiates the corresponding devices listed in each .csv file.

- **Consequences**
    - This design decision enables the "plug & play" ability of our system, 
    since it becomes able to automatically detect new devices.
    
    
    
- *DiscoveryModule* class: https://github.com/zeespogeira/ADS-HouseOfThings/blob/main/src/ads.group06.houseofthings/src/main/java/DiscoveryModule.java

    

#### 3.2.1. Devices domain 

The two base types of devices our controller supports (sensors and actuators) are 
implementations of two abstract classes: *AbstractSensor* and *AbstractActuator*, 
respectively. This is illustrated in the following diagram:  

![alt text](https://github.com/zeespogeira/ADS-HouseOfThings/blob/main/documentation/images-exports/diagrams-SensorsActuators.png?raw=true)


### 3.3. Patterns

This section presents the patterns used in this project, including a description
of the problem that led to the use of the pattern, details about its implementation,
and consequences.

#### 3.3.1. Singleton
- **Problem in Context**
    - We identified a problem related to how would the sensors communicate to the "rest of the world" when a new reading was produced. To tackle the problem, we chose to include a component, SensorReadingHub, that would be responsible to aggregate all sensor readings. Once a reading is produced by the sensor, it should be stored in a single common component. This component would then be responsible for notifying all the entities interested in sensor information.
    - How can we make sure that when a Sensor publishes a reading, the reading is stored in an common object to all sensors?
- **The Pattern**
    - By using the Singleton pattern we enforce that all the readings are stored in a single object and we can react to the "new reading" by notifying all the entities that depend on a sensor reading.
    
- **Implementation**
    - To implement this pattern we created a class HubProvider, that has a static SensorReadingsHub that can only be instantiated once. To garanteed that that is true we have a method getReadingHub() that checks if the hub was already instantiated, and if it was returns it.
    Then, in our main class we instantiate a SensorReadingsHub that calls the HubProvider method getReadingHub().
- **Consequences**
    - As a consequence of the use of this pattern, we have at most one hub for sensor readings.
    
    ![alt text](https://github.com/zeespogeira/ADS-HouseOfThings/blob/main/documentation/images-exports/diagrams-Singleton.png?raw=true)

    
#### 3.3.2. Observer
- **Problem in Context**
    - How can we make sure that when a Sensor publishes a reading all entities that depend on that reading act accordingly?
    - When a sensor publishes a reading we need to make sure that all actuators that depend from that sensor act accordingly to the action defined.
- **The Pattern**
    - The pattern observer defines a mechanism to notify multiple objects about events that happen on the observed object.
    - Specifically, when a new reading is defined, the actuators dependants from that reading are warned to act.
- **Implementation**
    - Using this pattern allowed us to implement a mechanism in a single component that notifies all the objects that need to act after a sensor emits a reading. The pattern is implemented in the Hub class.
    - In that class, we have a method **notifyActions** that sees the actions that are dependent of that sensor and tells them to execute it's behavior (in this case, call the method act from the actuators).
- **Consequences**
    - As a consequence of the use of this pattern we know are capable to say to the actuators to act when necessary, with a minimal amount of code.
 
     ![alt text](https://github.com/zeespogeira/ADS-HouseOfThings/blob/main/documentation/images-exports/diagrams-Observer.png?raw=true)
   

#### 3.3.3. Mediator
- **Problem in Context**
    - How can an actuator be triggered when a set of conditions are met?
    - One problem we had was how to trigger an actuator after its conditions were met.
    - We have the classes to implement the actuators and the class to implement the conditions, but we needed another class to handel their communication.
- **The Pattern**
    - This pattern reduces dependencies between objects. It restricts direct communication of the objects and enforces the communication only via the mediator object.
    - In this case, we created the Action class that is instantiated with, between other things, a list of actions and a list of conditions. This class will then be responsable for making the actuators act when the conditions are true.  
- **Implementation**
    - The Action object serves as the mediator between Conditions and Actuators. When an Action is notified to execute, it checks the state of all of its conditions and signals the actuators to act.
- **Consequences**
    - This way we can make the actuators to act . 

    ![alt text](https://github.com/zeespogeira/ADS-HouseOfThings/blob/main/documentation/images-exports/diagrams-Mediator.png?raw=true)
 

#### 3.3.4. Command
- **Problem in Context**
  - For our actuators to act, we needed some way for our method to be called without the class that calls it knowing what was within the method it was calling.
  
- **The Pattern**
    - This pattern allows the Action class to call the method act, from the actuators class, without knowing what the act truly does.
    
- **Implementation**
    - To implement this pattern we create an abstract method, act, in abstract class AbstractActuators. The actuator subclasses then implement this method as they see fit.
 Then, the class Action calls the act method.
  
- **Consequences**
    - As a consequence of this pattern, in the very least, all the types of actuators need to implement the act method.
    
    ![alt text](https://github.com/zeespogeira/ADS-HouseOfThings/blob/main/documentation/images-exports/diagrams-Command.png?raw=true)



#### 3.3.5. Factory Method
- **Problem in Context**
    - To compare the sensor value with the condition value we have various options. The conditions can be, for instance, lower than or equal to. Our problem was how to remove the complex chainning of "if conditions" in our class to create a more efficient, organized and easy to read code.
    - We need to compare the sensor value with the condition value  
- **The Pattern**
    - The Factory Method pattern provides an object (*ComparerFactory*) responsible for deciding the instantiation of the correct implementation.
- **Implementation**
    - We introduced the interface *IComparer* that is implemented by any class responsible for performing comparisons. A *ComparerFactory* was added, with the responsibility to decide which of of the *IComparer* implementation should be instantiated. 
- **Consequences**
    - As a consequence of the use of this pattern we have a more organized and easy to understand code. 
    - Right now we only need to call the *Comparer* class and send the values we want to compare and the class decides which one to implement.
    
    ![alt text](https://github.com/zeespogeira/ADS-HouseOfThings/blob/main/documentation/images-exports/diagrams-Factory%20Method.png?raw=true)

    
#### 3.3.6. Template Method
- **Problem in Context**
    - Problem 1
        - We needed to be able to have various types of the "same" actuator/sensor. An example is the *Lamp* and *LampBosch* and *LampPhilips*, which represent a generic lamp, and two specific lamps that may have different logic. 
        - More specifically, a basic type actuator (Example: *Lamp*) only has a state (on/off) and if the lamp from the brand Bosch also has a feature for intensity, it can override the act method and change the intensity value.
        - The sensors are implemented in a similar fashion.
    
    - Problem 2 
        - We needed to force the concrete sensors/actuators to follow a basic structure.
        - For the sensors we needed them to follow the structure from the *AbstractSensor* class and to specify it's fields in the subclasses.
        - The actuators the same thing, but with the class *AbstractActuator*.
    
    - Problem 3
        - We also needed to have unique ID for all actuators/sensors. For that we implemented our template method, *setID()*,  in classes  *AbstractSensor* and *AbstractActuators*. This way all their subclasses have a unique id, that cannot be changed and can be referenced from other classes.
       
- **The Pattern**
    - Problem 1
        - The pattern provides a way for the actuators and sensors to have an option to specify its implementation, if necessary.
        - For instance, using the hook method *act* a subclass can override the act method if it has more options.
    - Problem 2
        - We're defining the methods for the ID in the abstract class, so they are the template method. The hook methods would be the getReadings, por example.
        - That getReadings then would be a template method in the Humidity class, for example.
    - Problem 3
        - We implemented the setID() in the abstractActuator and AbstractSensor and when its subclasses are instantiated, the ID is set and incremented.
- **Implementation**
    - We create an abstract class (general actuator) that implements the act method (overriding it from its also abstract superclass(*AbstractActuator*)). It's subclasses can then override it if they need to.
    - For the sensors, we have the *AbstractSensor* class that has the abstract method *sense*, that will be implemented in the abstract type class (Ex: *Humidity*) and override it in the subclasses.
    - Another example is the *getID* from the classes. It is defined in *AbstractSensor/AbstractActuator*.
- **Consequences**
    - As a consequence of the use of this pattern we are able to specify the actuator/sensor behaviour if needed.

    ![alt text](https://github.com/zeespogeira/ADS-HouseOfThings/blob/main/documentation/images-exports/diagrams-Template.png?raw=true)

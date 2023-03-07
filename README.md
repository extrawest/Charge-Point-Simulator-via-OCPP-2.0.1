<a href="https://www.extrawest.com/"><img src="https://drive.google.com/uc?export=view&id=1kXfNj5WfW2oSMzQR82xYBI6Bw_W8-LpK" width="20%"></a>
# Charge Point Simulator via OCPP 2.0.1

"Charge Point Simulator via OCPP 2.0.1" is an application that simulates multiple charge points based on OCPP 2.0.1 protocol.

This project is packaged as a Docker image, making it easy to deploy and run on any platform that supports Docker.

## Badges
![contr](https://img.shields.io/github/contributors/extrawest/Charge-Point-Simulator-via-OCPP-2.0.1?style=for-the-badge)
![commits](https://img.shields.io/github/commit-activity/m/extrawest/Charge-Point-Simulator-via-OCPP-2.0.1?style=for-the-badge)
![lastcommit](https://img.shields.io/github/last-commit/extrawest/Charge-Point-Simulator-via-OCPP-2.0.1?style=for-the-badge)
![OCPP](https://img.shields.io/badge/OCPP-2.0.1-yellowgreen?style=for-the-badge)
![JDK](https://img.shields.io/badge/JDK-17-yellow?style=for-the-badge)
![social](https://img.shields.io/github/forks/extrawest/Charge-Point-Simulator-via-OCPP-2.0.1?style=for-the-badge)

## Field of use

Java simulator of OCPP 2.0 charge points is a software application that is designed to mimic the behavior of electric vehicle charging stations (charge points) that conform to the OCPP 2.0 protocol. This simulator is important for several reasons:

**Testing**: A Java simulator of OCPP 2.0 charge points is an important tool for testing and validating OCPP 2.0-based charge point management systems. By simulating multiple charge points with different configurations and behaviors, developers can test their systems under a variety of conditions and ensure that they are functioning properly.

**Training**: The simulator can also be used for training purposes, allowing technicians and support staff to become familiar with the behavior and configuration of OCPP 2.0-based charge points before deploying them in the field.

**Standardization**: OCPP 2.0 is a widely adopted standard for communication between electric vehicle charging stations and central management systems. By providing a Java simulator of OCPP 2.0 charge points, developers can ensure that their systems are compatible with this widely adopted standard.

**Flexibility**: The simulator can be configured to simulate different types of charging stations with different capabilities and configurations, allowing developers to test their systems under a variety of conditions.

Overall, a Java simulator of OCPP 2.0 charge points is an important tool for developers, technicians, and support staff working with electric vehicle charging systems. It helps ensure that these systems are reliable, secure, and compatible with the OCPP 2.0 standard.


## Technologies used
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Jetty](https://www.eclipse.org/jetty/)
- [Docker](https://docs.docker.com/)
- [Picocli](https://picocli.info/)


## Requirements
- Java 17 or higher
- Maven 3.6 or higher
- Docker 17.05 or higher
## Features

ChargePoints simulator sends BootNotifications from chargers.
If it's successefull then will send Heartbeats from each charger.

After that 10% of connected charge points starts a transactions (StartTransaction, MeterValues, StopTransaction).

The main idea - using this utility for load testing of Central System
## Environment Variables

To run this project, you will need to add the following environment variables to your .env file

`CENTRAL_SYSTEM_URL` - the address of the service that will process all requests from charging stations

`CP_COUNT` - number of stations to be launched



## Quick Start

### Run Local
```bash
$ java -jar ocpp-charge-point-emulator-cli.jar -C $CENTRAL_SYSTEM_URL -S $CP_COUNT
```


### Run Docker

```bash
$ docker run --sysctl net.ipv4.ip_local_port_range="1024 65535" --rm -e ATTACH_JFR=true -v D:\:/jfr -e SPRING_PROFILES_ACTIVE=dev ghcr.io/extrawest/charge-point-simulator-via-ocpp-2.0.1:master --csUrl $CENTRAL_SYSTEM_URL -S $CP_COUNT
```

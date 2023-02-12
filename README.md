<h1 align="center"> Charge points emulator </h1> <br>

<p align="center">
  This is a emulator for huge numbers of charge points, that connect to central system through web socket and implement OCPP 2.0.1
</p>


## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Requirements](#requirements)
- [Quick Start](#quick-start)



## Introduction

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/e91606af4a364076a7058c5ea1c006a8)](https://www.codacy.com/app/joneubank/microservice-template-java?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=overture-stack/microservice-template-java&amp;utm_campaign=Badge_Grade)
[![CircleCI](https://circleci.com/gh/overture-stack/microservice-template-java/tree/master.svg?style=shield)](https://circleci.com/gh/overture-stack/microservice-template-java/tree/master)

## Features
ChargePoints emulator sends BootNotifications from chargers. If it's successefull than will send Heartbeats from each charger. After that 10% of connected charge points starts a transactions. (StartTransaction, MeterValues, StopTransaction)
The main idea - using this utility for load testing of Central System


## Requirements
The application can be run locally or in a docker container, the requirements for each setup are listed below.


### Local
* [Java 8 SDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Maven](https://maven.apache.org/download.cgi)


### Docker
* [Docker](https://www.docker.com/get-docker)


## Quick Start

### Run Local
```bash
$ java -jar ocpp-charge-point-emulator-cli.jar -C "central system url" -S "count of charge points" -L "logging level"
```


### Run Docker

First build the image:
```bash
$ docker build -t gitlab.extrawest.com:5050/i-ocpp/ocpp-station-emulation .
```

When ready, run it:
```bash
$ docker run --sysctl net.ipv4.ip_local_port_range="1024 65535" --rm -e ATTACH_JFR=true -v D:\:/jfr -e SPRING_PROFILES_ACTIVE=dev gitlab.extrawest.com:5050/i-ocpp/ocpp-station-emulation --csUrl "central system url" "count of charge points" -L "logging level"
```


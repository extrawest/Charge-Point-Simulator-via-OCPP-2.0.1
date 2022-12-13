#!/bin/bash
# TODO: move the content of this file to README.MD

docker run\
 --sysctl net.ipv4.ip_local_port_range='1024 65535'\
 -e SPRING_PROFILES_ACTIVE=dev\
 --add-host host.docker.internal:host-gateway\
 gitlab.extrawest.com:5050/i-ocpp/ocpp-station-emulation --csUrl "ws://host.docker.internal:3000/ocpp" --stationCount 64511

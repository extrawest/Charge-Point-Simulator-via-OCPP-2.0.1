FROM openjdk:17-alpine

COPY target/ocpp-charge-point-emulator-cli.jar ocpp-charge-point-emulator-cli.jar
COPY ./entrypoint.sh /
RUN chmod +x /entrypoint.sh
ENTRYPOINT ["/entrypoint.sh"]

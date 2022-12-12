FROM openjdk:17-alpine

ARG JAR_FILE=target/ocpp-charge-point-emulator-cli.jar
COPY ${JAR_FILE} ocpp-charge-point-emulator-cli.jar
ENTRYPOINT ["java","-jar","/ocpp-charge-point-emulator-cli.jar"]

FROM --platform=linux/amd64 openjdk:21-slim
ARG JAR_FILE=target/wms.jar
#ARG PROFILE=default
ENV PROFILE="default"
COPY ${JAR_FILE} wms.jar
#ENTRYPOINT ["java", "-jar", "/wms.jar", "echo ${PROFILE}", "--spring.profiles.active=${PROFILE}"]
ENTRYPOINT java -jar /wms.jar --spring.profiles.active=${PROFILE}
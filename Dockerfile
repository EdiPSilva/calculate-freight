FROM openjdk:17
MAINTAINER Edi Silva
COPY build/libs/*.jar calculate-freight.jar
EXPOSE 8081
CMD ["java", "-jar", "calculate-freight.jar"]
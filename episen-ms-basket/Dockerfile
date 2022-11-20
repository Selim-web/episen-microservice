FROM adoptopenjdk/openjdk11

MAINTAINER Selim BOUHASSATINE

VOLUME ["/ms-basket"]

COPY target/episen-ms-basket-1.0.0-SNAPSHOT.jar /ms-basket/ms-basket-1.0.0.jar

ENTRYPOINT ["java","-jar","/ms-basket/ms-basket-1.0.0.jar"]

EXPOSE 8090
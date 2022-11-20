FROM adoptopenjdk/openjdk11

MAINTAINER Selim BOUHASSATINE

VOLUME ["/ms-membership"]

COPY target/episen-ms-membership-1.0.0-SNAPSHOT.jar /ms-membership/ms-membership-1.0.0.jar

ENTRYPOINT ["java","-jar","/ms-membership/ms-membership-1.0.0.jar"]

EXPOSE 8070


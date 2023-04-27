FROM openjdk:11
ADD target/workerMachine-0.0.1-SNAPSHOT.jar wm.jar
ENTRYPOINT ["java","-jar","wm.jar"]
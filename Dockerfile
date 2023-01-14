FROM maven:3.8.5-openjdk-18-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
#RUN mvn -f /home/app/pom.xml clean package -DskipTests
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:18-alpine
COPY --from=build /home/app/target/filemanager-0.0.1-SNAPSHOT.jar /usr/local/lib/filemanager.jar
EXPOSE 8997
ENTRYPOINT ["java","-jar","/usr/local/lib/backend.jar"]

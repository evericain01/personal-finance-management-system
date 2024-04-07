FROM openjdk:19-jdk
WORKDIR /app
EXPOSE 8080
COPY target/PFMS-1.0-SNAPSHOT.jar /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]

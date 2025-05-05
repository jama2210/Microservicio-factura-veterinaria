FROM openjdk:21-ea-24-oracle 
WORKDIR /app
COPY target/peliscloudjpa-0.0.1-SNAPSHOT.jar app.jar
COPY bd_prueba_dos /app/bd_prueba_dos
EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
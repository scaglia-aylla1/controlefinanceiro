FROM eclipse-temurin:21-jre
WORKDIR /app
COPY target/controle-financeiro-0.0.1-SNAPSHOT.jar controle-financeiro-v1.0.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar","controle-financeiro-v1.0.jar"]
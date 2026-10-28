FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

COPY target/Pretium-1.0-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

# Produção deve subir com segurança ativa explicitamente.
ENV SPRING_PROFILES_ACTIVE=prod,security

ENTRYPOINT ["java","-jar","/app/app.jar"]

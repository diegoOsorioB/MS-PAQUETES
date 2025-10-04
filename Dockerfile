# ======================
# Build stage
# ======================
FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /app

# Copia todo el código y construye el JAR
COPY . .
RUN mvn -B clean package -DskipTests

# ======================
# Runtime stage
# ======================
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copia el JAR construido
COPY --from=build /app/target/*.jar app.jar

# Opciones de memoria
ENV JAVA_OPTS="-Xms64m -Xmx128m"

# Puerto del microservicio
EXPOSE 8081

# Comando de inicio
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]

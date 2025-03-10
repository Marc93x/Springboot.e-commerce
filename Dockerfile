# Utilise une image de base Java pour le build
FROM maven:3.8.4-openjdk-17-slim AS build

# Définir le répertoire de travail
WORKDIR /app

# Copier le fichier pom.xml et télécharger les dépendances
COPY pom.xml .
RUN mvn dependency:go-offline

# Copier tout le reste du projet et construire l'application
COPY src /app/src
RUN mvn clean package -DskipTests

# Utilise une image JRE pour l'exécution de l'application
FROM openjdk:17-jdk-slim

# Définir le répertoire de travail dans l'image finale
WORKDIR /app

# Copier le fichier JAR depuis l'image de build
COPY --from=build /app/target/ecommerce-api-1.0.0.jar /app/app.jar

# Exposer le port sur lequel ton application Spring Boot tourne
EXPOSE 8080

# Commande pour exécuter ton application
ENTRYPOINT ["java", "-jar", "app.jar"]


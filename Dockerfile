# Utiliser l'image Eclipse Temurin pour Java 21
FROM eclipse-temurin:21

# Définir le répertoire de travail
WORKDIR /app

# Copier le fichier JAR généré par Maven ou Gradle
COPY target/marketplace-backend-0.0.1-SNAPSHOT.jar app.jar

# Exposer le port de l'application Spring Boot
EXPOSE 8080

# Commande pour exécuter l'application
CMD ["java", "-jar", "app.jar"]
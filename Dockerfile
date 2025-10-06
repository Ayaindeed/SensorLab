# Utiliser WildFly comme image de base
FROM quay.io/wildfly/wildfly:27.0.0.Final-jdk11

# Définir le répertoire de travail
WORKDIR /opt/jboss/wildfly

# Copier le fichier WAR généré dans le répertoire de déploiement de WildFly
COPY target/iotplatform.war /opt/jboss/wildfly/standalone/deployments/

# Exposer le port 8080 pour accéder à l'application
EXPOSE 8080

# Démarrer WildFly en mode standalone
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0"]
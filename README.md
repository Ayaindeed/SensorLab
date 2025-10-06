# SensorLab

SensorLab est une plateforme IoT qui permet de gérer et d'exploiter des données de capteurs en temps réel.

## Fonctionnalités

- Collecte des données depuis différents capteurs (température, humidité, pression, CO2, luminosité)
- Visualisation des données en temps réel via un tableau de bord web
- Ingestion et stockage des lectures de capteurs
- Interface utilisateur simple et intuitive

## Technologies Utilisées

### Backend
- **Java 11** : Langage de programmation principal
- **Jakarta EE 10** : Plateforme d'entreprise pour les applications Java
  - Jakarta Servlet : Gestion des requêtes HTTP
  - Jakarta CDI (Contexts and Dependency Injection) : Injection de dépendances et gestion du cycle de vie
  - Jakarta JSTL (JavaServer Pages Standard Tag Library) : Bibliothèque de balises pour JSP
- **Maven 3** : Gestionnaire de dépendances et outil de build

### Frontend
- **JSP (JavaServer Pages)** : Pages web dynamiques
- **HTML5/CSS3** : Structure et style de l'interface utilisateur
- **JavaScript** : Interactivité côté client

### Serveur d'Application
- **WildFly 27** : Serveur d'applications Java EE certifié

### Conteneurisation et Déploiement
- **Docker** : Conteneurisation de l'application
- **Docker Compose** : Orchestration des conteneurs

### Outils de Développement
- **Git** : Système de contrôle de version
- **Maven Wrapper** : Wrapper Maven pour garantir la version correcte

## Architecture

### Vue d'Ensemble

L'application suit une architecture **MVC (Model-View-Controller)** en couches, respectant les principes SOLID et les bonnes pratiques Java EE.

```
┌─────────────────────────────────────────────────────────┐
│                    Couche Présentation                   │
│  (JSP Views: index.jsp, iot-dashboard.jsp)              │
└────────────────────┬────────────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────────────┐
│                    Couche Contrôleur                     │
│         (IoTSensorControllerServlet.java)               │
│  - Gère les requêtes HTTP GET/POST                      │
│  - Validation des données d'entrée                      │
└────────────────────┬────────────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────────────┐
│                     Couche Service                       │
│         (IoTSensorManagerService.java)                  │
│  - Logique métier de l'application                     │
│  - Traitement et filtrage des données                   │
└────────────────────┬────────────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────────────┐
│                      Couche DAO                          │
│  Interface: SensorReadingDAO                            │
│  Implémentation: InMemorySensorReadingDAO               │
│  - Abstraction de la persistance des données            │
└────────────────────┬────────────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────────────┐
│                 Couche de Données                        │
│            (HashMap - Stockage en mémoire)              │
└─────────────────────────────────────────────────────────┘
```

### Composants Principaux

#### 1. Modèle (Model)
- **SensorReading** : Data Transfer Object (DTO) représentant une lecture de capteur
  - Attributs : readingId, sensorId, sensorType, value, unit, timestamp, location
  - Implémente `Serializable` pour permettre la sérialisation

#### 2. Couche DAO (Data Access Object)
- **SensorReadingDAO** (Interface) : Définit le contrat pour l'accès aux données
  - Méthodes : addReading, getAllReadings, getReadingById, getReadingsBySensorId, getReadingsBySensorType
- **InMemorySensorReadingDAO** (Implémentation) : Stockage en mémoire avec HashMap
  - Annotée `@ApplicationScoped` pour CDI
  - Permet un changement facile vers une base de données réelle

#### 3. Couche Service
- **IoTSensorManagerService** : Logique métier centralisée
  - Annotée `@ApplicationScoped`
  - Injection de SensorReadingDAO via `@Inject`
  - Méthodes : ingestSensorReading, retrieveAllSensorReadings, findReadingById, filterReadingsBySensorType, getLatestReadings

#### 4. Couche Contrôleur
- **IoTSensorControllerServlet** : Point d'entrée pour les requêtes HTTP
  - Annotée `@WebServlet("/iot-dashboard")`
  - Gère GET (affichage) et POST (ajout de données)
  - Injection du service via CDI

#### 5. Couche Vue
- **index.jsp** : Page d'accueil de l'application
- **iot-dashboard.jsp** : Tableau de bord principal avec formulaire et affichage des données

#### 6. Configuration
- **CORSFilter** : Filtre pour gérer les requêtes Cross-Origin
- **beans.xml** : Configuration CDI pour l'injection de dépendances

### Principes de Conception

#### Injection de Dépendances (CDI)
L'application utilise Jakarta CDI pour gérer les dépendances entre composants :
- `@ApplicationScoped` : Portée de l'application pour les beans partagés
- `@Inject` : Injection automatique des dépendances

#### Séparation des Responsabilités
Chaque couche a une responsabilité claire :
- **DAO** : Accès et persistance des données
- **Service** : Logique métier et règles de gestion
- **Contrôleur** : Gestion des requêtes HTTP et navigation
- **Vue** : Présentation des données

#### Abstraction par Interface
L'utilisation d'interfaces (ex: SensorReadingDAO) permet :
- Couplage faible entre les composants
- Facilité de test avec des mocks
- Possibilité de changer l'implémentation sans modifier le code client
- Évolutivité vers des solutions Big Data (Cassandra, Hadoop, etc.)

### Flux de Données

#### Ajout d'une Lecture de Capteur
1. L'utilisateur remplit le formulaire dans `iot-dashboard.jsp`
2. La requête POST est envoyée à `IoTSensorControllerServlet`
3. Le servlet valide les données et crée un objet `SensorReading`
4. Le servlet appelle `IoTSensorManagerService.ingestSensorReading()`
5. Le service délègue au DAO via `SensorReadingDAO.addReading()`
6. Les données sont stockées dans la HashMap
7. Redirection vers la page GET pour afficher les données mises à jour

#### Affichage des Lectures
1. Requête GET vers `IoTSensorControllerServlet`
2. Le servlet appelle `IoTSensorManagerService.retrieveAllSensorReadings()`
3. Le service récupère les données via `SensorReadingDAO.getAllReadings()`
4. Les données sont ajoutées aux attributs de la requête
5. Forward vers `iot-dashboard.jsp` pour l'affichage

### Déploiement

#### Conteneurisation avec Docker
L'application est conteneurisée pour faciliter le déploiement :

**Dockerfile** :
- Image de base : WildFly 27 avec JDK 11
- Copie du fichier WAR dans le répertoire de déploiement
- Exposition du port 8080
- Démarrage automatique de WildFly

**docker-compose.yml** :
- Configuration simple pour lancer l'application
- Mapping du port 8080

#### Processus de Build et Déploiement
1. **Build Maven** : `mvn clean package` génère `iotplatform.war`
2. **Build Docker** : `docker-compose build` crée l'image Docker
3. **Lancement** : `docker-compose up` démarre le conteneur
4. **Accès** : http://localhost:8080/iot-dashboard

### Évolutivité et Améliorations Futures

#### Limitations Actuelles
- Stockage en mémoire (données perdues au redémarrage)
- Pas de persistance durable
- Scalabilité limitée (un seul serveur)
- Pas de traitement temps réel avancé

#### Pistes d'Amélioration
- **Base de données** : Migration vers PostgreSQL, MongoDB ou bases de données de séries temporelles (InfluxDB, TimescaleDB)
- **Big Data** : Intégration avec Apache Kafka pour le streaming, Cassandra pour le stockage distribué
- **Microservices** : Découpage en microservices avec Spring Boot ou Quarkus
- **API REST** : Exposition d'une API RESTful pour les intégrations externes
- **Authentification** : Ajout de la sécurité avec JWT ou OAuth2
- **Monitoring** : Intégration de Prometheus et Grafana pour la surveillance

## Installation et Utilisation

### Prérequis
- Java 11 ou supérieur
- Maven 3.6+ (ou utiliser le Maven Wrapper inclus)
- Docker et Docker Compose (pour le déploiement conteneurisé)

### Compilation
```bash
./mvnw clean package
```

### Déploiement avec Docker
```bash
docker-compose up --build
```

### Accès à l'Application
Ouvrez votre navigateur et accédez à :
```
http://localhost:8080
```

Puis cliquez sur "Accéder au tableau de bord" ou allez directement à :
```
http://localhost:8080/iot-dashboard
```

## Collaborateurs
@nisbe1812

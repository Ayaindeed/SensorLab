# SensorLab

SensorLab est un projet qui permet de gérer et d’exploiter des données de capteurs.

## Fonctionnalités

- Collecte des données depuis différents capteurs
- Visualisation des données en temps réel
- Exportation des données pour analyse
- Interface utilisateur simple

## Architecture

- **Web (JSP)** : Interface utilisateur pour visualiser et ajouter des lectures de capteurs.
- **Service Métier** : La logique métier est centralisée dans `IoTSensorManagerService`, qui délègue la gestion des données au DAO.
- **DAO (Data Access Object)** : Abstraction pour la persistance des données. L’implémentation par défaut est en mémoire (`InMemorySensorReadingDAO`), mais le code est conçu pour permettre facilement d’autres implémentations (ex : Cassandra, Hadoop, etc.).
- **Modèle** : `SensorReading` représente une lecture de capteur (température, humidité, etc.).
- **Configuration** : Filtres CORS pour l’ouverture des APIs.
- **Déploiement** : Docker + WildFly pour packager et exécuter l’application web.

## Outils et technologies utilisés

- **Jakarta EE** (CDI pour l’injection de dépendances, JPA éventuellement)
- **JSP/JSTL** pour l’interface web
- **Docker** : Conteneurisation et exécution sur WildFly
- **WildFly** : Serveur d’application Java EE
- **Maven** : Gestion des dépendances et du build

## Fonctionnalités

- Collecte des données depuis différents capteurs
- Visualisation des données en temps réel
- Exportation des données pour analyse
- Interface utilisateur simple
`

## Utilisation

- Accédez à l’interface via `http://localhost:8080/`
- Ajoutez et visualisez les lectures de capteurs.


Ce projet est sous licence MIT.
##Collab
@nisbe1812

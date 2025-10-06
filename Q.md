## 1.Pourquoi avons-nous utilisé une interface SensorReadingDAO et une implémentation InMemorySensorReadingDAO ? Quel est l'avantage de cette approche pour une future intégration Big Data ?
L'utilisation d'une interface permet de respecter le principe de ségrégation des interfaces (ISP) et d'inversion des dépendances (DIP)
Cela permet de changer facilement l'implémentation sans modifier le reste du code (couplage faible)
Pour une future intégration Big Data, on pourrait simplement créer une nouvelle implémentation (ex: HadoopSensorReadingDAO ou CassandraSensorReadingDAO) sans toucher au reste de l'application


## 2.Quel est le rôle de CDI dans cette application ? Comment facilite-t-il le développement et la testabilité ?
CDI (Contexts and Dependency Injection) gère l'injection des dépendances automatiquement
Il facilite la modularité et le découplage des composants
Pour les tests, on peut facilement mocker ou remplacer les composants grâce aux interfaces et à l'injection de dépendances
Permet de gérer le cycle de vie des objets et leur portée (scope)

## 3.Si nous devions gérer des millions de lectures par seconde, quelles seraient les limites de notre InMemorySensorReadingDAO ? Quelles technologies Big Data (vues dans le syllabus) pourraient la remplacer ?
Limites de InMemorySensorReadingDAO pour le Big Data Limites :
Mémoire limitée du serveur
Pas de persistance des données
Pas de distribution possible
Performances limitées pour les requêtes complexes
Technologies Big Data possibles :
Apache Cassandra pour le stockage distribué
Apache Kafka pour le streaming de données
Apache Hadoop pour le traitement distribué
InfluxDB ou TimescaleDB pour les séries temporelles

## 4.Comment cette architecture simple pourrait-elle être adaptée pour gérer un très grand volume de données IoT ? (Pensez aux limites de la solution actuelle).
Adaptation pour grands volumes IoT


Ajouter une couche de streaming (Kafka) pour gérer le flux de données
Implémenter un système de stockage distribué
Mettre en place du traitement en temps réel
Utiliser des bases de données optimisées pour les séries temporelles
Ajouter du load balancing et de la mise à l'échelle horizontale

## 5.Quels sont les avantages de conteneuriser cette application avec Docker, notamment dans un contexte de déploiement de microservices IoT ?
Avantages de Docker pour IoT


Déploiement cohérent et reproductible
Isolation des composants
Facilité de mise à l'échelle
Gestion simplifiée des dépendances
Intégration facile avec les orchestrateurs comme Kubernetes
Facilite les mises à jour et le rollback
Portabilité entre différents environnement


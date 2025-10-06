package com.example.iotplatform.dao;

import com.example.iotplatform.model.SensorReading;
import java.util.List;

/**
 * Interface DAO pour la gestion des lectures de capteurs
 */
public interface SensorReadingDAO {

    /**
     * Ajoute une nouvelle lecture de capteur
     * @param reading la lecture à ajouter
     */
    void addReading(SensorReading reading);

    /**
     * Récupère toutes les lectures de capteurs
     * @return liste de toutes les lectures
     */
    List<SensorReading> getAllReadings();

    /**
     * Récupère une lecture par son identifiant
     * @param readingId l'identifiant de la lecture
     * @return la lecture correspondante ou null si non trouvée
     */
    SensorReading getReadingById(String readingId);

    /**
     * Récupère toutes les lectures d'un capteur spécifique
     * @param sensorId l'identifiant du capteur
     * @return liste des lectures du capteur
     */
    List<SensorReading> getReadingsBySensorId(String sensorId);

    /**
     * Récupère toutes les lectures d'un type de capteur spécifique
     * @param sensorType le type de capteur
     * @return liste des lectures du type spécifié
     */
    List<SensorReading> getReadingsBySensorType(String sensorType);
}
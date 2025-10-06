package com.example.iotplatform.service;

import com.example.iotplatform.dao.SensorReadingDAO;
import com.example.iotplatform.model.SensorReading;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service métier pour la gestion des capteurs IoT
 * Contient la logique métier et délègue la persistance au DAO
 */
@ApplicationScoped
public class IoTSensorManagerService {

    @Inject
    private SensorReadingDAO sensorReadingDAO;

    /**
     * Ingère une nouvelle lecture de capteur
     * @param reading la lecture à ingérer
     */
    public void ingestSensorReading(SensorReading reading) {
        if (reading != null) {
            sensorReadingDAO.addReading(reading);
        }
    }

    /**
     * Récupère toutes les lectures de capteurs
     * @return liste de toutes les lectures
     */
    public List<SensorReading> retrieveAllSensorReadings() {
        return sensorReadingDAO.getAllReadings();
    }

    /**
     * Recherche une lecture par son identifiant
     * @param readingId l'identifiant de la lecture
     * @return la lecture correspondante ou null
     */
    public SensorReading findReadingById(String readingId) {
        return sensorReadingDAO.getReadingById(readingId);
    }

    /**
     * Filtre les lectures par type de capteur
     * @param sensorType le type de capteur
     * @return liste des lectures du type spécifié
     */
    public List<SensorReading> filterReadingsBySensorType(String sensorType) {
        return sensorReadingDAO.getReadingsBySensorType(sensorType);
    }

    /**
     * Récupère les dernières lectures ajoutées
     * @param limit nombre maximum de lectures à retourner
     * @return liste des dernières lectures (triées par timestamp décroissant)
     */
    public List<SensorReading> getLatestReadings(int limit) {
        return sensorReadingDAO.getAllReadings().stream()
                .sorted(Comparator.comparingLong(SensorReading::getTimestamp).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    /**
     * Récupère les lectures d'un capteur spécifique
     * @param sensorId l'identifiant du capteur
     * @return liste des lectures du capteur
     */
    public List<SensorReading> getReadingsBySensorId(String sensorId) {
        return sensorReadingDAO.getReadingsBySensorId(sensorId);
    }
}
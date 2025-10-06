package com.example.iotplatform.dao;

import com.example.iotplatform.model.SensorReading;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implémentation en mémoire du DAO pour les lectures de capteurs
 * Utilise une HashMap pour stocker les données
 */
@ApplicationScoped
public class InMemorySensorReadingDAO implements SensorReadingDAO {

    // Stockage en mémoire des lectures
    private final Map<String, SensorReading> readingsStore = new HashMap<>();

    @Override
    public void addReading(SensorReading reading) {
        if (reading != null && reading.getReadingId() != null) {
            readingsStore.put(reading.getReadingId(), reading);
        }
    }

    @Override
    public List<SensorReading> getAllReadings() {
        return new ArrayList<>(readingsStore.values());
    }

    @Override
    public SensorReading getReadingById(String readingId) {
        return readingsStore.get(readingId);
    }

    @Override
    public List<SensorReading> getReadingsBySensorId(String sensorId) {
        return readingsStore.values().stream()
                .filter(reading -> reading.getSensorId().equals(sensorId))
                .collect(Collectors.toList());
    }

    @Override
    public List<SensorReading> getReadingsBySensorType(String sensorType) {
        return readingsStore.values().stream()
                .filter(reading -> reading.getSensorType().equalsIgnoreCase(sensorType))
                .collect(Collectors.toList());
    }
}
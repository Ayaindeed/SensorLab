package com.example.iotplatform.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Data Transfer Object représentant une lecture de capteur IoT
 */
public class SensorReading implements Serializable {

    private static final long serialVersionUID = 1L;

    private String readingId;
    private String sensorId;
    private String sensorType;
    private double value;
    private String unit;
    private long timestamp;
    private String location;

    // Constructeur par défaut
    public SensorReading() {
    }

    // Constructeur avec tous les paramètres
    public SensorReading(String readingId, String sensorId, String sensorType,
                         double value, String unit, long timestamp, String location) {
        this.readingId = readingId;
        this.sensorId = sensorId;
        this.sensorType = sensorType;
        this.value = value;
        this.unit = unit;
        this.timestamp = timestamp;
        this.location = location;
    }

    // Getters
    public String getReadingId() {
        return readingId;
    }

    public String getSensorId() {
        return sensorId;
    }

    public String getSensorType() {
        return sensorType;
    }

    public double getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getLocation() {
        return location;
    }

    // Setters
    public void setReadingId(String readingId) {
        this.readingId = readingId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    // equals() et hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SensorReading that = (SensorReading) o;
        return Objects.equals(readingId, that.readingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(readingId);
    }

    // toString()
    @Override
    public String toString() {
        return "SensorReading{" +
                "readingId='" + readingId + '\'' +
                ", sensorId='" + sensorId + '\'' +
                ", sensorType='" + sensorType + '\'' +
                ", value=" + value +
                ", unit='" + unit + '\'' +
                ", timestamp=" + timestamp +
                ", location='" + location + '\'' +
                '}';
    }
}
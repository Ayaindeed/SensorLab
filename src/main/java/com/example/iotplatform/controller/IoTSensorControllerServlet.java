package com.example.iotplatform.controller;

import com.example.iotplatform.model.SensorReading;
import com.example.iotplatform.service.IoTSensorManagerService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Servlet contrôleur pour le tableau de bord IoT
 * Gère les requêtes GET (affichage) et POST (ingestion de données)
 */
@WebServlet("/iot-dashboard")
public class IoTSensorControllerServlet extends HttpServlet {

    @Inject
    private IoTSensorManagerService iotSensorManagerService;

    /**
     * Gère les requêtes GET - Affiche le tableau de bord avec toutes les lectures
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Récupération de toutes les lectures
        List<SensorReading> readings = iotSensorManagerService.retrieveAllSensorReadings();

        // Ajout des lectures à la requête pour transmission à la JSP
        request.setAttribute("readings", readings);

        // Forward vers la page JSP
        request.getRequestDispatcher("/WEB-INF/views/iot-dashboard.jsp")
                .forward(request, response);
    }

    /**
     * Gère les requêtes POST - Ingère une nouvelle lecture de capteur
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Récupération des paramètres du formulaire
            String sensorId = request.getParameter("sensorId");
            String sensorType = request.getParameter("sensorType");
            String valueStr = request.getParameter("value");
            String unit = request.getParameter("unit");
            String location = request.getParameter("location");

            // Validation basique
            if (sensorId == null || sensorId.trim().isEmpty() ||
                    sensorType == null || sensorType.trim().isEmpty() ||
                    valueStr == null || valueStr.trim().isEmpty()) {

                request.setAttribute("error", "Tous les champs obligatoires doivent être remplis");
                doGet(request, response);
                return;
            }

            // Conversion de la valeur
            double value = Double.parseDouble(valueStr);

            // Création de la nouvelle lecture
            SensorReading newReading = new SensorReading();
            newReading.setReadingId(UUID.randomUUID().toString());
            newReading.setSensorId(sensorId.trim());
            newReading.setSensorType(sensorType.trim());
            newReading.setValue(value);
            newReading.setUnit(unit != null ? unit.trim() : "");
            newReading.setLocation(location != null ? location.trim() : "");
            newReading.setTimestamp(System.currentTimeMillis());

            // Ingestion de la lecture
            iotSensorManagerService.ingestSensorReading(newReading);

            // Redirection vers la page d'affichage (pattern POST-Redirect-GET)
            response.sendRedirect(request.getContextPath() + "/iot-dashboard");

        } catch (NumberFormatException e) {
            request.setAttribute("error", "La valeur doit être un nombre valide");
            doGet(request, response);
        }
    }
}
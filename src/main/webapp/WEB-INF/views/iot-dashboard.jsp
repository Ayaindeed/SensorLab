<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tableau de Bord IoT</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 20px;
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
        }

        h1 {
            text-align: center;
            color: white;
            margin-bottom: 30px;
            font-size: 2.5em;
            text-shadow: 2px 2px 4px rgba(0,0,0,0.3);
        }

        .card {
            background: white;
            border-radius: 15px;
            padding: 30px;
            margin-bottom: 30px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.3);
        }

        h2 {
            color: #667eea;
            margin-bottom: 20px;
            border-bottom: 3px solid #667eea;
            padding-bottom: 10px;
        }

        .form-group {
            margin-bottom: 15px;
        }

        label {
            display: block;
            margin-bottom: 5px;
            color: #333;
            font-weight: 600;
        }

        input, select {
            width: 100%;
            padding: 12px;
            border: 2px solid #e0e0e0;
            border-radius: 8px;
            font-size: 14px;
            transition: border-color 0.3s;
        }

        input:focus, select:focus {
            outline: none;
            border-color: #667eea;
        }

        button {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 12px 30px;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: transform 0.2s;
            width: 100%;
            margin-top: 10px;
        }

        button:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(102, 126, 234, 0.4);
        }

        .readings-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
            gap: 20px;
            margin-top: 20px;
        }

        .reading-card {
            background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
            padding: 20px;
            border-radius: 10px;
            border-left: 5px solid #667eea;
            transition: transform 0.2s;
        }

        .reading-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 5px 15px rgba(0,0,0,0.2);
        }

        .reading-header {
            font-size: 1.2em;
            font-weight: bold;
            color: #667eea;
            margin-bottom: 10px;
        }

        .reading-detail {
            margin: 5px 0;
            color: #333;
        }

        .reading-detail strong {
            color: #764ba2;
        }

        .value-display {
            font-size: 1.8em;
            font-weight: bold;
            color: #667eea;
            margin: 10px 0;
        }

        .timestamp {
            font-size: 0.9em;
            color: #666;
            font-style: italic;
        }

        .no-data {
            text-align: center;
            padding: 40px;
            color: #999;
            font-size: 1.2em;
        }

        .error {
            background-color: #f8d7da;
            color: #721c24;
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 20px;
            border-left: 5px solid #dc3545;
        }

        .stats {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 15px;
            margin-bottom: 20px;
        }

        .stat-box {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 20px;
            border-radius: 10px;
            text-align: center;
        }

        .stat-number {
            font-size: 2.5em;
            font-weight: bold;
        }

        .stat-label {
            font-size: 0.9em;
            opacity: 0.9;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>🌐 Tableau de Bord IoT</h1>

    <!-- Formulaire d'ingestion -->
    <div class="card">
        <h2>📊 Nouvelle Lecture de Capteur</h2>

        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>

        <form method="POST" action="${pageContext.request.contextPath}/iot-dashboard">
            <div class="form-group">
                <label for="sensorId">ID du Capteur *</label>
                <input type="text" id="sensorId" name="sensorId" required
                       placeholder="Ex: SENSOR-001">
            </div>

            <div class="form-group">
                <label for="sensorType">Type de Capteur *</label>
                <select id="sensorType" name="sensorType" required>
                    <option value="">-- Sélectionnez --</option>
                    <option value="TEMPERATURE">Température</option>
                    <option value="HUMIDITY">Humidité</option>
                    <option value="PRESSURE">Pression</option>
                    <option value="CO2">CO2</option>
                    <option value="LIGHT">Luminosité</option>
                </select>
            </div>

            <div class="form-group">
                <label for="value">Valeur *</label>
                <input type="number" id="value" name="value" step="0.01" required
                       placeholder="Ex: 23.5">
            </div>

            <div class="form-group">
                <label for="unit">Unité</label>
                <input type="text" id="unit" name="unit"
                       placeholder="Ex: °C, %, hPa, ppm">
            </div>

            <div class="form-group">
                <label for="location">Emplacement</label>
                <input type="text" id="location" name="location"
                       placeholder="Ex: Salle des serveurs">
            </div>

            <button type="submit">➕ Ajouter la Lecture</button>
        </form>
    </div>

    <!-- Statistiques -->
    <div class="card">
        <div class="stats">
            <div class="stat-box">
                <div class="stat-number">${readings.size()}</div>
                <div class="stat-label">Lectures Total</div>
            </div>
        </div>
    </div>

    <!-- Affichage des lectures -->
    <div class="card">
        <h2>📡 Lectures des Capteurs</h2>

        <c:choose>
            <c:when test="${empty readings}">
                <div class="no-data">
                    Aucune lecture disponible. Ajoutez votre première lecture ci-dessus.
                </div>
            </c:when>
            <c:otherwise>
                <div class="readings-grid">
                    <c:forEach var="reading" items="${readings}">
                        <div class="reading-card">
                            <div class="reading-header">
                                    ${reading.sensorType}
                            </div>

                            <div class="value-display">
                                    ${reading.value} ${reading.unit}
                            </div>

                            <div class="reading-detail">
                                <strong>Capteur:</strong> ${reading.sensorId}
                            </div>

                            <div class="reading-detail">
                                <strong>Lieu:</strong> ${reading.location}
                            </div>

                            <div class="reading-detail">
                                <strong>ID Lecture:</strong> ${reading.readingId}
                            </div>

                            <div class="timestamp">
                                <jsp:useBean id="dateValue" class="java.util.Date"/>
                                <jsp:setProperty name="dateValue" property="time" value="${reading.timestamp}"/>
                                <fmt:formatDate value="${dateValue}" pattern="dd/MM/yyyy HH:mm:ss"/>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</body>
</html>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>IoT Platform - Accueil</title>
    <style>
        body {
            font-family: "Segoe UI", Arial, sans-serif;
            background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 700px;
            margin: 80px auto;
            background-color: white;
            padding: 40px;
            border-radius: 12px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
            text-align: center;
        }
        h1 {
            color: #2c3e50;
            margin-bottom: 20px;
        }
        p {
            color: #555;
            font-size: 1.1em;
            margin-bottom: 30px;
        }
        a {
            display: inline-block;
            background-color: #e74c3c;
            color: white;
            text-decoration: none;
            padding: 12px 25px;
            border-radius: 8px;
            font-weight: bold;
            transition: background-color 0.3s ease;
        }
        a:hover {
            background-color: #c0392b;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Bienvenue sur la plateforme IoT</h1>
    <p>Cette application permet de simuler la collecte et l’affichage des données de capteurs (température, humidité, etc.).</p>
    <a href="${pageContext.request.contextPath}/iot-dashboard">Accéder au tableau de bord</a>
</div>
</body>
</html>

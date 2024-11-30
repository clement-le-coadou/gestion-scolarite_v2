<%@ page import="java.util.List" %>
<%@ page import="mainApp.model.EmploiDuTempsEnseignant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Emploi du Temps - Enseignant</title>
    <link rel="stylesheet" href="resources/emploiDuTemps.css">
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    
</head>
<body class="bg-light">
    <%@ include file="menu-nav.jsp" %>
    <h1>Mon Emploi du Temps</h1>

    <!-- Vérification du message d'erreur -->
    <p><%= request.getAttribute("message") != null ? request.getAttribute("message") : "" %></p>

    <h2>Emploi du temps de la semaine</h2>

    <table border="1">
        <thead>
            <tr>
                <th>Jour</th>
                <th>Heure</th>
                <th>Matière</th>
                <th>Salle</th>
            </tr>
        </thead>
        <tbody>
            <% 
                List<EmploiDuTempsEnseignant> emploiDuTemps = (List<EmploiDuTempsEnseignant>) request.getAttribute("emploiDuTemps");
                if (emploiDuTemps != null && !emploiDuTemps.isEmpty()) {
                    for (EmploiDuTempsEnseignant emploi : emploiDuTemps) {
            %>
            <tr>
                <td><%= emploi.getJourSemaine() %></td>
                <td><%= emploi.getHeureDebut() + " - " + emploi.getHeureDebut().plusMinutes(emploi.getDuree()) %></td>
                <td><%= emploi.getCours().getNom() %></td>
                <td><%= emploi.getSalle() %></td>
            </tr>
            <% 
                    }
                }
            %>
        </tbody>
    </table>

    <a href="/logout">Déconnexion</a>
</body>
</html>
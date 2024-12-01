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
    <div class="container my-5">
        <h1 class="text-center text-primary">Mon Emploi du Temps - Enseignant</h1>

        <!-- Vérification du message d'erreur -->
        <p class="text-danger text-center"><%= request.getAttribute("message") != null ? request.getAttribute("message") : "" %></p>

        <div class="emploi-du-temps mt-4">
            <table class="table table-bordered text-center">
                <thead class="thead-dark">
                    <tr>
                        <th>Heures</th>
                        <th>Lundi</th>
                        <th>Mardi</th>
                        <th>Mercredi</th>
                        <th>Jeudi</th>
                        <th>Vendredi</th>
                        <th>Samedi</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                        // Définir les plages horaires
                        String[] heures = { "08:00", "09:00", "10:00", "11:00", "12:00", "14:00", "15:00", "16:00", "17:00" };
                        String[] jours = { "LUNDI", "MARDI", "MERCREDI", "JEUDI", "VENDREDI", "SAMEDI" };

                        // Récupérer l'emploi du temps de l'enseignant
                        List<EmploiDuTempsEnseignant> emploiDuTemps = (List<EmploiDuTempsEnseignant>) request.getAttribute("emploiDuTemps");

                        if (emploiDuTemps != null && !emploiDuTemps.isEmpty()) { %>
                        <p>Cours trouvés : <%= emploiDuTemps.size() %></p>
                    <% } else { %>
                        <p>Aucun cours trouvé pour l'emploi du temps.</p>
                    <% }

                        // Boucle pour afficher les horaires
                        for (int i = 0; i < heures.length - 1; i++) {
                    %>
                    <tr>
                        <td><%= heures[i] %> - <%= heures[i + 1] %></td>
                        <% for (String jour : jours) { %>
                            <td>
                                <%
                                    boolean coursTrouve = false;
                                    if (emploiDuTemps != null) {
                                        for (EmploiDuTempsEnseignant emploi : emploiDuTemps) {
                                            if (emploi.getJourSemaine().name().equals(jour) &&
                                                emploi.getHeureDebut().toString().startsWith(heures[i])) {
                                    %>
                                    <div class="cours bg-success text-white p-2 rounded">
                                        <strong><%= emploi.getCours().getNom() %></strong><br>
                                        <small>Salle : <%= emploi.getSalle() %></small><br>
                                        <small>Durée : <%= emploi.getDuree() %> min</small><br>
                                        <small>Etudiants inscrits : <%= emploi.getCours().getInscriptions().size() %></small>
                                    </div>
                                    <%
                                                coursTrouve = true;
                                                break;
                                            }
                                        }
                                    }
                                    if (!coursTrouve) {
                                        out.print(""); // Affiche une cellule vide si aucun cours n'est trouvé
                                    }
                                %>
                            </td>
                        <% } %>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>

        <div class="text-center mt-4">
            <a href="/logout" class="btn btn-danger">Déconnexion</a>
        </div>
    </div>
</body>
</html>

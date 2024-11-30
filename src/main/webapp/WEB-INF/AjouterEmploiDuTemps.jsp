<%@ page import="mainApp.model.Cours" %>
<%@ page import="mainApp.model.Enseignant" %>
<%@ page import="mainApp.model.Etudiant" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ajouter un Emploi du Temps</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <%@ include file="menu-nav.jsp" %>

<div class="container mt-5">
    <h2 class="text-center mb-4">Ajouter un Créneau</h2>
    <!-- Vérification du message d'erreur -->
    <p><%= request.getAttribute("message") != null ? request.getAttribute("message") : "" %></p>
    <form action="AjouterEmploiDuTemps" method="post">
        <!-- Sélection du Cours -->
        <div class="form-group">
            <label for="coursId">Cours :</label>
            <select class="form-control" id="coursId" name="coursId" required>
                <option value="">--Sélectionner--</option>
                <%
                    List<Cours> coursList = (List<Cours>) request.getAttribute("coursList");
                    if (coursList != null) {
                        for (Cours cours : coursList) {
                %>
                    <option value="<%= cours.getId() %>"><%= cours.getNom() %></option>
                <%
                        }
                    }
                %>
            </select>
        </div>

        <!-- Sélection de l'Étudiant -->
        <div class="form-group">
            <label for="etudiantId">Étudiant :</label>
            <select class="form-control" id="etudiantId" name="etudiantId" required>
                <option value="">--Sélectionner--</option>
                <%
                    List<Etudiant> etudiants = (List<Etudiant>) request.getAttribute("etudiants");
                    if (etudiants != null) {
                        for (Etudiant etudiant : etudiants) {
                %>
                    <option value="<%= etudiant.getId() %>"><%= etudiant.getNom() %> <%= etudiant.getPrenom() %></option>
                <%
                        }
                    }
                %>
            </select>
        </div>

        <!-- Sélection de l'Enseignant -->
        <div class="form-group">
            <label for="enseignantId">Enseignant :</label>
            <select class="form-control" id="enseignantId" name="enseignantId" required>
                <option value="">--Sélectionner--</option>
                <%
                    List<Enseignant> enseignants = (List<Enseignant>) request.getAttribute("enseignants");
                    if (enseignants != null) {
                        for (Enseignant enseignant : enseignants) {
                %>
                    <option value="<%= enseignant.getId() %>"><%= enseignant.getNom() %> <%= enseignant.getPrenom() %></option>
                <%
                        }
                    }
                %>
            </select>
        </div>

        <!-- Jour de la semaine -->
        <div class="form-group">
            <label for="jourSemaine">Jour de la semaine :</label>
            <select class="form-control" id="jourSemaine" name="jourSemaine" required>
                <option value="">--Sélectionner--</option>
                <option value="LUNDI">Lundi</option>
                <option value="MARDI">Mardi</option>
                <option value="MERCREDI">Mercredi</option>
                <option value="JEUDI">Jeudi</option>
                <option value="VENDREDI">Vendredi</option>
            </select>
        </div>

        <!-- Heure de Début -->
        <div class="form-group">
            <label for="heureDebut">Heure de Début :</label>
            <input type="time" class="form-control" id="heureDebut" name="heureDebut" required>
        </div>

        <!-- Durée du Cours -->
        <div class="form-group">
            <label for="duree">Durée (en minutes) :</label>
            <input type="number" class="form-control" id="duree" name="duree" required>
        </div>

        <!-- Salle -->
        <div class="form-group">
            <label for="salle">Salle :</label>
            <input type="text" class="form-control" id="salle" name="salle" required>
        </div>

        <!-- Boutons de soumission -->
        <button type="submit" class="btn btn-success">Ajouter l'Emploi du Temps</button>
        <a href="gestionEmploiDuTemps" class="btn btn-secondary">Annuler</a>
    </form>
</div>

</body>
</html>

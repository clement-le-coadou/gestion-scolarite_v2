<%@ page import="mainApp.model.Cours" %>
<%@ page import="mainApp.model.Enseignant" %>
<%@ page import="java.util.List" %>
<%@ page import="mainApp.service.EnseignantService" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Modifier Cours</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <h2 class="text-center mb-4">Modifier Cours</h2>

    <%
        Cours cours = (Cours) request.getAttribute("cours");

        // Récupérer la liste des enseignants pour le champ select
        List<Enseignant> enseignants = (List<Enseignant>) request.getAttribute("listeEnseignant");
    %>

    <form action="ModifierCours" method="post">
        <input type="hidden" name="id" value="<%= cours.getId() %>">

        <div class="form-group">
            <label for="nom">Nom du Cours :</label>
            <input type="text" class="form-control" id="nom" name="nom" value="<%= cours.getNom() %>" required>
        </div>

        <div class="form-group">
            <label for="description">Description :</label>
            <textarea class="form-control" id="description" name="description" rows="4" required><%= cours.getDescription() %></textarea>
        </div>

        <div class="form-group">
            <label for="enseignant">Enseignant :</label>
            <select class="form-control" id="enseignant" name="enseignantId">
                <option value="">--Sélectionner--</option>
                <%
                    for (Enseignant enseignant : enseignants) {
                %>
                    <option value="<%= enseignant.getId() %>" <%= cours.getEnseignant() != null && cours.getEnseignant().getId().equals(enseignant.getId()) ? "selected" : "" %>>
                        <%= enseignant.getNom() + " " + enseignant.getPrenom() %>
                    </option>
                <%
                    }
                %>
            </select>
        </div>

        <button type="submit" class="btn btn-primary">Enregistrer les modifications</button>
    </form>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

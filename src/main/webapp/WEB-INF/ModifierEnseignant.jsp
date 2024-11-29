<%@ page import="mainApp.model.Enseignant" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Modifier Enseignant</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <h2 class="text-center mb-4">Modifier Enseignant</h2>

    <%
        Enseignant enseignant = (Enseignant) request.getAttribute("enseignant");
    %>

    <form action="ModifierEnseignant" method="post">
        <input type="hidden" name="id" value="<%= enseignant.getId() %>">

        <div class="form-group">
            <label for="nom">Nom :</label>
            <input type="text" class="form-control" id="nom" name="nom" value="<%= enseignant.getNom() %>" required>
        </div>

        <div class="form-group">
            <label for="prenom">Prénom :</label>
            <input type="text" class="form-control" id="prenom" name="prenom" value="<%= enseignant.getPrenom() %>" required>
        </div>

        <div class="form-group">
            <label for="email">Email :</label>
            <input type="email" class="form-control" id="email" name="email" value="<%= enseignant.getEmail() %>" required>
        </div>

        <div class="form-group">
            <label for="contact">Contact :</label>
            <input type="text" class="form-control" id="contact" name="contact" value="<%= enseignant.getContact() %>" required>
        </div>

        <button type="submit" class="btn btn-primary">Modifier</button>
    </form>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

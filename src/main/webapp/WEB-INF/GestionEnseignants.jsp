<%@ page import="java.util.List" %>
<%@ page import="mainApp.model.Enseignant" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestion des Enseignants</title>
    <link rel="stylesheet" href="resources/bootstrap.min.css">
    <link rel="stylesheet" href="resources/banniere.css">
</head>
<body class="bg-light">
	<%@ include file="menu-nav.jsp" %>
	
<div class="container mt-5">
    <h2 class="text-center mb-4">Gestion des Enseignants</h2>

    <div class="mb-3">
        <a href="AjouterEnseignant.jsp" class="btn btn-success">Ajouter un Enseignant</a>
    </div>

    <table class="table table-bordered">
        <thead>
            <tr>
                <th>ID</th>
                <th>Nom</th>
                <th>Prénom</th>
                <th>Email</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <%-- Looping through the enseignants list using a scriptlet --%>
            <%
                List<Enseignant> enseignants = (List<Enseignant>) request.getAttribute("enseignants"); // Get enseignants from request
                if (enseignants != null) {
                    for (Enseignant enseignant : enseignants) {
            %>
                <tr>
                    <td><%= enseignant.getId() %></td>
                    <td><%= enseignant.getNom() %></td>
                    <td><%= enseignant.getPrenom() %></td>
                    <td><%= enseignant.getEmail() %></td>
                    <td>
                        <a href="ModifierEnseignant?id=<%= enseignant.getId() %>" class="btn btn-primary">Modifier</a>
                        <form action="SupprimerEnseignant" method="post" style="display:inline;">
                            <input type="hidden" name="id" value="<%= enseignant.getId() %>">
                            <button type="submit" class="btn btn-danger">Supprimer</button>
                        </form>
                    </td>
                </tr>
            <%
                    }
                }
            %>
        </tbody>
    </table>
    
    <a href="accueil.jsp" class="btn btn-secondary">Retour à l'accueil</a>
    
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

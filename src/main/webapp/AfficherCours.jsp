<%@ page import="java.util.List" %>
<%@ page import="jpa.Cours" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des Cours</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/banniere.css">
</head>
<body class="bg-light">
    <%@ include file="header.jsp" %>

<div class="container mt-5">
    <h2 class="text-center mb-4">Liste des Cours</h2>

    <% 
    List<Cours> coursList = (List<Cours>) request.getAttribute("coursList");

    // Debugging output
    if (coursList != null) {
        out.println("<h5 class='text-secondary'>Données de Debugging :</h5>");
        for (Cours cours : coursList) {
            out.println("Nom: " + cours.getNom() + ", ");
            out.println("Description: " + cours.getDescription() + ", ");
            out.println("ID: " + cours.getId() + "<br>");
        }
    } else {
        out.println("<p class='text-warning'>Aucun cours trouvé dans l'attribut 'coursList'.</p>");
    }
    %>
    
    <table class="table table-bordered table-hover mt-4">
        <thead class="thead-dark">
            <tr>
                <th>Nom</th>
                <th>Description</th>
                <th>Enseignant</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <% if (coursList != null) { %>
                <% for (Cours cours : coursList) { %>
                    <tr>
                        <td><%= cours.getNom() %></td>
                        <td><%= cours.getDescription() %></td>
                        <td><%= cours.getEnseignant().getNom() %> <%= cours.getEnseignant().getPrenom() %></td>
                        <td>
                            <form action="ModifierCours" method="get" class="d-inline">
                                <input type="hidden" name="id" value="<%= cours.getId() %>">
                                <button type="submit" class="btn btn-primary btn-sm">Afficher Détails</button>
                            </form>
                        </td>
                    </tr>
                <% } %>
            <% } else { %>
                <tr>
                    <td colspan="4" class="text-center">Aucun cours disponible.</td>
                </tr>
            <% } %>
        </tbody>
    </table>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

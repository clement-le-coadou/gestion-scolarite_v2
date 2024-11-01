<%@ page import="java.util.List" %>
<%@ page import="jpa.Enseignant" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des Enseignants</title>
    <link rel="stylesheet" href="resources/bootstrap.min.css">
    <link rel="stylesheet" href="resources/banniere.css">
</head>
<body class="bg-light">
    <%@ include file="header.jsp" %>

<div class="container mt-5">
    <h2 class="text-center mb-4">Liste des Enseignants</h2>

    <% 
    List<Enseignant> enseignants = (List<Enseignant>) request.getAttribute("enseignants");

    if (enseignants != null) {
        out.println("<h5 class='text-secondary'>Informations des enseignants :</h5>");
    } else {
        out.println("<p class='text-warning'>Aucun enseignant trouvé dans la base de données.</p>");
    }
    %>

    <table class="table table-bordered table-hover mt-4">
        <thead class="thead-dark">
            <tr>
                <th>Nom</th>
                <th>Prénom</th>
                <th>Email</th>
                <th>Contact</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <% if (enseignants != null) { %>
                <% for (Enseignant enseignant : enseignants) { %>
                    <tr>
                        <td><%= enseignant.getNom() %></td>
                        <td><%= enseignant.getPrenom() %></td>
                        <td><%= enseignant.getEmail() %></td>
                        <td><%= enseignant.getContact() %></td>
                        <td>
                            <form action="ModifierEnseignant" method="get" class="d-inline">
                                <input type="hidden" name="id" value="<%= enseignant.getId() %>">
                                <button type="submit" class="btn btn-primary btn-sm">Voir Profil</button>
                            </form>
                        </td>
                    </tr>
                <% } %>
            <% } else { %>
                <tr>
                    <td colspan="5" class="text-center">Aucun enseignant disponible.</td>
                </tr>
            <% } %>
        </tbody>
    </table>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

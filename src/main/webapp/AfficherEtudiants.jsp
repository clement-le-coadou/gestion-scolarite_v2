<%@ page import="java.util.List" %>
<%@ page import="jpa.Etudiant" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des Étudiants</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <h2 class="text-center mb-4">Liste des Étudiants</h2>

    <% 
    List<Etudiant> etudiants = (List<Etudiant>) request.getAttribute("etudiants");

    // Debugging output
    if (etudiants != null) {
        out.println("<h5 class='text-secondary'>Données de Debugging :</h5>");
        for (Etudiant etudiant : etudiants) {
            out.println("Nom: " + etudiant.getNom() + ", ");
            out.println("Prénom: " + etudiant.getPrenom() + ", ");
            out.println("ID: " + etudiant.getId() + "<br>");
        }
    } else {
        out.println("<p class='text-warning'>Aucun étudiant trouvé dans l'attribut 'etudiants'.</p>");
    }
    %>
    
    <table class="table table-bordered table-hover mt-4">
        <thead class="thead-dark">
            <tr>
                <th>Nom</th>
                <th>Prénom</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <% if (etudiants != null) { %>
                <% for (Etudiant etudiant : etudiants) { %>
                    <tr>
                        <td><%= etudiant.getNom() %></td>
                        <td><%= etudiant.getPrenom() %></td>
                        <td>
                            <form action="ModifierEtudiant" method="get" class="d-inline">
                                <input type="hidden" name="id" value="<%= etudiant.getId() %>">
                                <button type="submit" class="btn btn-primary btn-sm">Afficher Profil</button>
                            </form>
                        </td>
                    </tr>
                <% } %>
            <% } else { %>
                <tr>
                    <td colspan="3" class="text-center">Aucun étudiant disponible.</td>
                </tr>
            <% } %>
        </tbody>
    </table>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

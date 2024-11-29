<%@ page import="java.util.List" %>
<%@ page import="mainApp.model.Cours" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestion des Cours</title>
    <link rel="stylesheet" href="resources/bootstrap.min.css">
    <link rel="stylesheet" href="resources/banniere.css">
</head>
<body class="bg-light">
    <%@ include file="menu-nav.jsp" %>

    <div class="container mt-5">
        <h2 class="text-center mb-4">Gestion des Cours</h2>

        <div class="d-flex justify-content-between w-100 mt-3 mb-3">
            <a href="AjouterCours" class="btn btn-success">Ajouter un Cours</a>
            <a href="InscrireEtudiant" class="btn btn-info">Inscrire un Étudiant</a>    
        </div>

        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nom du Cours</th>
                    <th>Description</th>
                    <th>Enseignant</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    // Retrieve the list of courses from the request scope
                    List<Cours> coursList = (List<Cours>) request.getAttribute("coursList");
                    if (coursList != null) {
                        for (Cours cours : coursList) {
                %>
                            <tr>
                                <td><%= cours.getId() %></td>
                                <td><%= cours.getNom() %></td>
                                <td><%= cours.getDescription() %></td>
                                <td>
                                    <% 
                                        // Check if the course has an assigned teacher
                                        if (cours.getEnseignant() != null) { 
                                    %>
                                        <%= cours.getEnseignant().getNom() + " " + cours.getEnseignant().getPrenom() %>
                                    <% 
                                        } else { 
                                    %>
                                        Non assigné
                                    <% 
                                        } 
                                    %>
                                </td>
                                <td>
                                    <a href="ModifierCours?id=<%= cours.getId() %>" class="btn btn-primary">Modifier</a>
                                    <form action="SupprimerCours" method="post" style="display:inline;">
                                        <input type="hidden" name="id" value="<%= cours.getId() %>">
                                        <button type="submit" class="btn btn-danger">Supprimer</button>
                                    </form>
                                </td>
                            </tr>
                <% 
                        }
                    } else { 
                %>
                        <tr>
                            <td colspan="5" class="text-center">Aucun cours trouvé</td>
                        </tr>
                <% 
                    }
                %>
            </tbody>
        </table>

        <a href="accueil" class="btn btn-secondary">Retour à l'accueil</a>
    </div>

</body>
</html>

<%@ page import="java.util.List" %>
<%@ page import="mainApp.model.Etudiant" %>
<%@ page import="mainApp.service.EtudiantService" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestion des Étudiants</title>
    <link rel="stylesheet" href="resources/bootstrap.min.css">
    <link rel="stylesheet" href="resources/banniere.css">
</head>
<body class="bg-light">
	<%@ include file="menu-nav.jsp" %>

<div class="container mt-5">
    <h2 class="text-center mb-4">Gestion des Étudiants</h2>
    
    <%-- Retrieve the role from the session --%>
    <% role = (String) session.getAttribute("role"); %> <!-- Assuming role is in session -->
    
    <% if ("Administrateur".equals(role)) { %>
    <div class="mb-3">
        <a href="AjouterEtudiant" class="btn btn-success">Ajouter un Étudiant</a>
    </div>
    <% } %>
    
	<!-- Formulaire de recherche -->
	<div class="mb-4">
	    <div class="input-group">
	        <input id="searchInput" type="text" class="form-control" placeholder="Rechercher un étudiant" oninput="filterTable()">
	        <button class="btn btn-primary" type="button">Rechercher</button>
	    </div>
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
            <% 
                // Get the list of Etudiant objects from the request or model
                List<Etudiant> etudiantList = (List<Etudiant>) request.getAttribute("etudiants");
                if (etudiantList != null) {
                    for (Etudiant etudiant : etudiantList) {
            %>
            <tr>
                <td><%= etudiant.getId() %></td>
                <td><%= etudiant.getNom() %></td>
                <td><%= etudiant.getPrenom() %></td>
                <td><%= etudiant.getEmail() %></td>
                
                <td>
                    <a href="ConsulterCours?id=<%= etudiant.getId() %>" class="btn btn-info">Voir les Cours</a>
                    <% if ("Administrateur".equals(role)) { %>
                    <a href="ModifierEtudiant?id=<%= etudiant.getId() %>" class="btn btn-primary">Modifier</a>
                    <form action="SupprimerEtudiant" method="post" style="display:inline;">
                        <input type="hidden" name="id" value="<%= etudiant.getId() %>">
                        <button type="submit" class="btn btn-danger">Supprimer</button>
                    </form>
                    <% } %>
                </td>
            </tr>
            <% 
                    }
                } else {
            %>
                <tr>
                    <td colspan="5" class="text-center">Aucun étudiant trouvé</td>
                </tr>
            <% 
                }
            %>
        </tbody>
    </table>

    <a href="accueil" class="btn btn-secondary">Retour à l'accueil</a>
    
</div>

<script>
    // Search function implemented in JavaScript
    function filterTable() {
        const searchValue = document.getElementById("searchInput").value.toLowerCase();
        const tableRows = document.querySelectorAll("table tbody tr");

        tableRows.forEach(row => {
            const cells = row.querySelectorAll("td");
            const rowText = Array.from(cells).slice(1, 4) // Nom, Prénom, Email
                                  .map(cell => cell.textContent.toLowerCase())
                                  .join(" ");
            if (rowText.includes(searchValue)) {
                row.style.display = ""; // Afficher la ligne
            } else {
                row.style.display = "none"; // Masquer la ligne
            }
        });
    }
</script>

</body>
</html>
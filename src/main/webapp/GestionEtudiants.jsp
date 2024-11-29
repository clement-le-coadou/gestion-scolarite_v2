<%@ page import="java.util.List" %>
<%@ page import="model.Etudiant" %>
<%@ page import="daogenerique.CrudGeneric" %>
<%@ page import="org.hibernate.SessionFactory" %>
<%@ page import="org.hibernate.cfg.Configuration" %>
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
	<%if("Administrateur".equals(role)) {%>
    <div class="mb-3">
        <a href="AjouterEtudiant.jsp" class="btn btn-success">Ajouter un Étudiant</a>
    </div>
    <%} %>
    
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
                // Initialisation de la session et récupération des étudiants
                SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
                CrudGeneric<Etudiant> etudiantDAO = new CrudGeneric<>(sessionFactory, Etudiant.class);
                List<Etudiant> etudiantList = etudiantDAO.findAll();
                
                String search = request.getParameter("search");
                //List<Etudiant> etudiantList;
                
                /*
                if (search != null && !search.isEmpty()) {
                    etudiantList = etudiantDAO.findBy("nom", search); // Méthode à implémenter pour filtrer par nom ou prénom
                } else {
                    etudiantList = etudiantDAO.findAll();
                }
                */

                for (Etudiant etudiant : etudiantList) {
            %>
            <tr>
                <td><%= etudiant.getId() %></td>
                <td><%= etudiant.getNom() %></td>
                <td><%= etudiant.getPrenom() %></td>
                <td><%= etudiant.getEmail() %></td>
                
                <td>
                	<a href="ConsulterCours?id=<%= etudiant.getId() %>" class="btn btn-info">Voir les Cours</a>
                	<%if("Administrateur".equals(role)) {%>
                    <a href="ModifierEtudiant?id=<%= etudiant.getId() %>" class="btn btn-primary">Modifier</a>
                    <form action="SupprimerEtudiant" method="post" style="display:inline;">
                        <input type="hidden" name="id" value="<%= etudiant.getId() %>">
                        <button type="submit" class="btn btn-danger">Supprimer</button>
                    </form>
                    <%} %>
                </td>
            </tr>
            <%
                }
            %>
        </tbody>
    </table>

    <a href="accueil.jsp" class="btn btn-secondary">Retour à l'accueil</a>
    
</div>


<script>
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

<%@ page import="java.util.List" %>
<%@ page import="jpa.Cours" %>
<%@ page import="daogenerique.CrudGeneric" %>
<%@ page import="org.hibernate.SessionFactory" %>
<%@ page import="org.hibernate.cfg.Configuration" %>
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
      <%@ include file="header.jsp" %>

<div class="container mt-5">
    <h2 class="text-center mb-4">Gestion des Cours</h2>


	<div class="d-flex justify-content-between w-100 mt-3 mb-3">
    	<a href="AjouterCours.jsp" class="btn btn-success">Ajouter un Cours</a>
    	<a href="InscrireEtudiant.jsp" class="btn btn-info">Inscrire un Étudiant</a>    
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
                // Initialisation de la session et récupération des cours
                SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
                CrudGeneric<Cours> coursDAO = new CrudGeneric<>(sessionFactory, Cours.class);
                List<Cours> coursList = coursDAO.findAll();

                for (Cours cours : coursList) {
            %>
            <tr>
                <td><%= cours.getId() %></td>
                <td><%= cours.getNom() %></td>
                <td><%= cours.getDescription() %></td>
                <td><%= (cours.getEnseignant() != null ? cours.getEnseignant().getNom() + " " + cours.getEnseignant().getPrenom() : "Non assigné") %></td>
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
            %>
        </tbody>
    </table>
    
    <a href="accueil.jsp" class="btn btn-secondary">retour à l'accueil</a>
    
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

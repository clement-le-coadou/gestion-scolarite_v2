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
    <title>Liste des Cours</title>
    <link rel="stylesheet" href="resources/bootstrap.min.css">
    <link rel="stylesheet" href="resources/banniere.css">
</head>
<body class="bg-light">
<%@ include file="header.jsp" %>


<div class="container mt-5">
    <h2 class="text-center mb-4">Liste des Cours</h2>

 
    
    <table class="table table-bordered table-hover mt-4">
        <thead class="thead-dark">
            <tr>
                <th>Nom</th>
                <th>Description</th>
                <th>Enseignant</th>
             
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
            		<% if (coursList != null) { %>
                    <tr>
                        <td><%= cours.getNom() %></td>
                        <td><%= cours.getDescription() %></td>
                        <td><%= (cours.getEnseignant() != null ? cours.getEnseignant().getNom() + " " + cours.getEnseignant().getPrenom() : "Non assigné") %></td>
                    </tr>
                
            <% } else { %>
                <tr>
                    <td colspan="4" class="text-center">Aucun cours disponible.</td>
                </tr>
            	<% } %>
            <% } %>
        </tbody>
    </table>
    <div class="d-flex justify-content-between w-100 mt-3">
    <a href="accueil.jsp" class="btn btn-secondary">Retour à l'accueil</a>
    <a href="MesCours" class="btn btn-info">Mes Cours</a>
	</div>
</div>	


<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

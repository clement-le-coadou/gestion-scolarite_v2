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
    <h2 class="text-center mb-4">Mes Cours</h2>

 
    
    <table class="table table-bordered table-hover mt-4">
        <thead class="thead-dark">
            <tr>
                <th>Nom</th>
                <th>Description</th>
                <th>Enseignant</th>
             
            </tr>
        </thead>
            
         <%
        List<Cours> mesCours = (List<Cours>) request.getAttribute("mesCours");
    %>
        <tbody>
            <%
                if (mesCours != null && !mesCours.isEmpty()) {
                    for (Cours cours : mesCours) {
            %>
                        <tr>
                            <td><%= cours.getNom() %></td>
                            <td><%= cours.getDescription() %></td>
                            <td><%= (cours.getEnseignant() != null ? cours.getEnseignant().getNom() + " " + cours.getEnseignant().getPrenom() : "Non assigné") %></td>
                        </tr>
            <%
                    }
                } else {
            %>
                    <tr>
                        <td colspan="3" class="text-center">Aucun cours à afficher.</td>
                    </tr>
            <%
                }
            %>
        </tbody>
    </table>

    <a href="AfficherCours.jsp" class="btn btn-secondary mt-3">Retour à la liste des Cours</a>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

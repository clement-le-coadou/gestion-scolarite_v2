<%@ page import="java.util.List" %>
<%@ page import="jpa.Etudiant" %>
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
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
	<%@ include file="header.jsp" %>

<div class="container mt-5">
    <h2 class="text-center mb-4">Gestion des Étudiants</h2>

    <div class="mb-3">
        <a href="AjouterEtudiant.jsp" class="btn btn-success">Ajouter un Étudiant</a>
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

                for (Etudiant etudiant : etudiantList) {
            %>
            <tr>
                <td><%= etudiant.getId() %></td>
                <td><%= etudiant.getNom() %></td>
                <td><%= etudiant.getPrenom() %></td>
                <td><%= etudiant.getEmail() %></td>
                <td>
                    <a href="ModifierEtudiant?id=<%= etudiant.getId() %>" class="btn btn-primary">Modifier</a>
                    <form action="SupprimerEtudiant" method="post" style="display:inline;">
                        <input type="hidden" name="id" value="<%= etudiant.getId() %>">
                        <button type="submit" class="btn btn-danger">Supprimer</button>
                    </form>
                </td>
            </tr>
            <%
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

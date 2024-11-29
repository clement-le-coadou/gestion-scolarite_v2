<%@ page import="java.util.List" %>
<%@ page import="model.Enseignant" %>
<%@ page import="daogenerique.CrudGeneric" %>
<%@ page import="org.hibernate.SessionFactory" %>
<%@ page import="org.hibernate.cfg.Configuration" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestion des Enseignants</title>
    <link rel="stylesheet" href="resources/bootstrap.min.css">
    <link rel="stylesheet" href="resources/banniere.css">
</head>
<body class="bg-light">
	<%@ include file="menu-nav.jsp" %>
	
<div class="container mt-5">
    <h2 class="text-center mb-4">Gestion des Enseignants</h2>

    <div class="mb-3">
        <a href="AjouterEnseignant.jsp" class="btn btn-success">Ajouter un Enseignant</a>
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
				<c:forEach var="enseignant" items="${enseignants}">
                    <tr>
                        <td>${enseignant.id}</td>
                        <td>${enseignant.nom}</td>
                        <td>${enseignant.prenom}</td>
                        <td>${enseignant.email}</td>
                        <td>
                            <a href="ModifierEnseignant?id=${enseignant.id}" class="btn btn-primary">Modifier</a>
                            <form action="SupprimerEnseignant" method="post" style="display:inline;">
                                <input type="hidden" name="id" value="${enseignant.id}">
                                <button type="submit" class="btn btn-danger">Supprimer</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
        </tbody>
    </table>
    
    <a href="accueil.jsp" class="btn btn-secondary">Retour à l'accueil</a>
    
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

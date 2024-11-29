<%@ page import="java.util.List" %>
<%@ page import="model.Cours" %>
<%@ page import="daogenerique.CrudGeneric" %>
<%@ page import="org.hibernate.SessionFactory" %>
<%@ page import="org.hibernate.cfg.Configuration" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
			<c:forEach var="cours" items="${coursList}">
                    <tr>
                        <td>${cours.id}</td>
                        <td>${cours.nom}</td>
                        <td>${cours.description}</td>
                        <td>
                            <c:choose>
                                <c:when test="${cours.enseignant != null}">
                                    ${cours.enseignant.nom} ${cours.enseignant.prenom}
                                </c:when>
                                <c:otherwise>
                                    Non assigné
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <a href="ModifierCours?id=${cours.id}" class="btn btn-primary">Modifier</a>
                            <form action="SupprimerCours" method="post" style="display:inline;">
                                <input type="hidden" name="id" value="${cours.id}">
                                <button type="submit" class="btn btn-danger">Supprimer</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
        </tbody>
    </table>
    
    <a href="accueil.jsp" class="btn btn-secondary">retour à l'accueil</a>
    
</div>

</body>
</html>

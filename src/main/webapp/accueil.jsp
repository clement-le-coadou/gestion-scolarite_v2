<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Accueil</title>
        <link rel="stylesheet" href="resources/bootstrap.min.css">
        <link rel="stylesheet" href="resources/banniere.css">
    </head>
<body>
  <%@ include file="header.jsp" %>
<nav class="navbar navbar-expand-lg navbar-light bg-light"> 
    <div class="container"> 
        <span class="navbar-text">Rôle: <%= role %></span> 
        <ul class="navbar-nav ml-auto"> 
            <% if ("Administrateur".equals(role)) { %>
                <li class="nav-item"> 
                    <a class="nav-link" href="GestionCours.jsp">Gestion des cours</a> 
                </li> 
            <% } %>
            <li class="nav-item"> 
                <a class="nav-link" href="AfficherCours">Liste des cours</a> 
            </li> 
            <li class="nav-item"> 
                <a class="nav-link" href="GestionNotes.jsp">Mes notes</a> 
            </li> 
            <li class="nav-item"> 
                <a class="nav-link" href="#">Mes infos</a> 
            </li> 
            <li class="nav-item"> 
                <!-- Affiche "Se déconnecter" si l'utilisateur est connecté -->
                <a class="nav-link" href="#">Se déconnecter</a> 
            </li> 
        </ul> 
    </div> 
</nav>


            <br>
            <a href="AfficherEtudiants">Afficher la liste des élèves</a>
            <br>
            <a href="AfficherCours">Afficher la liste des cours</a>
            <br>
            <a href="AfficherEnseignants">Afficher la liste des enseignants</a>
</body>
</html>
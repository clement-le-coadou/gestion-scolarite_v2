<%@page import="jpa.Administrateur"%>
<%@page import="jpa.Enseignant"%>
<%@page import="jpa.Etudiant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
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
            <span class="navbar-text">Rôle: Étudiant</span> 
            <ul class="navbar-nav ml-auto"> 
                <li class="nav-item"> 
                    <a class="nav-link" href="#">Gestion des cours</a> 
                </li> 
                <li class="nav-item"> 
                    <a class="nav-link" href="AfficherCours.jsp">Liste des cours</a> 
                </li> 
                <li class="nav-item"> 
                    <a class="nav-link" href="#">Mes notes</a> 
                </li> 
                <li class="nav-item"> 
                    <a class="nav-link" href="#">Mes infos</a> 
                </li> 
                <li class="nav-item"> 
                    <!-- ou se connecter, selon si on sait si il est connecté ou non -->
                    <a class="nav-link" href="#">Se déconnecter</a> 
                </li> 
            </ul> 
        </div> 
    </nav>
<%
    // Récupération de l'objet utilisateur depuis la session
    Object user = session.getAttribute("username");

    if (user != null) {
        if (user instanceof Etudiant) {
            Etudiant etudiant = (Etudiant) user;
            out.println("Vous êtes connecté en tant qu'Étudiant : " + etudiant.getNom() + " " + etudiant.getPrenom());
        } else if (user instanceof Enseignant) {
            Enseignant enseignant = (Enseignant) user;
            out.println("Vous êtes connecté en tant qu'Enseignant : " + enseignant.getNom() + " " + enseignant.getPrenom());
        } else if (user instanceof Administrateur) {
            Administrateur administrateur = (Administrateur) user;
            out.println("Vous êtes connecté en tant qu'Administrateur : " + administrateur.getNom() + " " + administrateur.getPrenom());
                       
        }
    } else {
        out.println("Aucun utilisateur connecté.");
    }
%>

            <br>
            <a href="AfficherEtudiants">Afficher la liste des élèves</a>
            <br>
            <a href="AfficherCours">Afficher la liste des cours</a>
            <br>
            <a href="AfficherEnseignants">Afficher la liste des enseignants</a>
</body>
</html>
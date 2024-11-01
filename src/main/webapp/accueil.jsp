<%@page import="jpa.Administrateur"%>
<%@page import="jpa.Enseignant"%>
<%@page import="jpa.Etudiant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Accueil</title>
</head>
<body>
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
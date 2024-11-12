<%@page import="org.hibernate.internal.build.AllowSysOut"%>
<%@ page import="java.util.List" %>
<%@ page import="jpa.Cours" %>
<%@ page import="daogenerique.CrudGeneric" %>
<%@ page import="org.hibernate.SessionFactory" %>
<%@ page import="org.hibernate.cfg.Configuration" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Informations</title>
    <link rel="stylesheet" href="resources/bootstrap.min.css">
    <link rel="stylesheet" href="resources/banniere.css">
</head>
<body class="bg-light">
    <%@ include file="menu-nav.jsp" %>

    <%
        // Ensure `user` is accessible in EL by setting it as a request attribute
        if (user != null) {
            if (user instanceof Etudiant) {
                request.setAttribute("user", (Etudiant) user);
                request.setAttribute("userType", "etudiant");
            } else if (user instanceof Enseignant) {
                request.setAttribute("user", (Enseignant) user);
                request.setAttribute("userType", "enseignant");
            } else if (user instanceof Administrateur) {
                request.setAttribute("user", (Administrateur) user);
                request.setAttribute("userType", "administrateur");
            }else{
                request.setAttribute("userType", "etudiant");
            }
        } else {
            // If no user is found, set a default Etudiant
            Etudiant defaultUser = new Etudiant();
            request.setAttribute("user", defaultUser);
            request.setAttribute("userType", "etudiant");
        }
    %>

    <div class="container mt-5">
        <h2 class="text-center mb-4">Modifier Utilisateur</h2>

        <form action="ModifierUtilisateur" method="post" class="p-4 bg-white shadow-sm rounded">
            <input type="hidden" name="id" value="${user.id}">
            <input type="hidden" name="userType" value="${userType}">

            <div class="form-group">
                <label for="nom">Nom :</label>
                <input type="text" class="form-control" id="nom" name="nom" value="${user.nom}" required>
            </div>

            <div class="form-group">
                <label for="prenom">Prénom :</label>
                <input type="text" class="form-control" id="prenom" name="prenom" value="${user.prenom}" required>
            </div>

            <% if ("etudiant".equals(request.getAttribute("userType"))) { %>
                <!-- Date de Naissance for Etudiant only -->
                <div class="form-group">
                    <label for="dateNaissance">Date de Naissance :</label>
                    <input type="date" class="form-control" id="dateNaissance" name="dateNaissance" 
                           value="${user.dateNaissance != null ? user.dateNaissance : ''}" required>
                </div>
            <% } %>

            <div class="form-group">
                <label for="email">Email :</label>
                <input type="email" class="form-control" id="email" name="email" value="${user.email}" required>
            </div>

            <% if ("etudiant".equals(request.getAttribute("userType")) || "enseignant".equals(request.getAttribute("userType"))) { %>
                <!-- Contact for Etudiant and Enseignant only -->
                <div class="form-group">
                    <label for="contact">Contact :</label>
                    <input type="text" class="form-control" id="contact" name="contact" value="${user.contact}">
                </div>
            <% } %>

            <button type="submit" class="btn btn-primary btn-block">Enregistrer les modifications</button>
        </form>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

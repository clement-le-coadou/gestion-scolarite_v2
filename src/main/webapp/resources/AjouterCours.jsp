<%@ page import="jpa.Cours" %>
<%@ page import="jpa.Enseignant" %>
<%@ page import="java.util.List" %>
<%@ page import="daogenerique.CrudGeneric" %>
<%@ page import="org.hibernate.SessionFactory" %>
<%@ page import="org.hibernate.cfg.Configuration" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ajouter un Cours</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <h2 class="text-center mb-4">Ajouter un Cours</h2>

    <%
        // Récupérer la liste des enseignants pour le champ select
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        CrudGeneric<Enseignant> enseignantDAO = new CrudGeneric<>(sessionFactory, Enseignant.class);
        List<Enseignant> enseignants = enseignantDAO.findAll();
    %>

    <form action="AjouterCours" method="post">
        <div class="form-group">
            <label for="nom">Nom du Cours :</label>
            <input type="text" class="form-control" id="nom" name="nom" required>
        </div>

        <div class="form-group">
            <label for="description">Description :</label>
            <textarea class="form-control" id="description" name="description" rows="4" required></textarea>
        </div>

        <div class="form-group">
            <label for="enseignant">Enseignant :</label>
            <select class="form-control" id="enseignant" name="enseignantId" required>
                <option value="">--Sélectionner--</option>
                <%
                    for (Enseignant enseignant : enseignants) {
                %>
                    <option value="<%= enseignant.getId() %>">
                        <%= enseignant.getNom() + " " + enseignant.getPrenom() %>
                    </option>
                <%
                    }
                %>
            </select>
        </div>

        <button type="submit" class="btn btn-primary">Ajouter le Cours</button>
        <a href="gestionCours.jsp" class="btn btn-secondary">Annuler</a>
    </form>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

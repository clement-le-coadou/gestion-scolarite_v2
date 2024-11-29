<%@ page import="java.util.List" %>
<%@ page import="model.Cours" %>
<%@ page import="model.Etudiant" %>
<%@ page import="daogenerique.CrudGeneric" %>
<%@ page import="org.hibernate.SessionFactory" %>
<%@ page import="org.hibernate.cfg.Configuration" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Inscrire un Étudiant à un Cours</title>
    <link rel="stylesheet" href="resources/bootstrap.min.css">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <h2 class="text-center mb-4">Inscription d'un Étudiant à un Cours</h2>

        <form action="InscrireEtudiant" method="post">
            <div class="form-group">
                <label for="etudiant">Sélectionner un Étudiant :</label>
                <select class="form-control" id="etudiant" name="etudiantId" required>
                    <%
                        // Initialiser la session Hibernate pour récupérer les étudiants
                        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
                        CrudGeneric<Etudiant> etudiantDAO = new CrudGeneric<>(sessionFactory, Etudiant.class);
                        List<Etudiant> etudiants = etudiantDAO.findAll();

                        for (Etudiant etudiant : etudiants) {
                    %>
                        <option value="<%= etudiant.getId() %>"><%= etudiant.getNom() %> <%= etudiant.getPrenom() %></option>
                    <%
                        }
                    %>
                </select>
            </div>

            <div class="form-group">
                <label for="cours">Sélectionner un Cours :</label>
                <select class="form-control" id="cours" name="coursId" required>
                    <%
                        CrudGeneric<Cours> coursDAO = new CrudGeneric<>(sessionFactory, Cours.class);
                        List<Cours> coursList = coursDAO.findAll();

                        for (Cours cours : coursList) {
                    %>
                        <option value="<%= cours.getId() %>"><%= cours.getNom() %></option>
                    <%
                        }
                    %>
                </select>
            </div>

            <button type="submit" class="btn btn-primary">Inscrire</button>
        </form>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

<%@ page import="java.util.List" %>
<%@ page import="jpa.Note" %>
<%@ page import="jpa.Etudiant" %>
<%@ page import="jpa.Cours" %>
<%@ page import="daogenerique.CrudGeneric" %>
<%@ page import="org.hibernate.SessionFactory" %>
<%@ page import="org.hibernate.cfg.Configuration" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestion des Notes</title>
    <link rel="stylesheet" href="resources/bootstrap.min.css">
    <link rel="stylesheet" href="resources/banniere.css">
</head>
<body class="bg-light">
	<%@ include file="menu-nav.jsp" %>
    <div class="container mt-5">
        <h2 class="text-center mb-4">Gestion des Notes</h2>

        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>�tudiant</th>
                    <th>Cours</th>
                    <th>Note</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="note" items="${noteList}">
                    <tr>
                        <td>${note.id}</td>
                        <td>${note.etudiant.nom} ${note.etudiant.prenom}</td>
                        <td>${note.cours.nom}</td>
                        <td>${note.note}</td>
                        <td>
                            <a href="GestionNotes?id=${note.id}" class="btn btn-primary">Modifier</a>
                            <form action="SupprimerNote" method="post" style="display:inline;">
                                <input type="hidden" name="id" value="${note.id}">
                                <button type="submit" class="btn btn-danger">Supprimer</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <a href="accueil.jsp" class="btn btn-secondary">Retour � l'accueil</a>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

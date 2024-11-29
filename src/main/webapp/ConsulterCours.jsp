<%@ page import="java.util.List" %>
<%@ page import="model.Cours" %>
<%@ page import="model.Inscription" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="UTF-8">
    <title>Consulter les Cours</title>
   <link rel="stylesheet" href="resources/bootstrap.min.css">
    <link rel="stylesheet" href="resources/banniere.css">
</head>

<body class="bg-light">

	<%@ include file="menu-nav.jsp" %>

    <div class="container mt-5">
        <h2 class="text-center mb-4">Cours de l'élève</h2>

        <!-- Tableau des cours -->
        <table class="table table-striped table-bordered">
            <thead class="thead-dark">
                <tr>
                    <th scope="col">Cours</th>
                    <th scope="col">Enseignant</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Cours> coursList = (List<Cours>) request.getAttribute("coursList");
                    if (coursList != null && !coursList.isEmpty()) {
                        for (Cours cours : coursList) {
                %>
                        <tr>
                            <td><%= cours.getNom() %></td>
                            <td><%= cours.getEnseignant().getNom() %></td>
                        </tr>
                <%
                        }
                    } else {
                %>
                        <tr>
                            <td colspan="2" class="text-center">Aucun cours trouvé</td>
                        </tr>
                <%
                    }
                %>
            </tbody>
        </table>

        <!-- Bouton de retour -->
        <div class="text-center mt-4">
            <a href="GestionEtudiants.jsp" class="btn btn-secondary">Retour</a>
        </div>
    </div>

    <!-- Scripts Bootstrap (JS, Popper.js, et jQuery) -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>

</html>

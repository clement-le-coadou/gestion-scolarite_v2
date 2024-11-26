<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="jpa.Cours" %>
<%@ page import="jpa.Note" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Mes Notes</title>
    <link rel="stylesheet" href="resources/bootstrap.min.css">
    <link rel="stylesheet" href="resources/banniere.css">
</head>
<body class="bg-light">
    <%@ include file="menu-nav.jsp" %>

    <div class="container mt-5">
        <h2 class="text-center mb-4">Mes Notes</h2>

		<%
            List<Cours> coursList = (List<Cours>) request.getAttribute("coursList");
            List<Note> notes = (List<Note>) request.getAttribute("notesList");

            if (coursList != null && !coursList.isEmpty()) {
        %>
		
		<br>
        
        <table class="table table-striped table-bordered">
            <thead class="table-dark">
                <tr>
                    <th>Cours</th>
                    <th>Note</th>
                </tr>
            </thead>
            <tbody>
                <% for (Cours cours : coursList) {
                    Note note = notes.stream().filter(n -> n.getCours().getId().equals(cours.getId())).findFirst().orElse(null);
                %>
                <tr>
                    <td><%= cours.getNom() %></td>
                    <td><%= (note != null) ? note.getNote() : "Pas de note" %></td>
                </tr>
                <% } %>
            </tbody>
        </table>
        <% } else { %>
        <p class="text-center text-danger">Aucune note disponible.</p>
        <% } %>
        
        <div class="d-flex justify-content-center mt-4">
		    <a href="generatePdf" class="btn btn-success btn-sm">Generer le PDF</a>
		</div>

        <div class="d-flex justify-content-center mt-4">
            <a href="accueil.jsp" class="btn btn-secondary">Retour à l'accueil</a>
        </div>
    </div>
</body>
</html>

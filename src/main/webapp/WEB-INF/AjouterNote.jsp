<%@ page import="model.Cours" %>
<%@ page import="model.Etudiant" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ajouter Note</title>
    <link rel="stylesheet" href="resources/bootstrap.min.css">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <h2 class="text-center mb-4">Ajouter une Note</h2>
        <form method="post" action="AjouterNote">
            <input type="hidden" name="idEtudiant" value="<%= request.getParameter("idEtudiant") %>">
            <input type="hidden" name="coursId" value="<%= request.getParameter("coursId") %>">
            
           <div class="mb-3">
			    <label for="note" class="form-label">Note :</label>
			    <input type="number" step="0.01" name="note" id="note" class="form-control" required min="0" max="20">
			</div>

            
            <button type="submit" class="btn btn-success">Ajouter</button>
            <a href="accueil.jsp" class="btn btn-secondary">Retour à l'accueil</a>

        </form>
    </div>
</body>
</html>

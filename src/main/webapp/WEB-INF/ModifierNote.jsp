<%@ page import="mainApp.model.Note" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Modifier Note</title>
    <link rel="stylesheet" href="resources/bootstrap.min.css">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <h2 class="text-center mb-4">Modifier une Note</h2>
        <form method="post" action="ModifierNote">
            <input type="hidden" name="idNote" value="<%= request.getParameter("idNote") %>">
            
            <div class="mb-3">
                <label for="note" class="form-label">Nouvelle Note :</label>
                <input type="number" step="0.01" name="note" id="note" class="form-control" value="<%= request.getParameter("noteValue") %>" required min="0" max="20">
            </div>
            
            <button type="submit" class="btn btn-primary">Modifier</button>
                 <a href="accueil" class="btn btn-secondary">Retour à l'accueil</a>
        </form>
    </div>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="mainApp.model.Cours" %>

<html>
<head>
    <title>Gestion des Notes</title>
    <link rel="stylesheet" href="resources/bootstrap.min.css">
    <link rel="stylesheet" href="resources/banniere.css">
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <h2 class="text-center mb-4">Gestion des Notes</h2>

        <form method="post" action="GestionNotes" class="form-inline justify-content-center">
            <div class="form-group mr-3">
                <label for="coursSelect" class="mr-2">Sélectionnez un cours :</label>
                <select name="coursId" id="coursSelect" class="form-control" onchange="this.form.submit()">
                    <option value="">-- Choisissez un cours --</option>
                    <% 
                        List<Cours> coursList = (List<Cours>) request.getAttribute("coursList"); 
                        for (Cours cours : coursList) { 
                    %>
                        <option value="<%= cours.getId() %>" 
                            <%= (request.getParameter("coursId") != null && 
                                 request.getParameter("coursId").equals(String.valueOf(cours.getId()))) 
                                 ? "selected" : "" %>>
                            <%= cours.getNom() %>
                        </option>
                    <% } %>
                </select>
            </div>
        </form>
    </div>
</body>
</html>

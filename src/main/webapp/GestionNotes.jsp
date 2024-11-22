<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="jpa.Cours, jpa.Etudiant, jpa.Note" %>

<html>
<head>
    <title>Gestion des Notes</title>
</head>
<body>
    <h2>Gestion des Notes</h2>

    <form method="post" action="GestionNotes">
        <label for="coursSelect">Sélectionnez un cours :</label>
        <select name="coursId" id="coursSelect" onchange="this.form.submit()">
            <option value="">-- Choisissez un cours --</option>
            <% 
                List<Cours> coursList = (List<Cours>) request.getAttribute("coursList"); 
                for (Cours cours : coursList) { 
            %>
                <option value="<%= cours.getId() %>" <%= (request.getParameter("coursId") != null && request.getParameter("coursId").equals(String.valueOf(cours.getId()))) ? "selected" : "" %> >
                    <%= cours.getNom() %>
                </option>
            <% } %>
        </select>
    </form>
</body>
</html>

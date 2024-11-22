<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="jpa.Cours, jpa.Note" %>

<html>
<head>
    <title>Mes Notes</title>
</head>
<body>
    <h2>Mes Notes</h2>

    <% 
        List<Cours> coursList = (List<Cours>) request.getAttribute("coursList");
        List<Note> notes = (List<Note>) request.getAttribute("notesList");
        
        if (coursList != null && !coursList.isEmpty()) {
    %>
        <table border="1">
            <tr>
                <th>Cours</th>
                <th>Note</th>
            </tr>
            <% for (Cours cours : coursList) { 
                Note note = notes.stream().filter(n -> n.getCours().getId().equals(cours.getId())).findFirst().orElse(null);
            %>
            <tr>
                <td><%= cours.getNom() %></td>
                <td><%= (note != null) ? note.getNote() : "Pas de note" %></td>
            </tr>
            <% } %>
        </table>
    <% } else { %>
        <p>Aucune note disponible.</p>
    <% } %>
</body>
</html>

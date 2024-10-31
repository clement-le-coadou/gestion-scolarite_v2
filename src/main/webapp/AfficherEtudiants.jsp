<%@ page import="java.util.List" %>
<%@ page import="jpa.Etudiant" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des Étudiants</title>
</head>
<body>
    <h2>Liste des Étudiants</h2>
    
    <% 
    List<Etudiant> etudiants = (List<Etudiant>) request.getAttribute("etudiants");
    
    // Debugging output
    if (etudiants != null) {
        out.println("<h3>Données de Debugging :</h3>");
        for (Etudiant etudiant : etudiants) {
            out.println("Nom: " + etudiant.getNom() + ", ");
            out.println("Prénom: " + etudiant.getPrenom() + ", ");
            out.println("ID: " + etudiant.getId() + "<br>");
        }
    } else {
        out.println("Aucun étudiant trouvé dans l'attribut 'etudiants'.");
    }
    %>
    
    <table border="1">
        <thead>
            <tr>
                <th>Nom</th>
                <th>Prénom</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <% if (etudiants != null) { %>
                <% for (Etudiant etudiant : etudiants) { %>
                    <tr>
                        <td><%= etudiant.getNom() %></td>
                        <td><%= etudiant.getPrenom() %></td>
                        <td>
                            <form action="ModifierEtudiant" method="get">
                                <input type="hidden" name="id" value="<%= etudiant.getId() %>">
                                <input type="submit" value="Afficher Profil">
                            </form>
                        </td>
                    </tr>
                <% } %>
            <% } else { %>
                <tr>
                    <td colspan="3">Aucun étudiant disponible.</td>
                </tr>
            <% } %>
        </tbody>
    </table>
</body>
</html>

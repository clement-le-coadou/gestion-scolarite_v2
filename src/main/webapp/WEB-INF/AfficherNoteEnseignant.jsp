<%@ page import="java.util.List" %>
<%@ page import="mainApp.model.Etudiant" %>
<%@ page import="mainApp.model.Note" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

        <table class="table table-striped table-bordered">
            <thead class="table-dark">
                <tr>
                    <th>ID Étudiant</th>
                    <th>Nom</th>
                    <th>Prénom</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <%
                    // Récupérer les données passées depuis le contrôleur
                    int coursId = (int) request.getAttribute("coursId");
                    List<Note> notes = (List<Note>) request.getAttribute("notes");
                    List<Etudiant> etudiantList = (List<Etudiant>) request.getAttribute("etudiantList");

                    for (Etudiant etudiant : etudiantList) {
                        // Filtrer les notes spécifiques à cet étudiant
                        Note noteEtudiant = null;
                        for (Note note : notes) {
                            if (note.getEtudiant().getId() == etudiant.getId() && note.getCours().getId() == coursId) {
                                noteEtudiant = note;
                                break;
                            }
                        }
                %>
                <tr>
                    <td><%= etudiant.getId() %></td>
                    <td><%= etudiant.getNom() %></td>
                    <td><%= etudiant.getPrenom() %></td>
                    <td>
                        <% if (noteEtudiant != null) { %>
                            <!-- Si une note existe, afficher Modifier et Supprimer -->
                            <span class="me-3"><%= noteEtudiant.getNote() %> - <%= noteEtudiant.getCours().getNom() %></span>
                            <a href="ModifierNote?idNote=<%= noteEtudiant.getId() %>" class="btn btn-primary btn-sm me-2">Modifier</a>
                            <form action="SupprimerNote" method="post" style="display:inline;">
                                <input type="hidden" name="idNote" value="<%= noteEtudiant.getId() %>">
                                <button type="submit" class="btn btn-danger btn-sm">Supprimer</button>
                            </form>
                        <% } else { %>
                            <!-- Si aucune note, afficher Ajouter -->
                            <a href="AjouterNote?idEtudiant=<%= etudiant.getId() %>&coursId=<%= coursId %>" class="btn btn-success btn-sm">Ajouter Note</a>
                        <% } %>
                    </td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>

        <a href="accueil.jsp" class="btn btn-secondary">Retour à l'accueil</a>
    </div>

</body>
</html>

<%@ page import="java.util.List" %>
<%@ page import="jpa.Etudiant" %>
<%@ page import="jpa.Note" %>
<%@ page import="daogenerique.CrudGeneric" %>
<%@ page import="org.hibernate.SessionFactory" %>
<%@ page import="org.hibernate.cfg.Configuration" %>
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
                    <th>Notes</th>
                   
                </tr>
            </thead>
            <tbody>
                <%
                 
                    // Liste des étudiants
                    int coursId = (int) request.getAttribute("coursId");
                    List<Note> notes = (List<Note>) request.getAttribute("notes");
                    List<Etudiant> etudiantList = (List<Etudiant>) request.getAttribute("etudiantList");
                    for (Etudiant etudiant : etudiantList) {
                        
                        
                %>
                <tr>
                    <td><%= etudiant.getId() %></td>
                    <td><%= etudiant.getNom() %></td>
                    <td><%= etudiant.getPrenom() %></td>
               
					<td>
					    <% if (!notes.isEmpty()) { %>
					        <ul class="list-unstyled">
					            <% for (Note note : notes) { %>
					                <li class="d-flex align-items-center mb-2">
					                    <!-- Affichage de la note -->
					                    <span class="me-3"><%= note.getNote() %> - <%= note.getCours().getNom() %></span>
					
					                    <!-- Boutons d'action pour la note -->
					                    <a href="ModifierNote?idNote=<%= note.getId() %>" class="btn btn-primary btn-sm me-2">Modifier</a>
					                    <form action="SupprimerNote" method="post" style="display:inline;">
					                        <input type="hidden" name="idNote" value="<%= note.getId() %>">
					                        <button type="submit" class="btn btn-danger btn-sm">Supprimer</button>
					                    </form>
					                </li>
					            <% } %>
					        </ul>
					    <% } else { %>
					        <em>Aucune note attribuée</em>
					    <% } %>
					
					 
					    <a href="AjouterNote?idEtudiant=<%= etudiant.getId() %>&coursId=<%= coursId %>" class="btn btn-success btn-sm mt-2">Ajouter Note</a>
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

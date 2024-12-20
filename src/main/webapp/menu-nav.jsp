<%@page import="jpa.Administrateur"%>
<%@page import="jpa.Enseignant"%>
<%@page import="jpa.Etudiant"%>

<%@ include file="header.jsp" %>
<nav class="navbar navbar-expand-lg navbar-light bg-light shadow">
    <div class="container">
        <a class="navbar-brand text-primary font-weight-bold" href="accueil.jsp">CY Tech</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ml-auto">
                <% if ("Administrateur".equals(role)) { %>
                    <li class="nav-item">
                        <a class="nav-link" href="AfficherCours?page=gestion">Gestion des cours</a>
                    </li>
                <% } %>
                <% if ("Administrateur".equals(role) || "Enseignant".equals(role)) { %>
                    <li class="nav-item">
                        <a class="nav-link" href="GestionEtudiants.jsp">Gestion des �tudiants</a>
                    </li>
                <% } %>
                <li class="nav-item">
                    <a class="nav-link" href="AfficherCours?page=liste">Liste des cours</a>
                </li>
               
                <li class="nav-item">
                    <a class="nav-link" href="RedirectionNotesServlet">Notes</a>
                </li>
           
                <% if (user != null) { %>
                    <li class="nav-item">
                        <a class="nav-link" href="AfficherInfos.jsp">Mes infos</a>
                    </li>
                <% } %>
                <li class="nav-item">
                    <a class="nav-link" href="Connexion.jsp">
                        <% if ("Non connect�".equals(role)) { %>Se connecter<% } else { %>Se d�connecter<% } %>
                    </a>
                </li>
            </ul>
        </div>
    </div>
</nav>

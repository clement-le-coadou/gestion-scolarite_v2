<%@page import="jpa.Administrateur"%>
<%@page import="jpa.Enseignant"%>
<%@page import="jpa.Etudiant"%>

<%@ include file="header.jsp" %>
<nav class="navbar navbar-expand-lg navbar-light bg-light"> 
    <div class="container"> 
        <span class="navbar-text">Rôle: <%= role %></span> 
        <ul class="navbar-nav ml-auto"> 
            <% if ("Administrateur".equals(role)) { %>
                <li class="nav-item"> 
                    <a class="nav-link" href="GestionCours.jsp">Gestion des cours</a> 
                </li> 
            <% } %>
             <% if ("Administrateur".equals(role) || ("Enseignant".equals(role))) { %>
                <li class="nav-item"> 
                    <a class="nav-link" href="GestionEtudiants.jsp">Liste étudiante</a> 
                </li> 
            <% } %>
            <li class="nav-item"> 
                <a class="nav-link" href="AfficherCours">Liste des cours</a> 
            </li> 
            <li class="nav-item"> <% if ("Enseignant".equals(role)){         	
            		%>
            		<a class="nav-link" href="ModifierNote.jsp">Gestion des notes</a>
            		<%}else if("Etudiant".equals(role)){ %>
            		<a class="nav-link" href="GestionNotes.jsp">Mes notes</a>
            		<%} %>
                 
            </li> 
            <li class="nav-item"> 
                <a class="nav-link" href="AfficherInfos.jsp">Mes infos</a> 
            </li> 
            <li class="nav-item"> 
            	
                <!--  -->
                <a class="nav-link" href="Connexion.jsp"><% if ("Non connecté".equals(role)){         	
            		%>
            		Se connecter
            		<%}else{ %>
            		Se déconnecter
            		<%} %></a> 
            </li> 
        </ul> 
    </div> 
</nav>
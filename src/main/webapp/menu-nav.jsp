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
                    <a class="nav-link" href="AfficherCours?page=gestion">Gestion des cours</a> 
                </li> 
            <% } %>
             <% if ("Administrateur".equals(role) || ("Enseignant".equals(role))) { %>
                <li class="nav-item"> 
                    <a class="nav-link" href="GestionEtudiants.jsp">Gestion des étudiants</a> 
                </li> 
            <% } %>
            <li class="nav-item"> 
                <a class="nav-link" href="AfficherCours?page=liste">Liste des cours</a> 
            </li> 
            <li class="nav-item">
            <a class="nav-link" href="RedirectionNotesServlet">Notes</a> 
                 
            </li> 
            <%if(user != null){ %>
            <li class="nav-item"> 
                <a class="nav-link" href="AfficherInfos.jsp">Mes infos</a> 
            </li> 
            <%} %>
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
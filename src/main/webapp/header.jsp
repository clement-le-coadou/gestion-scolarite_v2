<%@page import="model.Administrateur"%>
<%@page import="model.Enseignant"%>
<%@page import="model.Etudiant"%>
<header class="bg-primary text-white p-3 mb-4 shadow">
    <div class="container d-flex justify-content-between align-items-center">
        <h1><a class="text-white text-decoration-none" href="accueil.jsp">CY Tech</a></h1>
        <div>
            <%     // Récupération de l'objet utilisateur depuis la session
            Object user = session.getAttribute("username");
            String username;
            String role;
            
	        if (user != null) {
		        if (user instanceof Etudiant) {
		            Etudiant etudiant = (Etudiant) user;
		            
		             username = etudiant.getNom() + " " + etudiant.getPrenom();
		             role = "Etudiant";
		        } else if (user instanceof Enseignant) {
		            Enseignant enseignant = (Enseignant) user;
		            
		            username =  enseignant.getNom() + " " + enseignant.getPrenom();
		            role = "Enseignant";
		        } else if (user instanceof Administrateur) {
		            Administrateur administrateur = (Administrateur) user;
		            
		            username =  administrateur.getNom() + " " + administrateur.getPrenom();  
		            role = "Administrateur";
		        }
		        else{
			        username = "Non connecté";
			        role = "Non connecté";
		        }
		    } else {
		        username = "Non connecté";
		        role = "Non connecté";
		    }%>
            <span>Utilisateur : <%= username %></span><br>
            <span>Rôle : <%= role %></span>
        </div>
    </div>
</header>


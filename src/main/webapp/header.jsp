<%@page import="jpa.Administrateur"%>
<%@page import="jpa.Enseignant"%>
<%@page import="jpa.Etudiant"%>
<header class="bg-primary p-3"> 
    <div class="container"> 
        <div class="row d-flex align-items-center">
            <div class="col">
                <h1>CY Tech</h1> 
            </div>
            <div class="info-right col text-right">
            <%     // R�cup�ration de l'objet utilisateur depuis la session
            Object user = session.getAttribute("username");
            String username;
            
        if (user != null) {
	        if (user instanceof Etudiant) {
	            Etudiant etudiant = (Etudiant) user;
	            
	             username = etudiant.getNom() + " " + etudiant.getPrenom();
	        } else if (user instanceof Enseignant) {
	            Enseignant enseignant = (Enseignant) user;
	            
	            username =  enseignant.getNom() + " " + enseignant.getPrenom();
	        } else if (user instanceof Administrateur) {
	            Administrateur administrateur = (Administrateur) user;
	            
	            username =  administrateur.getNom() + " " + administrateur.getPrenom();          
	        }
	        else{
		        username = "Non connect�";
	        }
	    } else {
	        username = "Non connect�";
	    }%>
                <span>Utilisateur: <% out.print(username);%></span> <br>
            </div>
        </div>
    </div> 
</header> 
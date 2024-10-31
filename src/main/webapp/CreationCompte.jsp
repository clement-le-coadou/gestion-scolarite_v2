<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Création de Compte</title>
    <script>
        function showForm() {
            const userType = document.getElementById("userType").value;
            document.getElementById("etudiantForm").style.display = userType === "etudiant" ? "block" : "none";
            document.getElementById("enseignantForm").style.display = userType === "enseignant" ? "block" : "none";
            document.getElementById("selectedUserType").value = userType; // Définir le type sélectionné
        }
    </script>
</head>
<body>

<h2>Création de Compte</h2>

<!-- Vérification et affichage du message d'erreur -->
<% 
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null) { 
%>
    <div style="color:red; font-weight:bold; margin-bottom:20px;">
        <%= errorMessage %>
    </div>
<% 
    } 
%>

<!-- Sélection du type de compte -->
<label for="userType">Choisissez le type de compte :</label>
<select id="userType" onchange="showForm()">
    <option value="">--Sélectionner--</option>
    <option value="etudiant">Étudiant</option>
    <option value="enseignant">Enseignant</option>
</select>

<!-- Formulaire pour étudiant -->
<form action="CreerCompte" method="post" style="margin-top:20px;">
    <!-- Champ caché pour stocker le type d'utilisateur -->
    <input type="hidden" id="selectedUserType" name="userType" value="">

    <!-- Formulaire Étudiant -->
    <div id="etudiantForm" style="display:none;">
        <h3>Informations Étudiant</h3>
        <label for="etudiantNom">Nom :</label>
        <input type="text" id="etudiantNom" name="nom"><br><br>

        <label for="etudiantPrenom">Prénom :</label>
        <input type="text" id="etudiantPrenom" name="prenom"><br><br>

        <label for="etudiantEmail">Email :</label>
        <input type="email" id="etudiantEmail" name="email"><br><br>

        <label for="etudiantMotDePasse">Mot de Passe :</label>
        <input type="password" id="etudiantMotDePasse" name="motDePasse"><br><br>

        <label for="etudiantDateNaissance">Date de Naissance :</label>
        <input type="date" id="etudiantDateNaissance" name="dateNaissance"><br><br>

        <label for="etudiantContact">Contact :</label>
        <input type="text" id="etudiantContact" name="contact"><br><br>
    </div>

    <!-- Formulaire Enseignant -->
    <div id="enseignantForm" style="display:none;">
        <h3>Informations Enseignant</h3>
        <label for="enseignantNom">Nom :</label>
        <input type="text" id="enseignantNom" name="nom"><br><br>

        <label for="enseignantPrenom">Prénom :</label>
        <input type="text" id="enseignantPrenom" name="prenom"><br><br>

        <label for="enseignantEmail">Email :</label>
        <input type="email" id="enseignantEmail" name="email"><br><br>

        <label for="enseignantMotDePasse">Mot de Passe :</label>
        <input type="password" id="enseignantMotDePasse" name="motDePasse"><br><br>

        <label for="enseignantContact">Contact :</label>
        <input type="text" id="enseignantContact" name="contact"><br><br>
    </div>

    <!-- Bouton de soumission -->
    <input type="submit" value="Créer Compte">
</form>

</body>
</html>

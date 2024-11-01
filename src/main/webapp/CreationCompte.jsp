<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Création de Compte</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <script>
        function toggleDateField() {
            const userType = document.getElementById("userType").value;
            const dateField = document.getElementById("dateNaissanceField");

            // Show or hide the date of birth field based on user type
            if (userType === "etudiant") {
                dateField.style.display = "block";
                document.getElementById("dateNaissance").required = true;
            } else {
                dateField.style.display = "none";
                document.getElementById("dateNaissance").required = false;
            }

            // Update hidden input for user type
            document.getElementById("selectedUserType").value = userType;
            console.log(document.getElementById("selectedUserType").value);
        }
    </script>
</head>
<body class="bg-light">

<div class="container mt-5">
    <h2 class="text-center mb-4">Création de Compte</h2>

    <!-- Affichage d'un message d'erreur si présent -->
    <% 
        String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage != null) { 
    %>
        <div class="alert alert-danger" role="alert">
            <%= errorMessage %>
        </div>
    <% 
        } 
    %>

    <!-- Sélection du type de compte -->
    <div class="form-group">
        <label for="userType">Choisissez le type de compte :</label>
        <select id="userType" class="form-control" onchange="toggleDateField()">
            <option value="">--Sélectionner--</option>
            <option value="etudiant">Étudiant</option>
            <option value="enseignant">Enseignant</option>
        </select>
    </div>

    <!-- Formulaire unique -->
    <form action="CreerCompte" method="post">
        <!-- Champ caché pour stocker le type d'utilisateur -->
        <input type="hidden" id="selectedUserType" name="userType" value="">

        <!-- Informations générales -->
        <div class="form-group">
            <label for="nom">Nom :</label>
            <input type="text" id="nom" name="nom" class="form-control" required>
        </div>
        <div class="form-group">
            <label for="prenom">Prénom :</label>
            <input type="text" id="prenom" name="prenom" class="form-control" required>
        </div>
        <div class="form-group">
            <label for="email">Email :</label>
            <input type="email" id="email" name="email" class="form-control" required>
        </div>

        <!-- Champ spécifique à l'étudiant : Date de Naissance -->
        <div id="dateNaissanceField" style="display:none;" class="form-group">
            <label for="dateNaissance">Date de Naissance :</label>
            <input type="date" id="dateNaissance" name="dateNaissance" class="form-control">
        </div>

        <div class="form-group">
            <label for="contact">Contact :</label>
            <input type="text" id="contact" name="contact" class="form-control">
        </div>
        
        <div class="form-group">
            <label for="motDePasse">Mot de Passe :</label>
            <input type="password" id="motDePasse" name="motDePasse" class="form-control" required>
        </div>

        <!-- Bouton de soumission -->
        <button type="submit" class="btn btn-primary btn-block">Créer Compte</button>
    </form>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

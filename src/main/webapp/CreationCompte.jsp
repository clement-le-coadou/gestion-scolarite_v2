<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Création de Compte</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <script>
        function showForm() {
            const userType = document.getElementById("userType").value;
            document.getElementById("etudiantForm").style.display = userType === "etudiant" ? "block" : "none";
            document.getElementById("enseignantForm").style.display = userType === "enseignant" ? "block" : "none";
            document.getElementById("selectedUserType").value = userType; // Définir le type sélectionné
        }
    </script>
</head>
<body class="bg-light">

<div class="container mt-5">
    <h2 class="text-center mb-4">Création de Compte</h2>

    <!-- Vérification et affichage du message d'erreur -->
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
        <select id="userType" class="form-control" onchange="showForm()">
            <option value="">--Sélectionner--</option>
            <option value="etudiant">Étudiant</option>
            <option value="enseignant">Enseignant</option>
        </select>
    </div>

    <!-- Formulaire principal -->
    <form action="CreerCompte" method="post">
        <!-- Champ caché pour stocker le type d'utilisateur -->
        <input type="hidden" id="selectedUserType" name="userType" value="">

        <!-- Formulaire Étudiant -->
        <div id="etudiantForm" style="display:none;" class="bg-white p-4 rounded shadow-sm mb-4">
            <h4>Informations Étudiant</h4>
            <div class="form-group">
                <label for="etudiantNom">Nom :</label>
                <input type="text" id="etudiantNom" name="nom" class="form-control" required>
            </div>
            <div class="form-group">
                <label for="etudiantPrenom">Prénom :</label>
                <input type="text" id="etudiantPrenom" name="prenom" class="form-control" required>
            </div>
            <div class="form-group">
                <label for="etudiantEmail">Email :</label>
                <input type="email" id="etudiantEmail" name="email" class="form-control" required>
            </div>
            <div class="form-group">
                <label for="etudiantMotDePasse">Mot de Passe :</label>
                <input type="password" id="etudiantMotDePasse" name="motDePasse" class="form-control" required>
            </div>
            <div class="form-group">
                <label for="etudiantDateNaissance">Date de Naissance :</label>
                <input type="date" id="etudiantDateNaissance" name="dateNaissance" class="form-control" required>
            </div>
            <div class="form-group">
                <label for="etudiantContact">Contact :</label>
                <input type="text" id="etudiantContact" name="contact" class="form-control">
            </div>
        </div>

        <!-- Formulaire Enseignant -->
        <div id="enseignantForm" style="display:none;" class="bg-white p-4 rounded shadow-sm mb-4">
            <h4>Informations Enseignant</h4>
            <div class="form-group">
                <label for="enseignantNom">Nom :</label>
                <input type="text" id="enseignantNom" name="nom" class="form-control" required>
            </div>
            <div class="form-group">
                <label for="enseignantPrenom">Prénom :</label>
                <input type="text" id="enseignantPrenom" name="prenom" class="form-control" required>
            </div>
            <div class="form-group">
                <label for="enseignantEmail">Email :</label>
                <input type="email" id="enseignantEmail" name="email" class="form-control" required>
            </div>
            <div class="form-group">
                <label for="enseignantMotDePasse">Mot de Passe :</label>
                <input type="password" id="enseignantMotDePasse" name="motDePasse" class="form-control" required>
            </div>
            <div class="form-group">
                <label for="enseignantContact">Contact :</label>
                <input type="text" id="enseignantContact" name="contact" class="form-control">
            </div>
        </div>

        <!-- Bouton de soumission -->
        <button type="submit" class="btn btn-primary btn-block">Créer Compte</button>
    </form>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

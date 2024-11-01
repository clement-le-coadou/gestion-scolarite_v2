<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Modifier Étudiant</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <h2 class="text-center mb-4">Modifier Étudiant</h2>

    <form action="ModifierEtudiant" method="post" class="p-4 bg-white shadow-sm rounded">
        <input type="hidden" name="id" value="${etudiant.id}">

        <div class="form-group">
            <label for="nom">Nom :</label>
            <input type="text" class="form-control" id="nom" name="nom" value="${etudiant.nom}" required>
        </div>

        <div class="form-group">
            <label for="prenom">Prénom :</label>
            <input type="text" class="form-control" id="prenom" name="prenom" value="${etudiant.prenom}" required>
        </div>

        <div class="form-group">
            <label for="dateNaissance">Date de Naissance :</label>
            <input type="date" class="form-control" id="dateNaissance" name="dateNaissance" value="${etudiant.dateNaissance != null ? etudiant.dateNaissance : ''}" required>
        </div>

        <div class="form-group">
            <label for="email">Email :</label>
            <input type="email" class="form-control" id="email" name="email" value="${etudiant.email}" required>
        </div>

        <div class="form-group">
            <label for="contact">Contact :</label>
            <input type="text" class="form-control" id="contact" name="contact" value="${etudiant.contact}">
        </div>

        <button type="submit" class="btn btn-primary btn-block">Enregistrer les modifications</button>
    </form>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

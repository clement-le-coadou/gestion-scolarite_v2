<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Modifier Étudiant</title>
</head>
<body>
    <h2>Modifier Étudiant</h2>
    <form action="ModifierEtudiant" method="post">
        <input type="hidden" name="id" value="${etudiant.id}">
        
        <label for="nom">Nom :</label>
        <input type="text" id="nom" name="nom" value="${etudiant.nom}"><br><br>

        <label for="prenom">Prénom :</label>
        <input type="text" id="prenom" name="prenom" value="${etudiant.prenom}"><br><br>

        <input type="submit" value="Enregistrer les modifications">
    </form>
</body>
</html>

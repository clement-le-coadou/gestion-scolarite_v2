<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Erreur <%= request.getAttribute("errorCode") %></title>
    <link rel="stylesheet" href="resources/bootstrap.min.css">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <h1 class="text-center text-danger">Erreur <%= request.getAttribute("errorCode") %></h1>
        <p class="text-center">
            <%= request.getAttribute("errorMessage") %>
        </p>
        <div class="text-center">
            <a href="/" class="btn btn-primary">Retour à l'accueil</a>
        </div>
    </div>
</body>
</html>

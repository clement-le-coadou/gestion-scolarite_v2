<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Accueil - CY Tech</title>
    <link rel="stylesheet" href="resources/bootstrap.min.css">
    <link rel="stylesheet" href="resources/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <%@ include file="menu-nav.jsp" %>

    <div class="container my-5">
        <div class="jumbotron text-center bg-light shadow">
			<h1 class="display-4">Bienvenue à CY Tech</h1>
            <p class="lead">Gérez les étudiants, enseignants, cours et résultats efficacement.</p>
            <hr class="my-4">
        </div>

        <div class="row text-center mt-5">
            <div class="col-md-4">
                <a href="AfficherEtudiants" class="text-decoration-none">
                    <div class="card shadow">
                        <div class="card-body">
                            <i class="fas fa-user-graduate fa-3x text-primary mb-3"></i>
                            <h5 class="card-title">Liste des Étudiants</h5>
                            <p class="card-text">Consultez et gérez les informations des étudiants.</p>
                        </div>
                    </div>
                </a>
            </div>

            <div class="col-md-4">
                <a href="AfficherCours" class="text-decoration-none">
                    <div class="card shadow">
                        <div class="card-body">
                            <i class="fas fa-book-open fa-3x text-success mb-3"></i>
                            <h5 class="card-title">Liste des Cours</h5>
                            <p class="card-text">Explorez et attribuez les cours aux étudiants.</p>
                        </div>
                    </div>
                </a>
            </div>

            <div class="col-md-4">
                <a href="AfficherEnseignants" class="text-decoration-none">
                    <div class="card shadow">
                        <div class="card-body">
                            <i class="fas fa-chalkboard-teacher fa-3x text-warning mb-3"></i>
                            <h5 class="card-title">Liste des Enseignants</h5>
                            <p class="card-text">Consultez et attribuez des cours aux enseignants.</p>
                        </div>
                    </div>
                </a>
            </div>
        </div>
    </div>
</body>
</html>

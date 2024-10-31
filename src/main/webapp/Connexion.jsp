<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Page de Connexion</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="d-flex align-items-center justify-content-center bg-light" style="height: 100vh;">

<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-4">
            <div class="card shadow-sm">
                <div class="card-body">
                    <h2 class="text-center mb-4">Connexion</h2>
                    <form action="Login" method="post">
                        <div class="form-group">
                            <label for="username">E-mail</label>
                            <input type="email" class="form-control" id="username" name="username" placeholder="Entrez votre adresse mail" required>
                        </div>
                        <div class="form-group">
                            <label for="password">Mot de passe</label>
                            <input type="password" class="form-control" id="password" name="password" placeholder="Entrez votre mot de passe" required>
                        </div>
                        <div class="form-group">
                            <label for="user_type">Type d'utilisateur</label>
                            <select class="form-control" id="user_type" name="user_type" required>
                                <option value="">Sélectionnez votre type</option>
                                <option value="administrateur">Administrateur</option>
                                <option value="etudiant">Étudiant</option>
                                <option value="enseignant">Enseignant</option>
                            </select>
                        </div>
                        <button type="submit" class="btn btn-primary btn-block">Connexion</button>
                	</form>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

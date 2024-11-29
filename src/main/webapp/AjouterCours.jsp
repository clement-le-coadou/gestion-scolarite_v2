<%@ page import="model.Enseignant" %>
<%@ page import="daogenerique.CrudGeneric" %>
<%@ page import="org.hibernate.SessionFactory" %>
<%@ page import="org.hibernate.cfg.Configuration" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ajouter un Cours</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <h2 class="text-center mb-4">Ajouter un Cours</h2>

    <form action="AjouterCours" method="post">
        <div class="form-group">
            <label for="nom">Nom du Cours :</label>
            <input type="text" class="form-control" id="nom" name="nom" required>
        </div>

        <div class="form-group">
            <label for="description">Description :</label>
            <textarea class="form-control" id="description" name="description" rows="4" required></textarea>
        </div>

        <div class="form-group">
            <label for="enseignantId">Enseignant :</label>
            <select class="form-control" id="enseignantId" name="enseignantId" required>
                <option value="">--Sélectionner--</option>
                <%
                    SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
                    CrudGeneric<Enseignant> enseignantDAO = new CrudGeneric<>(sessionFactory, Enseignant.class);
                    List<Enseignant> enseignants = enseignantDAO.findAll();

                    for (Enseignant enseignant : enseignants) {
                %>
                    <option value="<%= enseignant.getId() %>"><%= enseignant.getNom() %> <%= enseignant.getPrenom() %></option>
                <%
                    }
                %>
            </select>
        </div>

        <button type="submit" class="btn btn-success">Ajouter le Cours</button>
        <a href="gestionCours.jsp" class="btn btn-secondary">Annuler</a>
    </form>
</div>

</body>
</html>

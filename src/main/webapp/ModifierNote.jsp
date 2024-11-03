<%@ page import="jpa.Note" %>
<%@ page import="jpa.Etudiant" %>
<%@ page import="jpa.Cours" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Modifier la Note</title>
    <link rel="stylesheet" href="resources/bootstrap.min.css">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <h2 class="text-center mb-4">Modifier la Note</h2>

        <form action="GestionNotes" method="post">
            <input type="hidden" name="id" value="<%= request.getAttribute("note") != null ? ((Note) request.getAttribute("note")).getId() : "" %>">

            <div class="form-group">
                <label for="etudiant">Étudiant</label>
                <input type="text" class="form-control" id="etudiant" value="<%= ((Note) request.getAttribute("note")).getEtudiant().getNom() %> <%= ((Note) request.getAttribute("note")).getEtudiant().getPrenom() %>" readonly>
            </div>

            <div class="form-group">
                <label for="cours">Cours</label>
                <input type="text" class="form-control" id="cours" value="<%= ((Note) request.getAttribute("note")).getCours().getNom() %>" readonly>
            </div>

            <div class="form-group">
                <label for="note">Note</label>
                <input type="number" step="0.1" class="form-control" id="note" name="note" value="<%= ((Note) request.getAttribute("note")).getNote() %>" required>
            </div>

            <button type="submit" class="btn btn-primary">Enregistrer</button>
            <a href="GestionNotes.jsp" class="btn btn-secondary">Annuler</a>
        </form>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

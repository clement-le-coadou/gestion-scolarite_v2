package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import daogenerique.CrudGeneric;
import jpa.Etudiant;
import jpa.Cours;
import jpa.Inscription;

import java.io.IOException;

@WebServlet("/InscrireEtudiant")
public class InscrireEtudiant extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long etudiantId = Long.parseLong(request.getParameter("etudiantId"));
        Long coursId = Long.parseLong(request.getParameter("coursId"));

        CrudGeneric<Etudiant> etudiantDAO = new CrudGeneric<>(sessionFactory, Etudiant.class);
        CrudGeneric<Cours> coursDAO = new CrudGeneric<>(sessionFactory, Cours.class);

        // Récupérer l'étudiant et le cours
        Etudiant etudiant = etudiantDAO.read(etudiantId);
        Cours cours = coursDAO.read(coursId);

        // Créer une nouvelle inscription
        Inscription inscription = new Inscription();
        inscription.setEtudiant(etudiant);
        inscription.setCours(cours);

        // Enregistrer l'inscription
        CrudGeneric<Inscription> inscriptionDAO = new CrudGeneric<>(sessionFactory, Inscription.class);
        inscriptionDAO.create(inscription);

        // Rediriger vers la page de confirmation ou liste des cours
        response.sendRedirect("AfficherCours?page=gestion");
    }
}

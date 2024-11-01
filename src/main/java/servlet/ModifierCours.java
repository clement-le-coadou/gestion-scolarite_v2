package servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jpa.Cours;
import jpa.Enseignant;
import daogenerique.CrudGeneric;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;

@WebServlet("/ModifierCours")
public class ModifierCours extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long coursId = Long.valueOf(request.getParameter("id"));

        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        CrudGeneric<Cours> coursDAO = new CrudGeneric<>(sessionFactory, Cours.class);

        // Récupérer le cours par ID
        Cours cours = coursDAO.read(coursId);
        
        // Passer le cours à la JSP de modification
        request.setAttribute("cours", cours);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("ModifierCours.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long coursId = Long.valueOf(request.getParameter("id"));
        String nom = request.getParameter("nom");
        String description = request.getParameter("description");
        Long enseignantId = Long.valueOf(request.getParameter("enseignantId"));

        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        CrudGeneric<Cours> coursDAO = new CrudGeneric<>(sessionFactory, Cours.class);

        // Récupérer le cours par ID et mettre à jour les informations
        Cours cours = coursDAO.read(coursId);
        cours.setNom(nom);
        cours.setDescription(description);
        
        // Récupérer l'enseignant et le définir dans le cours
        CrudGeneric<Enseignant> enseignantDAO = new CrudGeneric<>(sessionFactory, Enseignant.class);
        Enseignant enseignant = enseignantDAO.read(enseignantId);
        cours.setEnseignant(enseignant);

        // Enregistrer les modifications
        coursDAO.update(cours);
        
        // Redirection après modification
        response.sendRedirect("AfficherCours");
    }
}

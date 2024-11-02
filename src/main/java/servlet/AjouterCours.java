package servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import daogenerique.CrudGeneric;
import jpa.Cours;
import jpa.Enseignant;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


@WebServlet("/AjouterCours")
public class AjouterCours extends HttpServlet {
    SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    CrudGeneric<Cours> coursDAO = new CrudGeneric<>(sessionFactory, Cours.class);

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nom = request.getParameter("nom");
        String description = request.getParameter("description");
        Long enseignantId = Long.parseLong(request.getParameter("enseignantId"));

        // Récupération de l'enseignant par son ID
        CrudGeneric<Enseignant> enseignantDAO = new CrudGeneric<>(sessionFactory, Enseignant.class);
        Enseignant enseignant = enseignantDAO.read(enseignantId);

        Cours cours = new Cours(nom, description, enseignant);
        coursDAO.create(cours);

        response.sendRedirect("GestionCours.jsp");  // Redirection vers la liste des cours
    }
}

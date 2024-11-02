package servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jpa.Enseignant;
import daogenerique.CrudGeneric;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.List;

@WebServlet("/AfficherEnseignants")
public class AfficherEnseignants extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Configurer la SessionFactory pour Hibernate
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        CrudGeneric<Enseignant> enseignantDAO = new CrudGeneric<>(sessionFactory, Enseignant.class);

        // R�cup�rer tous les enseignants
        List<Enseignant> enseignants = enseignantDAO.findAll();
        
        // Debugging : Affichage des ID de chaque enseignant dans la console
        for (Enseignant enseignant : enseignants) {
            System.out.println("Enseignant ID: " + enseignant.getId() + ", Nom: " + enseignant.getNom());
        }

        // Passer la liste des enseignants � la JSP
        request.setAttribute("enseignants", enseignants);

        // Rediriger vers la page JSP pour l'affichage
        RequestDispatcher dispatcher = request.getRequestDispatcher("GestionEnseignants.jsp");
        dispatcher.forward(request, response);

        // Fermer la session Hibernate
        sessionFactory.close();
    }
}

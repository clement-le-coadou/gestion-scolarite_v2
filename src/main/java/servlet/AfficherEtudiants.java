package servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jpa.Etudiant;
import daogenerique.CrudGeneric;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.List;

@WebServlet("/AfficherEtudiants")
public class AfficherEtudiants extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        CrudGeneric<Etudiant> etudiantDAO = new CrudGeneric<>(sessionFactory, Etudiant.class);

        // R�cup�rer tous les �tudiants
        List<Etudiant> etudiants = etudiantDAO.findAll();
        for (Etudiant etudianttemp : etudiants) {
            System.out.println("Etudiant ID: " + etudianttemp.getId());
        }
        
        // Passer la liste des �tudiants � la JSP
        request.setAttribute("etudiants", etudiants);
        
        // Redirection vers la page d'affichage
        RequestDispatcher dispatcher = request.getRequestDispatcher("GestionEtudiants.jsp");
        dispatcher.forward(request, response);
    }
}

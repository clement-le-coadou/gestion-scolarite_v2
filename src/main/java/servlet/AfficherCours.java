package servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jpa.Cours;
import daogenerique.CrudGeneric;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.List;

@WebServlet("/AfficherCours")
public class AfficherCours extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Configurer la SessionFactory pour Hibernate
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        CrudGeneric<Cours> coursDAO = new CrudGeneric<>(sessionFactory, Cours.class);

        // R�cup�rer tous les cours
        List<Cours> coursList = coursDAO.findAll();
        
        // Debugging : Affichage des ID de chaque cours dans la console
        for (Cours cours : coursList) {
            System.out.println("Cours ID: " + cours.getId() + ", Nom: " + cours.getNom());
        }

        // Passer la liste des cours � la JSP
        request.setAttribute("coursList", coursList);

        // Rediriger vers la page JSP pour l'affichage
        RequestDispatcher dispatcher = request.getRequestDispatcher("AfficherCours.jsp");
        dispatcher.forward(request, response);

        // Fermer la session Hibernate
        sessionFactory.close();
    }
}

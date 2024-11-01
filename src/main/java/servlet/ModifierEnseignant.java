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

@WebServlet("/ModifierEnseignant")
public class ModifierEnseignant extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long enseignantId = Long.valueOf(request.getParameter("id"));

        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        CrudGeneric<Enseignant> enseignantDAO = new CrudGeneric<>(sessionFactory, Enseignant.class);

        // Récupérer l'enseignant par ID
        Enseignant enseignant = enseignantDAO.read(enseignantId);
        
        // Passer l'enseignant à la JSP de modification
        request.setAttribute("enseignant", enseignant);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("ModifierEnseignant.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long enseignantId = Long.valueOf(request.getParameter("id"));
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String contact = request.getParameter("contact");

        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        CrudGeneric<Enseignant> enseignantDAO = new CrudGeneric<>(sessionFactory, Enseignant.class);

        // Récupérer l'enseignant par ID et mettre à jour les informations
        Enseignant enseignant = enseignantDAO.read(enseignantId);
        enseignant.setNom(nom);
        enseignant.setPrenom(prenom);
        enseignant.setEmail(email);
        enseignant.setContact(contact);

        // Enregistrer les modifications
        enseignantDAO.update(enseignant);
        
        // Redirection après modification
        response.sendRedirect("AfficherEnseignants");
    }
}

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

@WebServlet("/GestionEtudiants")
public class GestionEtudiants extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long etudiantId = Long.valueOf(request.getParameter("id"));

        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        CrudGeneric<Etudiant> etudiantDAO = new CrudGeneric<>(sessionFactory, Etudiant.class);

        // Récupérer l'étudiant par ID
        Etudiant etudiant = etudiantDAO.read(etudiantId);
        
        // Passer l'étudiant à la JSP de modification
        request.setAttribute("etudiant", etudiant);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("GestionEtudiants.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long etudiantId = Long.valueOf(request.getParameter("id"));
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");

        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        CrudGeneric<Etudiant> etudiantDAO = new CrudGeneric<>(sessionFactory, Etudiant.class);

        // Récupérer l'étudiant par ID et mettre à jour les informations
        Etudiant etudiant = etudiantDAO.read(etudiantId);
        etudiant.setNom(nom);
        etudiant.setPrenom(prenom);
        etudiant.setEmail(email);

        // Enregistrer les modifications
        etudiantDAO.update(etudiant);
        
        // Redirection après modification
        response.sendRedirect("GestionEtudiants.jsp");
    }
}

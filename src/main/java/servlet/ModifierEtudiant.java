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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/ModifierEtudiant")
public class ModifierEtudiant extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long etudiantId = Long.valueOf(request.getParameter("id"));

        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        CrudGeneric<Etudiant> etudiantDAO = new CrudGeneric<>(sessionFactory, Etudiant.class);

        // R�cup�rer l'�tudiant par ID
        Etudiant etudiant = etudiantDAO.read(etudiantId);
        
        // Passer l'�tudiant � la JSP de modification
        request.setAttribute("etudiant", etudiant);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("ModifierEtudiant.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long etudiantId = Long.valueOf(request.getParameter("id"));
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String dateNaissanceString = request.getParameter("dateNaissance");
        String email = request.getParameter("email");
        String contact = request.getParameter("contact");

        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        CrudGeneric<Etudiant> etudiantDAO = new CrudGeneric<>(sessionFactory, Etudiant.class);

        // R�cup�rer l'�tudiant par ID et mettre � jour les informations
        Etudiant etudiant = etudiantDAO.read(etudiantId);
        etudiant.setNom(nom);
        etudiant.setPrenom(prenom);

        // Convertir la date de naissance de String � Date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dateNaissance = sdf.parse(dateNaissanceString);
            etudiant.setDateNaissance(dateNaissance);
        } catch (ParseException e) {
            e.printStackTrace(); // G�rer l'exception de mani�re appropri�e
        }

        etudiant.setEmail(email);
        etudiant.setContact(contact);

        // Enregistrer les modifications
        etudiantDAO.update(etudiant);
        
        // Redirection apr�s modification
        response.sendRedirect("AfficherEtudiants");
    }
}

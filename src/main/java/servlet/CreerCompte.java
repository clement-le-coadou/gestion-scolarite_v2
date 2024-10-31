package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jpa.Enseignant;
import jpa.Etudiant;

import java.io.IOException;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import daogenerique.CrudGeneric;


@WebServlet("/CreerCompte")
public class CreerCompte extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    	
        String userType = request.getParameter("userType");  // Type de compte : étudiant ou enseignant

        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String motDePasse = request.getParameter("motDePasse");
        String contact = request.getParameter("contact");

        // Vérifier le type d'utilisateur
        if ("etudiant".equals(userType)) {
        	CrudGeneric<Etudiant> etudiantDAO = new CrudGeneric<>(sessionFactory, Etudiant.class);
            String dateNaissance = request.getParameter("dateNaissance");

            // Logique de création pour un étudiant
            Etudiant etudiant = new Etudiant();
            etudiant.setContact(contact);
            etudiant.setDateNaissance(null);
            etudiant.setEmail(email);
            etudiant.setNom(nom);
            etudiant.setPrenom(prenom);
            etudiant.setMotDePasse(motDePasse);
            etudiantDAO.create(etudiant); // Appel à la méthode DAO pour sauvegarder
            response.getWriter().println("Compte Étudiant créé avec succès !");
        } else if ("enseignant".equals(userType)) {
        	CrudGeneric<Enseignant> enseignantDAO = new CrudGeneric<>(sessionFactory, Enseignant.class);
            // Logique de création pour un enseignant
            Enseignant enseignant = new Enseignant();
            enseignant.setContact(contact);
            enseignant.setEmail(email);
            enseignant.setNom(nom);
            enseignant.setPrenom(prenom);
            enseignant.setMotDePasse(motDePasse);
            enseignantDAO.create(enseignant); // Appel à la méthode DAO pour sauvegarder
            response.getWriter().println("Compte Enseignant créé avec succès !");
        } else {
            response.getWriter().println("Type de compte invalide.");
        }
    }
}
package servlet;

import jakarta.servlet.RequestDispatcher;
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
import exceptions.UniqueConstraintViolationException;

@WebServlet("/CreerCompte")
public class CreerCompte extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        
        String userType = request.getParameter("userType");
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String motDePasse = request.getParameter("motDePasse");
        String contact = request.getParameter("contact");
        String dateNaissance = request.getParameter("dateNaissance");

        // Log pour debug : Afficher toutes les informations reçues
        System.out.println("=== Informations du formulaire ===");
        System.out.println("Type d'utilisateur: " + userType);
        System.out.println("Nom: " + nom);
        System.out.println("Prénom: " + prenom);
        System.out.println("Email: " + email);
        System.out.println("Mot de passe: " + motDePasse);
        System.out.println("Contact: " + contact);
        if ("etudiant".equals(userType)) {
            System.out.println("Date de naissance: " + dateNaissance);
        }
        System.out.println("===================================");
        // Vérification des champs vides
        if (nom == null || nom.isEmpty() || prenom == null || prenom.isEmpty() ||
            email == null || email.isEmpty() || motDePasse == null || motDePasse.isEmpty() ||
            contact == null || contact.isEmpty() ||
            ("etudiant".equals(userType) && (request.getParameter("dateNaissance") == null || request.getParameter("dateNaissance").isEmpty()))) {

            request.setAttribute("errorMessage", "Tous les champs sont obligatoires.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("CreationCompte.jsp");
            dispatcher.forward(request, response);
            return;
        }

        try {
            if ("etudiant".equals(userType)) {
                CrudGeneric<Etudiant> etudiantDAO = new CrudGeneric<>(sessionFactory, Etudiant.class);

                // Vérifier si l'email existe déjà
                if (etudiantDAO.findByEmail(email) != null) {
                    throw new UniqueConstraintViolationException("L'email est déjà utilisé.");
                }

                // Logique de création pour un étudiant
                Etudiant etudiant = new Etudiant();
                etudiant.setContact(contact);
                etudiant.setDateNaissance(null); // Ajoutez ici la logique pour convertir la date en format Date
                etudiant.setEmail(email);
                etudiant.setNom(nom);
                etudiant.setPrenom(prenom);
                etudiant.setMotDePasse(motDePasse);
                etudiantDAO.create(etudiant);
                response.getWriter().println("Compte Étudiant créé avec succès !");
                
            } else if ("enseignant".equals(userType)) {
                CrudGeneric<Enseignant> enseignantDAO = new CrudGeneric<>(sessionFactory, Enseignant.class);

                // Vérifier si l'email existe déjà
                if (enseignantDAO.findByEmail(email) != null) {
                    throw new UniqueConstraintViolationException("L'email est déjà utilisé.");
                }

                // Logique de création pour un enseignant
                Enseignant enseignant = new Enseignant();
                enseignant.setContact(contact);
                enseignant.setEmail(email);
                enseignant.setNom(nom);
                enseignant.setPrenom(prenom);
                enseignant.setMotDePasse(motDePasse);
                enseignantDAO.create(enseignant);
                response.getWriter().println("Compte Enseignant créé avec succès !");
                
            } else {
                request.setAttribute("errorMessage", "Type de compte invalide.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("CreationCompte.jsp");
                dispatcher.forward(request, response);
                return;
            }

            // Redirection après création réussie
            RequestDispatcher dispatcher = request.getRequestDispatcher("Connexion.jsp");
            dispatcher.forward(request, response);

        } catch (UniqueConstraintViolationException e) {
            // Exception pour email non unique
            request.setAttribute("errorMessage", e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("CreationCompte.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            // Exception générique pour tout autre problème
            request.setAttribute("errorMessage", "Une erreur est survenue lors de la création du compte. Veuillez réessayer.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("CreationCompte.jsp");
            dispatcher.forward(request, response);
        }
    }
}

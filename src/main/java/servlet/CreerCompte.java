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
import java.text.SimpleDateFormat;
import java.util.Locale;

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

        // Log pour debug : Afficher toutes les informations re�ues
        System.out.println("=== Informations du formulaire ===");
        System.out.println("Type d'utilisateur: " + userType);
        System.out.println("Nom: " + nom);
        System.out.println("Pr�nom: " + prenom);
        System.out.println("Email: " + email);
        System.out.println("Mot de passe: " + motDePasse);
        System.out.println("Contact: " + contact);
        if ("etudiant".equals(userType)) {
            System.out.println("Date de naissance: " + dateNaissance);
        }
        System.out.println("===================================");
        // V�rification des champs vides
        if (nom == null || nom.isEmpty() || prenom == null || prenom.isEmpty() ||
            email == null || email.isEmpty() || motDePasse == null || motDePasse.isEmpty() ||
            contact == null || contact.isEmpty() ||
            ("etudiant".equals(userType) && (request.getParameter("dateNaissance") == null || request.getParameter("dateNaissance").isEmpty()))) {

            request.setAttribute("errorMessage", "Tous les champs sont obligatoires.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("CreationCompte.jsp");
            dispatcher.forward(request, response);
            return;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if ("etudiant".equals(userType)) {
                CrudGeneric<Etudiant> etudiantDAO = new CrudGeneric<>(sessionFactory, Etudiant.class);

                // V�rifier si l'email existe d�j�
                if (etudiantDAO.findByEmail(email) != null) {
                    throw new UniqueConstraintViolationException("L'email est déjà utilisé.");
                }

                // Logique de cr�ation pour un �tudiant
                Etudiant etudiant = new Etudiant();
                etudiant.setContact(contact);
                etudiant.setDateNaissance(formatter.parse(dateNaissance));
                etudiant.setEmail(email);
                etudiant.setNom(nom);
                etudiant.setPrenom(prenom);
                etudiant.setMotDePasse(motDePasse);
                etudiantDAO.create(etudiant);
                response.getWriter().println("Compte �tudiant créé avec succès !");
                
            } else if ("enseignant".equals(userType)) {
                CrudGeneric<Enseignant> enseignantDAO = new CrudGeneric<>(sessionFactory, Enseignant.class);

                // V�rifier si l'email existe d�j�
                if (enseignantDAO.findByEmail(email) != null) {
                    throw new UniqueConstraintViolationException("L'email est déjà utilisé.");
                }

                // Logique de cr�ation pour un enseignant
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

            // Redirection apr�s cr�ation r�ussie
            request.setAttribute("successMessage", "Compte créé avec succès.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("Connexion.jsp");
            dispatcher.forward(request, response);

        } catch (UniqueConstraintViolationException e) {
            // Exception pour email non unique
            request.setAttribute("errorMessage", e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("CreationCompte.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            // Exception g�n�rique pour tout autre probl�me
            request.setAttribute("errorMessage", "Une erreur est survenue lors de la cr�ation du compte. Veuillez réessayer."+"\n Message:"+e.getMessage()+"\n Trace:"+e.getStackTrace());
            RequestDispatcher dispatcher = request.getRequestDispatcher("CreationCompte.jsp");
            dispatcher.forward(request, response);
        }
    }
}

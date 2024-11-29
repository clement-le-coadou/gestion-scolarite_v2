package servlet;

import model.Enseignant;
import model.Etudiant;
import daogenerique.CrudGeneric;
import exceptions.UniqueConstraintViolationException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.Locale;

@Controller
public class CreerCompte {

    @Autowired
    private SessionFactory sessionFactory;

    private CrudGeneric<Etudiant> etudiantDAO;
    private CrudGeneric<Enseignant> enseignantDAO;

    // Constructeur pour initialiser les DAOs
    public CreerCompte() {
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
        this.etudiantDAO = new CrudGeneric<>(sessionFactory, Etudiant.class);
        this.enseignantDAO = new CrudGeneric<>(sessionFactory, Enseignant.class);
    }

    @GetMapping("/CreerCompte")
    public String afficherCreationComptePage() {
        return "CreationCompte"; // Affiche le formulaire de création de compte
    }

    @PostMapping("/CreerCompte")
    public String creerCompte(@RequestParam String userType,
                              @RequestParam String nom,
                              @RequestParam String prenom,
                              @RequestParam String email,
                              @RequestParam String motDePasse,
                              @RequestParam String contact,
                              @RequestParam(required = false) String dateNaissance,
                              Model model) {

        // Vérification des champs obligatoires
        if (nom == null || nom.isEmpty() || prenom == null || prenom.isEmpty() ||
            email == null || email.isEmpty() || motDePasse == null || motDePasse.isEmpty() ||
            contact == null || contact.isEmpty() ||
            ("etudiant".equals(userType) && (dateNaissance == null || dateNaissance.isEmpty()))) {

            model.addAttribute("errorMessage", "Tous les champs sont obligatoires.");
            return "CreationCompte"; // Retourner à la page de création de compte
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        try {
            if ("etudiant".equals(userType)) {
                // Vérifier si l'email existe déjà
                if (etudiantDAO.findByEmail(email) != null) {
                    throw new UniqueConstraintViolationException("L'email est déjà utilisé.");
                }

                // Création de l'étudiant
                Etudiant etudiant = new Etudiant();
                etudiant.setContact(contact);
                etudiant.setDateNaissance(formatter.parse(dateNaissance));
                etudiant.setEmail(email);
                etudiant.setNom(nom);
                etudiant.setPrenom(prenom);
                etudiant.setMotDePasse(motDePasse);
                etudiantDAO.create(etudiant);
                model.addAttribute("successMessage", "Compte étudiant créé avec succès !");
                
            } else if ("enseignant".equals(userType)) {
                // Vérifier si l'email existe déjà
                if (enseignantDAO.findByEmail(email) != null) {
                    throw new UniqueConstraintViolationException("L'email est déjà utilisé.");
                }

                // Création de l'enseignant
                Enseignant enseignant = new Enseignant();
                enseignant.setContact(contact);
                enseignant.setEmail(email);
                enseignant.setNom(nom);
                enseignant.setPrenom(prenom);
                enseignant.setMotDePasse(motDePasse);
                enseignantDAO.create(enseignant);
                model.addAttribute("successMessage", "Compte enseignant créé avec succès !");
                
            } else {
                model.addAttribute("errorMessage", "Type de compte invalide.");
                return "CreationCompte"; // Retourner à la page de création de compte
            }

            // Redirection après création réussie
            return "Connexion"; // Page de connexion après la création réussie

        } catch (UniqueConstraintViolationException e) {
            // Exception pour email non unique
            model.addAttribute("errorMessage", e.getMessage());
            return "CreationCompte"; // Retourner à la page de création de compte

        } catch (Exception e) {
            // Exception générique pour tout autre problème
            model.addAttribute("errorMessage", "Une erreur est survenue lors de la création du compte. Veuillez réessayer.");
            return "CreationCompte"; // Retourner à la page de création de compte
        }
    }
}

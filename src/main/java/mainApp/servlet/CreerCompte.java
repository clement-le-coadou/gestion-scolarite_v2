package mainApp.servlet;

import mainApp.model.Enseignant;
import mainApp.model.Etudiant;
import mainApp.service.EnseignantService;
import mainApp.service.EtudiantService;

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
    private EtudiantService etudiantService;

    @Autowired
    private EnseignantService enseignantService;

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

        try {
            if ("etudiant".equals(userType)) {
                // Vérifier si l'email existe déjà dans la base d'étudiants
                if (etudiantService.findByEmail(email) != null) {
                    model.addAttribute("errorMessage", "L'email est déjà utilisé par un étudiant.");
                    return "CreationCompte";
                }

                // Création d'un nouvel étudiant
                Etudiant etudiant = new Etudiant();
                etudiant.setContact(contact);
                etudiant.setDateNaissance(formatter.parse(dateNaissance));
                etudiant.setEmail(email);
                etudiant.setNom(nom);
                etudiant.setPrenom(prenom);
                etudiant.setMotDePasse(motDePasse);

                etudiantService.createEtudiant(etudiant);
                model.addAttribute("successMessage", "Compte étudiant créé avec succès !");
                
            } else if ("enseignant".equals(userType)) {
                // Vérifier si l'email existe déjà dans la base des enseignants
                if (enseignantService.findByEmail(email) != null) {
                    model.addAttribute("errorMessage", "L'email est déjà utilisé par un enseignant.");
                    return "CreationCompte";
                }

                // Création d'un nouvel enseignant
                Enseignant enseignant = new Enseignant();
                enseignant.setContact(contact);
                enseignant.setEmail(email);
                enseignant.setNom(nom);
                enseignant.setPrenom(prenom);
                enseignant.setMotDePasse(motDePasse);

                enseignantService.createEnseignant(enseignant);
                model.addAttribute("successMessage", "Compte enseignant créé avec succès !");
                
            } else {
                model.addAttribute("errorMessage", "Type de compte invalide.");
                return "CreationCompte";
            }

            // Redirection après création réussie
            return "Connexion"; // Page de connexion après la création réussie

        } catch (Exception e) {
            // Exception générique pour tout autre problème
            model.addAttribute("errorMessage", "Une erreur est survenue lors de la création du compte. Veuillez réessayer.");
            return "CreationCompte";
        }
    }
}

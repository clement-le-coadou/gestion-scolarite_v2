package mainApp.servlet;

import jakarta.servlet.http.HttpSession;
import mainApp.model.Administrateur;
import mainApp.model.Enseignant;
import mainApp.model.Etudiant;
import mainApp.service.AdministrateurService;
import mainApp.service.EnseignantService;
import mainApp.service.EtudiantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Login {

    private final EtudiantService etudiantService;
    private final EnseignantService enseignantService;
    private final AdministrateurService administrateurService;

    @Autowired
    public Login(EtudiantService etudiantService,
                           EnseignantService enseignantService,
                           AdministrateurService administrateurService) {
        this.etudiantService = etudiantService;
        this.enseignantService = enseignantService;
        this.administrateurService = administrateurService;
    }

    @PostMapping("/login")
    public String login(@RequestParam("user_type") String userType,
                        @RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session, Model model) {

        if (userType == null || userType.isEmpty()) {
            model.addAttribute("errorMessage", "Veuillez sélectionner le type de votre compte.");
            return "Connexion"; // Show the login page with an error message
        }

        // Attempt authentication based on user type
        switch (userType.toLowerCase()) {
            case "etudiant":
                Etudiant etudiant = etudiantService.findByEmail(username);
                if (etudiant != null && etudiant.getMotDePasse().equals(password)) {
                    session.setAttribute("user", etudiant);
                    session.setAttribute("role", "Etudiant");
                    return "redirect:/accueil"; // Redirect to the home page after successful login
                }
                break;

            case "enseignant":
                Enseignant enseignant = enseignantService.findByEmail(username);
                if (enseignant != null && enseignant.getMotDePasse().equals(password)) {
                    session.setAttribute("user", enseignant);
                    session.setAttribute("role", "Enseignant");
                    return "redirect:/accueil";
                }
                break;

            case "administrateur":
                Administrateur administrateur = administrateurService.findByEmail(username);
                if (administrateur != null && administrateur.getMotDePasse().equals(password)) {
                    session.setAttribute("user", administrateur);
                    session.setAttribute("role", "Administrateur");
                    return "redirect:/accueil";
                }
                break;

            default:
                model.addAttribute("errorMessage", "Type d'utilisateur invalide.");
                return "Connexion";
        }

        // Authentication failed
        model.addAttribute("errorMessage", "Nom d'utilisateur ou mot de passe incorrect.");
        return "Connexion";
    }
}

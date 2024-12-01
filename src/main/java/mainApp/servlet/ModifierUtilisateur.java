package mainApp.servlet;

import mainApp.model.Administrateur;
import mainApp.model.Enseignant;
import mainApp.model.Etudiant;
import mainApp.service.AdministrateurService;
import mainApp.service.EnseignantService;
import mainApp.service.EtudiantService;
import mainApp.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/ModifierUtilisateur")
@SessionAttributes("username")
public class ModifierUtilisateur {

    private final UtilisateurService userService;
    private final EtudiantService etudiantService;
    private final EnseignantService enseignantService;
    private final AdministrateurService administrateurService;
    

    @Autowired
    public ModifierUtilisateur(UtilisateurService userService, EtudiantService etudiantService, EnseignantService enseignantService, AdministrateurService administrateurService) {
        this.userService = userService;
		this.etudiantService = etudiantService;
		this.enseignantService = enseignantService;
		this.administrateurService = administrateurService;
    }

    /**
     * Affiche les informations de l'utilisateur pour modification.
     */
    @GetMapping
    public String showUserInfo(HttpServletRequest request, HttpServletResponse response,Model model) {
    	HttpSession session = request.getSession();
    	String userType = (String) session.getAttribute("role");
    	if(userType==null) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/Login");  // Forward to accueil page
            try {
				dispatcher.forward(request, response);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
    	}
        Object user = userService.getUserById(userType, session.getAttribute("username"));
        request.setAttribute("user", user);
        request.setAttribute("userType", userType);
        return "AfficherInfos"; // Vue pour afficher les infos utilisateur
    }

    /**
     * Met à jour les informations de l'utilisateur en fonction de son type.
     */
    @PostMapping
    public String updateUser(
            @RequestParam("userType") String userType,
            @RequestParam("id") Long userId,
            @RequestParam("nom") String nom,
            @RequestParam("prenom") String prenom,
            @RequestParam("email") String email,
            @RequestParam(value = "contact", required = false) String contact,
            @RequestParam(value = "dateNaissance", required = false) String dateNaissance,
            HttpSession session,
            Model model, HttpServletRequest request) {

        // Mettre à jour les informations de l'utilisateur
    	try {
            userService.updateUser(userType, userId, nom, prenom, email, contact, dateNaissance, session);
    	}catch(Exception e) {
    		model.addAttribute("errorCode","Mail déjà utilisé");
    		model.addAttribute("errorMessage", "Une autre personne de votre catégorie possède cette adresse mail");
    		return "error";
    	}

        String userTypeTemp = (String) session.getAttribute("role");
        Object updatedUser = null;  // Déclaration d'une variable commune pour l'utilisateur mis à jour

        // Récupérer l'utilisateur mis à jour en fonction du type
        if ("Etudiant".equals(userTypeTemp)) {
            updatedUser = etudiantService.findEtudiantById(userId);
        } else if ("Enseignant".equals(userTypeTemp)) {
            updatedUser = enseignantService.findEnseignantById(userId);
        } else if ("Administrateur".equals(userTypeTemp)) {
            updatedUser = administrateurService.findAdministrateurById(userId);
        }

        // Ajouter l'utilisateur mis à jour au modèle
        request.setAttribute("user", updatedUser);
        request.setAttribute("userType", userTypeTemp);
    	session.setAttribute("username", updatedUser);
    	session.setAttribute("role", userType);
   	
        
        // Retourner à la vue pour afficher les informations mises à jour
        return "AfficherInfos"; // Redirection vers la page d'affichage
    }
}

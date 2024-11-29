package mainApp.servlet;

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

    @Autowired
    public ModifierUtilisateur(UtilisateurService userService) {
        this.userService = userService;
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
        model.addAttribute("user", user);
        model.addAttribute("userType", userType);
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
            Model model) {

        // Mettre à jour les informations de l'utilisateur
        userService.updateUser(userType, userId, nom, prenom, email, contact, dateNaissance, session);

        // Récupérer les informations mises à jour de l'utilisateur
        Object updatedUser = userService.getUserById(userType, session.getAttribute("username"));

        // Ajouter l'utilisateur mis à jour au modèle
        model.addAttribute("user", updatedUser);
        model.addAttribute("userType", userType);
    	session.setAttribute("username", updatedUser);
    	session.setAttribute("userType", userType);
    	
        
        // Retourner à la vue pour afficher les informations mises à jour
        return "AfficherInfos"; // Redirection vers la page d'affichage
    }
}

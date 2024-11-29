package servlet;

import service.CoursService;
import model.Cours;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AfficherCours {

    private final CoursService coursService;

    @Autowired
    public AfficherCours(CoursService coursService) {
        this.coursService = coursService;
    }

    @GetMapping("/AfficherCours")
    public String afficherCours(@RequestParam(value = "page", required = false) String page, Model model) {
        // Récupérer tous les cours
        List<Cours> coursList = coursService.findAllCours();

        // Debugging : Affichage des ID de chaque cours dans la console
        for (Cours cours : coursList) {
            System.out.println("Cours ID: " + cours.getId() + ", Nom: " + cours.getNom());
        }

        // Passer la liste des cours à la JSP
        model.addAttribute("coursList", coursList);

        // Rediriger vers la page appropriée
        if ("gestion".equals(page)) {
            return "GestionCours"; // Page de gestion des cours
        } else {
            return "AfficherCours"; // Page d'affichage des cours
        }
    }
}

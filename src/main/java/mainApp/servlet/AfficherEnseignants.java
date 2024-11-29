package mainApp.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import mainApp.model.Enseignant;
import mainApp.service.EnseignantService;

import java.util.List;

@Controller
public class AfficherEnseignants {

    private final EnseignantService enseignantService;

    @Autowired
    public AfficherEnseignants(EnseignantService enseignantService) {
        this.enseignantService = enseignantService;
    }

    @GetMapping("/AfficherEnseignants")
    public String afficherEnseignants(Model model) {
        // Récupérer tous les enseignants
        List<Enseignant> enseignants = enseignantService.findAllEnseignants();

        // Debugging : Affichage des ID de chaque enseignant dans la console
        for (Enseignant enseignant : enseignants) {
            System.out.println("Enseignant ID: " + enseignant.getId() + ", Nom: " + enseignant.getNom());
        }

        // Passer la liste des enseignants au modèle
        model.addAttribute("enseignants", enseignants);

        // Retourner le nom de la page JSP pour l'affichage
        return "GestionEnseignants"; // Cette page JSP doit être dans le dossier WEB-INF/jsp/
    }
}

package servlet;

import model.Enseignant;
import service.EnseignantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class ModifierEnseignant {

    private final EnseignantService enseignantService;

    @Autowired
    public ModifierEnseignant(EnseignantService enseignantService) {
        this.enseignantService = enseignantService;
    }

    // Affichage de la page de modification de l'enseignant
    @GetMapping("/modifierEnseignant")
    public String showModifierForm(@RequestParam("id") Long id, Model model) {
        Enseignant enseignant = enseignantService.findEnseignantById(id); // Utilisation du service pour récupérer l'enseignant
        if (enseignant == null) {
            return "redirect:/gestionEnseignants"; // Redirection vers la gestion des enseignants si non trouvé
        }
        model.addAttribute("enseignant", enseignant);
        return "modifierEnseignant"; // Nom de la vue Thymeleaf
    }

    // Traitement de la modification de l'enseignant
    @PostMapping("/modifierEnseignant")
    public String updateEnseignant(@ModelAttribute Enseignant enseignant) {
        enseignantService.updateEnseignant(enseignant); // Utilisation du service pour sauvegarder les modifications
        return "redirect:/gestionEnseignants"; // Redirection après la modification
    }
}

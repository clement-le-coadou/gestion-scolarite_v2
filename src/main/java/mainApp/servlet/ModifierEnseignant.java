package mainApp.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import mainApp.model.Enseignant;
import mainApp.service.EnseignantService;

import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class ModifierEnseignant {

    private final EnseignantService enseignantService;

    @Autowired
    public ModifierEnseignant(EnseignantService enseignantService) {
        this.enseignantService = enseignantService;
    }

    // Affichage de la page de modification de l'enseignant
    @GetMapping("/ModifierEnseignant")
    public String showModifierForm(@RequestParam("id") Long id, Model model) {
        Enseignant enseignant = enseignantService.findEnseignantById(id); // Utilisation du service pour récupérer l'enseignant
        if (enseignant == null) {
            return "redirect:/AfficherEnseignants"; // Redirection vers la gestion des enseignants si non trouvé
        }
        model.addAttribute("enseignant", enseignant);
        return "ModifierEnseignant"; 
    }

    // Traitement de la modification de l'enseignant
    @PostMapping("/ModifierEnseignant")
    public String updateEnseignant(@ModelAttribute Enseignant enseignant) {
    	Enseignant existingEnseignant = enseignantService.findEnseignantById(enseignant.getId());
        if (existingEnseignant == null) {
            return "redirect:/AfficherEnseignants"; // Redirect if the enseignant does not exist
        }
        enseignant.setMotDePasse(existingEnseignant.getMotDePasse());
        
        enseignantService.updateEnseignant(enseignant);
        return "redirect:/AfficherEnseignants";
    }
}

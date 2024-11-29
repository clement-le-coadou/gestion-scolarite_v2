package mainApp.servlet;

import mainApp.dao.EnseignantRepository;
import mainApp.model.Enseignant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SupprimerEnseignant {

    // Injection du repository EnseignantRepository
    @Autowired
    private EnseignantRepository enseignantRepository;

    @PostMapping("/SupprimerEnseignant")
    public String supprimerEnseignant(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        // Récupérer l'enseignant par ID avec Spring Data JPA
        Enseignant enseignant = enseignantRepository.findById(id).orElse(null);

        if (enseignant != null) {
            // Supprimer l'enseignant
            enseignantRepository.delete(enseignant);
            // Ajouter un message de succès dans les attributs de redirection
            redirectAttributes.addFlashAttribute("message", "Enseignant supprimé avec succès.");
        } else {
            // Ajouter un message d'erreur si l'enseignant n'est pas trouvé
            redirectAttributes.addFlashAttribute("error", "Enseignant introuvable.");
        }

        // Rediriger vers la page de gestion des enseignants
        return "redirect:/AfficherEnseignants";
    }
}

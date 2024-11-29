package mainApp.servlet;

import mainApp.dao.CoursRepository;
import mainApp.model.Cours;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SupprimerCours {

    // Injection du repository CoursRepository
    @Autowired
    private CoursRepository coursRepository;

    @PostMapping("/supprimerCours")
    public String supprimerCours(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        // Récupérer le cours par ID avec Spring Data JPA
        Cours cours = coursRepository.findById(id).orElse(null);

        if (cours != null) {
            // Supprimer le cours
            coursRepository.delete(cours);
            // Ajouter un message de succès dans les attributs de redirection
            redirectAttributes.addFlashAttribute("message", "Cours supprimé avec succès.");
        } else {
            // Ajouter un message d'erreur si le cours n'est pas trouvé
            redirectAttributes.addFlashAttribute("error", "Cours introuvable.");
        }

        // Rediriger vers la page de gestion des cours
        return "redirect:/afficherCours?page=gestion";
    }
}

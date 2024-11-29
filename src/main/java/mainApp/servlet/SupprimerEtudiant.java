package mainApp.servlet;

import mainApp.dao.EtudiantRepository;
import mainApp.model.Etudiant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SupprimerEtudiant {

    // Injection du repository EtudiantRepository
    @Autowired
    private EtudiantRepository etudiantRepository;

    @PostMapping("/SupprimerEtudiant")
    public String supprimerEtudiant(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        // Récupérer l'étudiant par ID avec Spring Data JPA
        Etudiant etudiant = etudiantRepository.findById(id).orElse(null);

        if (etudiant != null) {
            // Supprimer l'étudiant
            etudiantRepository.delete(etudiant);
            // Ajouter un message de succès dans les attributs de redirection
            redirectAttributes.addFlashAttribute("message", "Étudiant supprimé avec succès.");
        } else {
            // Ajouter un message d'erreur si l'étudiant n'est pas trouvé
            redirectAttributes.addFlashAttribute("error", "Étudiant introuvable.");
        }

        // Rediriger vers la page d'affichage des étudiants
        return "redirect:/AfficherEtudiants";
    }
}

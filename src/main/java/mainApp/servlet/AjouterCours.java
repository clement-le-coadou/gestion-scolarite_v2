package mainApp.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mainApp.model.Cours;
import mainApp.model.Enseignant;
import mainApp.service.CoursService;
import mainApp.service.EnseignantService;

@Controller
public class AjouterCours {

    private final CoursService coursService;
    private final EnseignantService enseignantService;

    @Autowired
    public AjouterCours(CoursService coursService, EnseignantService enseignantService) {
        this.coursService = coursService;
        this.enseignantService = enseignantService;
    }

    // Méthode pour afficher le formulaire d'ajout
    @GetMapping("/AjouterCours")
    public String afficherFormulaire() {
        // Retourner la vue du formulaire d'ajout de cours
        return "AjouterCours"; // Cette page JSP doit être dans le dossier WEB-INF/jsp/
    }

    // Méthode pour traiter la soumission du formulaire
    @PostMapping("/AjouterCours")
    public String ajouterCours(@RequestParam("nom") String nom, 
                               @RequestParam("description") String description, 
                               @RequestParam("enseignantId") Long enseignantId, 
                               RedirectAttributes redirectAttributes) {
        // Récupération de l'enseignant par son ID
        Enseignant enseignant = enseignantService.findEnseignantById(enseignantId);

        if (enseignant != null) {
            // Création du cours
            Cours cours = new Cours(nom, description, enseignant);
            coursService.createCours(cours);
            
            // Ajouter un message de succès dans les attributs de redirection
            redirectAttributes.addFlashAttribute("message", "Cours ajouté avec succès!");
        } else {
            // Ajouter un message d'erreur en cas de problème avec l'enseignant
            redirectAttributes.addFlashAttribute("message", "Enseignant non trouvé!");
        }

        // Redirection vers la liste des cours ou la page de gestion des cours
        return "redirect:/AfficherCours?page=gestion";
    }
}

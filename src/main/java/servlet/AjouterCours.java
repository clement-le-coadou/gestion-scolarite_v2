package servlet;

import daogenerique.CrudGeneric;
import jpa.Cours;
import jpa.Enseignant;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AjouterCours {

    private final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    private final CrudGeneric<Cours> coursDAO = new CrudGeneric<>(sessionFactory, Cours.class);

    // Méthode pour afficher la page de formulaire d'ajout (si nécessaire)
    @GetMapping("/AjouterCours")
    public String afficherFormulaire() {
        // Retourner la vue du formulaire d'ajout de cours (nom du fichier JSP ou autre template)
        return "AjouterCours"; // Cette page JSP doit être dans le dossier WEB-INF/jsp/
    }

    // Méthode pour traiter la soumission du formulaire
    @PostMapping("/AjouterCours")
    public String ajouterCours(@RequestParam("nom") String nom, 
                               @RequestParam("description") String description, 
                               @RequestParam("enseignantId") Long enseignantId, 
                               RedirectAttributes redirectAttributes) {
        // Récupération de l'enseignant par son ID
        CrudGeneric<Enseignant> enseignantDAO = new CrudGeneric<>(sessionFactory, Enseignant.class);
        Enseignant enseignant = enseignantDAO.read(enseignantId);

        if (enseignant != null) {
            // Création du cours
            Cours cours = new Cours(nom, description, enseignant);
            coursDAO.create(cours);
            
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

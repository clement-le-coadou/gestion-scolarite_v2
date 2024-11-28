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

    // M�thode pour afficher la page de formulaire d'ajout (si n�cessaire)
    @GetMapping("/AjouterCours")
    public String afficherFormulaire() {
        // Retourner la vue du formulaire d'ajout de cours (nom du fichier JSP ou autre template)
        return "AjouterCours"; // Cette page JSP doit �tre dans le dossier WEB-INF/jsp/
    }

    // M�thode pour traiter la soumission du formulaire
    @PostMapping("/AjouterCours")
    public String ajouterCours(@RequestParam("nom") String nom, 
                               @RequestParam("description") String description, 
                               @RequestParam("enseignantId") Long enseignantId, 
                               RedirectAttributes redirectAttributes) {
        // R�cup�ration de l'enseignant par son ID
        CrudGeneric<Enseignant> enseignantDAO = new CrudGeneric<>(sessionFactory, Enseignant.class);
        Enseignant enseignant = enseignantDAO.read(enseignantId);

        if (enseignant != null) {
            // Cr�ation du cours
            Cours cours = new Cours(nom, description, enseignant);
            coursDAO.create(cours);
            
            // Ajouter un message de succ�s dans les attributs de redirection
            redirectAttributes.addFlashAttribute("message", "Cours ajout� avec succ�s!");
        } else {
            // Ajouter un message d'erreur en cas de probl�me avec l'enseignant
            redirectAttributes.addFlashAttribute("message", "Enseignant non trouv�!");
        }

        // Redirection vers la liste des cours ou la page de gestion des cours
        return "redirect:/AfficherCours?page=gestion";
    }
}

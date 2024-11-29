package mainApp.servlet;

import mainApp.model.Cours;
import mainApp.model.Etudiant;
import mainApp.model.Inscription;
import mainApp.service.CoursService;
import mainApp.service.EtudiantService;
import mainApp.service.InscriptionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class InscrireEtudiant {

    @Autowired
    private EtudiantService etudiantService;

    @Autowired
    private CoursService coursService;

    @Autowired
    private InscriptionService inscriptionService;

    // Méthode pour gérer l'inscription d'un étudiant à un cours
    @PostMapping("/InscrireEtudiant")
    public String inscrireEtudiant(@RequestParam("etudiantId") Long etudiantId, 
                                    @RequestParam("coursId") Long coursId, 
                                    Model model) {

        try {
            // Récupérer l'étudiant et le cours à partir des IDs
            Etudiant etudiant = etudiantService.findEtudiantById(etudiantId);
            Cours cours = coursService.findCoursById(coursId);

            if (etudiant == null || cours == null) {
                model.addAttribute("errorMessage", "L'étudiant ou le cours n'existe pas.");
                return "error"; // Redirige vers une page d'erreur si l'étudiant ou le cours n'est pas trouvé
            }

            // Vérifier si l'inscription existe déjà pour éviter les doublons
            if (inscriptionService.isAlreadyInscribed(etudiant, cours)) {
                model.addAttribute("errorMessage", "L'étudiant est déjà inscrit à ce cours.");
                return "error"; // Page d'erreur en cas d'inscription en double
            }

            // Créer une nouvelle inscription
            Inscription inscription = new Inscription();
            inscription.setEtudiant(etudiant);
            inscription.setCours(cours);

            // Enregistrer l'inscription
            inscriptionService.createInscription(inscription);

            // Ajouter un message de succès
            model.addAttribute("successMessage", "L'étudiant a été inscrit au cours avec succès.");

            // Rediriger vers la page d'affichage des cours
            return "redirect:/AfficherCours?page=gestion"; // Page où les cours sont affichés

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Une erreur est survenue lors de l'inscription.");
            return "error"; // Page d'erreur
        }
    }
}

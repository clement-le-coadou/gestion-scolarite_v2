package mainApp.servlet;

import dao.CrudGeneric;
import mainApp.model.Cours;
import mainApp.model.Etudiant;
import mainApp.model.Inscription;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class InscrireEtudiant {

    @Autowired
    private CrudGeneric<Etudiant> etudiantDAO;

    @Autowired
    private CrudGeneric<Cours> coursDAO;

    @Autowired
    private CrudGeneric<Inscription> inscriptionDAO;

    // M�thode pour g�rer l'inscription d'un �tudiant � un cours
    @PostMapping("/InscrireEtudiant")
    public String inscrireEtudiant(@RequestParam("etudiantId") Long etudiantId, 
                                    @RequestParam("coursId") Long coursId, 
                                    Model model) {

        try {
            // R�cup�rer l'�tudiant et le cours � partir des IDs
            Etudiant etudiant = etudiantDAO.read(etudiantId);
            Cours cours = coursDAO.read(coursId);

            if (etudiant == null || cours == null) {
                model.addAttribute("errorMessage", "L'�tudiant ou le cours n'existe pas.");
                return "error"; // Redirige vers une page d'erreur si l'�tudiant ou le cours n'est pas trouv�
            }

            // Cr�er une nouvelle inscription
            Inscription inscription = new Inscription();
            inscription.setEtudiant(etudiant);
            inscription.setCours(cours);

            // Enregistrer l'inscription
            inscriptionDAO.create(inscription);

            // Ajouter un message de succ�s
            model.addAttribute("successMessage", "L'�tudiant a �t� inscrit au cours avec succ�s.");

            // Rediriger vers la page d'affichage des cours
            return "redirect:/AfficherCours?page=gestion"; // Page o� les cours sont affich�s

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Une erreur est survenue lors de l'inscription.");
            return "error"; // Page d'erreur
        }
    }
}

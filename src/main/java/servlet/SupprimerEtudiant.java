package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import daogenerique.CrudGeneric;
import jpa.Etudiant;

@Controller
public class SupprimerEtudiant {

    @Autowired
    private CrudGeneric<Etudiant> etudiantDAO;

    @PostMapping("/supprimerEtudiant")
    public String supprimerEtudiant(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        // Récupérer l'étudiant par ID
        Etudiant etudiant = etudiantDAO.read(id);
        if (etudiant != null) {
            etudiantDAO.delete(etudiant);
            redirectAttributes.addFlashAttribute("message", "Étudiant supprimé avec succès.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Étudiant introuvable.");
        }
        
        // Rediriger vers la page d'affichage des étudiants
        return "redirect:/afficherEtudiants";
    }
}

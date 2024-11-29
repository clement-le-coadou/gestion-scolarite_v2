package mainApp.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import daogenerique.CrudGeneric;
import mainApp.model.Etudiant;

@Controller
public class SupprimerEtudiant {

    @Autowired
    private CrudGeneric<Etudiant> etudiantDAO;

    @PostMapping("/supprimerEtudiant")
    public String supprimerEtudiant(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        // R�cup�rer l'�tudiant par ID
        Etudiant etudiant = etudiantDAO.read(id);
        if (etudiant != null) {
            etudiantDAO.delete(etudiant);
            redirectAttributes.addFlashAttribute("message", "�tudiant supprim� avec succ�s.");
        } else {
            redirectAttributes.addFlashAttribute("error", "�tudiant introuvable.");
        }
        
        // Rediriger vers la page d'affichage des �tudiants
        return "redirect:/afficherEtudiants";
    }
}

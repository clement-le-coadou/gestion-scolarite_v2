package controller;

import dao.CrudGeneric;
import jpa.Cours;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class Cours {

    @Autowired
    private CrudGeneric<Cours> coursDAO;

    @PostMapping("/supprimerCours")
    public String supprimerCours(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        // Récupérer le cours par ID
        Cours cours = coursDAO.read(id);
        if (cours != null) {
            coursDAO.delete(cours);
            redirectAttributes.addFlashAttribute("message", "Cours supprimé avec succès.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Cours introuvable.");
        }
        
        // Redirection vers la page de gestion des cours
        return "redirect:/afficherCours?page=gestion";
    }
}

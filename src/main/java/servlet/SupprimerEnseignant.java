package controller;

import dao.CrudGeneric;
import model.Enseignant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class Enseignant {

    @Autowired
    private CrudGeneric<Enseignant> enseignantDAO;

    @PostMapping("/supprimerEnseignant")
    public String supprimerEnseignant(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        // Récupérer l'enseignant par ID
        Enseignant enseignant = enseignantDAO.read(id);
        if (enseignant != null) {
            enseignantDAO.delete(enseignant);
            redirectAttributes.addFlashAttribute("message", "Enseignant supprimé avec succès.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Enseignant introuvable.");
        }
        
        // Redirection vers la liste des enseignants
        return "redirect:/afficherEnseignants";
    }
}

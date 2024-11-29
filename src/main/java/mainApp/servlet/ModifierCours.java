package mainApp.servlet;

import mainApp.model.Cours;
import mainApp.model.Enseignant;
import mainApp.service.CoursService;
import mainApp.service.EnseignantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ModifierCours {

    private final CoursService coursService;
    private final EnseignantService enseignantService;

    @Autowired
    public ModifierCours(CoursService coursService, EnseignantService enseignantService) {
        this.coursService = coursService;
        this.enseignantService = enseignantService;
    }

    @GetMapping("/ModifierCours")
    public String getModifierCours(@RequestParam("id") Long coursId, Model model) {
        Cours cours = coursService.findCoursById(coursId);
        model.addAttribute("cours", cours);
        return "ModifierCours";  // JSP view to edit the course
    }

    @PostMapping("/ModifierCours")
    public String postModifierCours(@RequestParam("id") Long coursId,
                                    @RequestParam("nom") String nom,
                                    @RequestParam("description") String description,
                                    @RequestParam("enseignantId") Long enseignantId,
                                    Model model) {

        // Fetch course and update fields
        Cours cours = coursService.findCoursById(coursId);
        if (cours != null) {
            cours.setNom(nom);
            cours.setDescription(description);

            // Fetch enseignant and assign to course
            Enseignant enseignant = enseignantService.findEnseignantById(enseignantId);
            if (enseignant != null) {
                cours.setEnseignant(enseignant);
            }

            // Save the updated course
            coursService.updateCours(cours);
        }

        // Redirect to another page after successful modification
        return "redirect:/AfficherCours?page=gestion";
    }
}

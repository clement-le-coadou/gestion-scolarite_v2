package mainApp.servlet;

import mainApp.model.Cours;
import mainApp.model.Enseignant;
import mainApp.model.Etudiant;
import mainApp.model.Inscription;
import mainApp.service.CoursService;
import mainApp.service.InscriptionService;
import mainApp.service.EnseignantService;
import mainApp.service.EtudiantService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MesCours {

    private final CoursService coursService;
    private final InscriptionService inscriptionService;

    @Autowired
    public MesCours(CoursService coursService, InscriptionService inscriptionService,
                    EtudiantService etudiantService, EnseignantService enseignantService) {
        this.coursService = coursService;
        this.inscriptionService = inscriptionService;
    }

    @GetMapping("/mesCours")
    public String getMesCours(@SessionAttribute("username") Object utilisateur, Model model) {
        List<Cours> coursList = null;

        if (utilisateur instanceof Etudiant) {
            Etudiant etudiant = (Etudiant) utilisateur;

            // Get inscriptions for this Etudiant
            List<Inscription> inscriptions = inscriptionService.findInscriptionsByEtudiantId(etudiant.getId());

            // Get courses from inscriptions
            coursList = inscriptions.stream()
                    .map(Inscription::getCours)
                    .distinct()
                    .collect(Collectors.toList());

        } else if (utilisateur instanceof Enseignant) {
            Enseignant enseignant = (Enseignant) utilisateur;

            // Get courses for this Enseignant
            coursList = coursService.findByEnseignantId(enseignant.getId());
        }

        model.addAttribute("coursList", coursList);
        return "AfficherCours";
    }
}

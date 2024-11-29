package mainApp.servlet;

import mainApp.model.Cours;
import mainApp.model.Etudiant;
import mainApp.model.Inscription;
import mainApp.model.Note;
import mainApp.service.CoursService;
import mainApp.service.InscriptionService;
import mainApp.service.NoteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class GestionNotes {

    @Autowired
    private InscriptionService inscriptionService;

    @Autowired
    private NoteService noteService;

    @Autowired
    private CoursService coursService;

    @GetMapping("/GestionNotes")
    public String gestionNotes(@RequestParam(value = "coursId", required = false) Long coursId, Model model) {
        if (coursId != null) {
            try {
                // Vérifier si le cours existe
                Cours cours = coursService.findCoursById(coursId);
                if (cours == null) {
                    model.addAttribute("errorMessage", "Le cours avec cet ID n'existe pas.");
                    return "error"; // Redirige vers une page d'erreur si le cours est introuvable
                }

                // Récupérer toutes les inscriptions pour ce cours
                List<Inscription> inscriptions = inscriptionService.findAllInscriptions()
                        .stream()
                        .filter(inscription -> inscription.getCours().getId().equals(coursId))
                        .collect(Collectors.toList());

                // Extraire les étudiants inscrits
                List<Etudiant> etudiantList = inscriptions.stream()
                        .map(Inscription::getEtudiant)
                        .collect(Collectors.toList());

                // Récupérer les notes des étudiants pour ce cours
                List<Note> notes = noteService.findAllNotes()
                        .stream()
                        .filter(note -> note.getCours().getId().equals(coursId))
                        .collect(Collectors.toList());

                model.addAttribute("notes", notes);
                model.addAttribute("etudiantList", etudiantList);
                model.addAttribute("coursId", coursId);

                return "AfficherNoteEnseignant"; // Affiche la vue des notes pour l'enseignant

            } catch (Exception e) {
                e.printStackTrace();
                model.addAttribute("errorMessage", "Une erreur est survenue lors du traitement.");
                return "error";
            }
        } else {
            model.addAttribute("errorMessage", "Aucun cours sélectionné.");
            return "error"; // Retourne une page d'erreur si aucun cours n'est sélectionné
        }
    }
}

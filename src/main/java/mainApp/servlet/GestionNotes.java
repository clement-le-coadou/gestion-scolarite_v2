package mainApp.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import mainApp.model.Cours;
import mainApp.model.Etudiant;
import mainApp.model.Inscription;
import mainApp.model.Note;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class GestionNotes {

    @Autowired
    private CrudGeneric<Inscription> inscriptionDAO;

    @Autowired
    private CrudGeneric<Note> noteDAO;

    @Autowired
    private CrudGeneric<Cours> coursDAO;

    @GetMapping("/GestionNotes")
    public String gestionNotes(@RequestParam(value = "coursId", required = false) Integer coursId, Model model) {
        if (coursId != null) {
            try {
                // R�cup�rer toutes les inscriptions et filtrer celles pour le cours donn�
                List<Inscription> inscriptions = inscriptionDAO.findAll();
                List<Etudiant> etudiantList = inscriptions.stream()
                        .filter(inscription -> inscription.getCours().getId() == coursId)
                        .map(Inscription::getEtudiant)
                        .collect(Collectors.toList());

                // R�cup�rer les notes des �tudiants pour ce cours
                List<Note> notes = noteDAO.findAll().stream()
                        .filter(note -> note.getCours().getId() == coursId)
                        .collect(Collectors.toList());

                model.addAttribute("notes", notes);
                model.addAttribute("etudiantList", etudiantList);
                model.addAttribute("coursId", coursId);

                return "AfficherNoteEnseignant"; // Redirige vers la vue AfficherNoteEnseignant
            } catch (Exception e) {
                e.printStackTrace();
                model.addAttribute("errorMessage", "ID de cours invalide.");
                return "error"; // Page d'erreur si le cours est invalide
            }
        } else {
            model.addAttribute("errorMessage", "Aucun cours s�lectionn�.");
            return "error"; // Page d'erreur si aucun cours n'est s�lectionn�
        }
    }
}

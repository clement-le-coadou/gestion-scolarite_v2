package mainApp.servlet;

import mainApp.dao.NoteRepository;
import mainApp.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SupprimerNote {

    // Injection du repository NoteRepository
    @Autowired
    private NoteRepository noteRepository;

    @PostMapping("/SupprimerNote")
    public String supprimerNote(@RequestParam("idNote") Long idNote, RedirectAttributes redirectAttributes) {
        // Récupérer la note par ID avec Spring Data JPA
        Note note = noteRepository.findById(idNote).orElse(null);

        if (note != null) {
            // Supprimer la note
            noteRepository.delete(note);
            redirectAttributes.addFlashAttribute("message", "Note supprimée avec succès.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Note introuvable.");
        }

        // Rediriger vers la page de gestion des notes pour le cours spécifique
        return "redirect:/GestionNotes?coursId=" + note.getCours().getId();
    }
}

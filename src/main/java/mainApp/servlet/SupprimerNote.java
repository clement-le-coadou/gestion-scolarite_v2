package mainApp.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import daogenerique.CrudGeneric;
import mainApp.model.Note;

@Controller
public class NoteController {

    @Autowired
    private CrudGeneric<Note> noteDAO;

    @PostMapping("/supprimerNote")
    public String supprimerNote(@RequestParam("idNote") int idNote, RedirectAttributes redirectAttributes) {
        // R�cup�rer la note par ID
        Note note = noteDAO.findById(idNote);
        if (note != null) {
            noteDAO.delete(note);
            redirectAttributes.addFlashAttribute("message", "Note supprim�e avec succ�s.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Note introuvable.");
        }

        // Rediriger vers la page de gestion des notes pour un cours sp�cifique
        return "redirect:/gestionNotes?coursId=" + note.getCours().getId();
    }
}

package mainApp.servlet;

import mainApp.model.Note;
import mainApp.service.NoteService;
import mainApp.email.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ModifierNote")
public class ModifierNote {

    private final NoteService noteService;

    @Autowired
    public ModifierNote(NoteService noteService) {
        this.noteService = noteService;
    }

    /**
     * Affiche le formulaire de modification de la note.
     */
    @GetMapping
    public String showEditForm(@RequestParam("idNote") Long idNote, Model model) {
        Note note = noteService.findNoteById(idNote);

        if (note != null) {
            model.addAttribute("note", note);
            return "ModifierNote"; // Vue JSP ou Thymeleaf pour le formulaire
        } else {
            return "redirect:/error?message=Note introuvable"; // Redirection en cas d'erreur
        }
    }

    /**
     * Met à jour la note et envoie un email après modification.
     */
    @PostMapping
    public String updateNote(
            @RequestParam("idNote") Long idNote,
            @RequestParam("note") double newNoteValue) {

        Note note = noteService.findNoteById(idNote);
        if (note != null) {
            // Mise à jour de la note
            note.setNote(newNoteValue);
            noteService.updateNote(note);

            // Envoi d'email après modification
            String destinataire = note.getEtudiant().getEmail(); // Assurez-vous que l'objet Etudiant contient l'email
            String sujet = "Modification de votre note";
            String contenu = "Bonjour " + note.getEtudiant().getPrenom() + " " + note.getEtudiant().getNom() +
                    ",\n\nVotre note pour le cours " + note.getCours().getNom() + " a été mise à jour.\n" +
                    "Nouvelle note : " + newNoteValue + "\n\nCordialement,\nL'équipe de gestion des études.";

            EmailUtil emailUtil = new EmailUtil();
			emailUtil.envoyerEmail(destinataire, sujet, contenu);
        }

        // Redirection vers la page de gestion des notes pour ce cours
        return "redirect:/GestionNotes?coursId=" + (note != null ? note.getCours().getId() : "");
    }
}

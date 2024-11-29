package controller;

import model.Note;
import daogenerique.CrudGeneric;
import email.EmailUtil;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/modifierNote")
public class ModifierNote {

    private final CrudGeneric<Note> noteDAO;

    @Autowired
    public ModifierNote() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        this.noteDAO = new CrudGeneric<>(sessionFactory, Note.class);
    }

    /**
     * Affiche le formulaire de modification de la note.
     */
    @GetMapping
    public String showEditForm(@RequestParam("idNote") int idNote, Model model) {
        Note note = noteDAO.findById(idNote);

        if (note != null) {
            model.addAttribute("note", note);
            return "ModifierNote"; // Vue JSP ou Thymeleaf pour le formulaire
        } else {
            // En cas d'erreur, renvoyer une vue ou un message d'erreur approprié
            return "redirect:/error?message=Note introuvable";
        }
    }

    /**
     * Met à jour la note et envoie un email après modification.
     */
    @PostMapping
    public String updateNote(
            @RequestParam("idNote") int idNote,
            @RequestParam("note") double newNoteValue) {

        Note note = noteDAO.findById(idNote);
        if (note != null) {
            // Mise à jour de la note
            note.setNote(newNoteValue);
            noteDAO.update(note);

            // Envoi d'email après modification
            String destinataire = note.getEtudiant().getEmail(); // Assurez-vous que l'objet Etudiant contient l'email
            String sujet = "Modification de votre note";
            String contenu = "Bonjour " + note.getEtudiant().getPrenom() + " " + note.getEtudiant().getNom() +
                    ",\n\nVotre note pour le cours " + note.getCours().getNom() + " a été mise à jour.\n" +
                    "Nouvelle note : " + newNoteValue + "\n\nCordialement,\nL'équipe de gestion des études.";

            EmailUtil.envoyerEmail(destinataire, sujet, contenu);
        }

        // Redirection vers la page de gestion des notes pour ce cours
        return "redirect:/gestionNotes?coursId=" + (note != null ? note.getCours().getId() : "");
    }
}

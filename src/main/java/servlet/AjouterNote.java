package servlet;

import service.NoteService;
import service.CoursService;
import service.EtudiantService;
import email.EmailUtil;  // Assurez-vous que EmailUtil est importé correctement
import model.Note;
import model.Cours;
import model.Etudiant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;  // Pour travailler avec Thymeleaf ou JSP
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AjouterNote {

    private final NoteService noteService;
    private final CoursService coursService;
    private final EtudiantService etudiantService;
    private final EmailUtil emailUtil;  // Injection de EmailUtil

    @Autowired
    public AjouterNote(NoteService noteService, CoursService coursService, EtudiantService etudiantService, EmailUtil emailUtil) {
        this.noteService = noteService;
        this.coursService = coursService;
        this.etudiantService = etudiantService;
        this.emailUtil = emailUtil;  // Injection d'EmailUtil
    }

    // Affichage du formulaire d'ajout de note
    @GetMapping("/AjouterNote")
    public String afficherFormulaire(@RequestParam("idEtudiant") Long idEtudiant,
                                     @RequestParam("coursId") Long coursId, Model model) {
        // Récupérer l'étudiant et le cours à partir de leurs IDs
        Etudiant etudiant = etudiantService.findEtudiantById(idEtudiant);
        Cours cours = coursService.findCoursById(coursId);

        if (etudiant != null && cours != null) {
            model.addAttribute("etudiant", etudiant);
            model.addAttribute("cours", cours);
            model.addAttribute("coursId", coursId);
            return "AjouterNote"; // Page JSP ou template Thymeleaf
        } else {
            model.addAttribute("error", "Étudiant ou cours introuvable.");
            return "error"; // Page d'erreur si étudiant ou cours est introuvable
        }
    }

    // Traitement de l'ajout de la note
    @PostMapping("/AjouterNote")
    public String ajouterNote(@RequestParam("idEtudiant") Long idEtudiant,
                              @RequestParam("coursId") Long coursId,
                              @RequestParam("note") double noteValue,
                              RedirectAttributes redirectAttributes) {
        Etudiant etudiant = etudiantService.findEtudiantById(idEtudiant);
        Cours cours = coursService.findCoursById(coursId);

        if (etudiant != null && cours != null) {
            // Création de la nouvelle note
            Note newNote = new Note();
            newNote.setEtudiant(etudiant);
            newNote.setCours(cours);
            newNote.setNote(noteValue);

            // Sauvegarde de la note
            noteService.createNote(newNote);

            // Envoi d'un email après l'ajout de la note
            String destinataire = etudiant.getEmail();
            String sujet = "Nouvelle note ajoutée";
            String contenu = "Bonjour " + etudiant.getPrenom() + " " + etudiant.getNom() +
                             ",\n\nUne nouvelle note a été ajoutée pour le cours " + cours.getNom() + ".\n" +
                             "Note : " + noteValue + "\n\nCordialement,\nL'équipe de gestion des études.";

            // Appel de la méthode sur l'instance de EmailUtil
            emailUtil.envoyerEmail(destinataire, sujet, contenu);

            // Message de succès après l'ajout
            redirectAttributes.addFlashAttribute("message", "La note a été ajoutée avec succès.");
            return "redirect:/GestionNotes?coursId=" + coursId;
        } else {
            redirectAttributes.addFlashAttribute("error", "Étudiant ou cours introuvable.");
            return "redirect:/error";
        }
    }
}

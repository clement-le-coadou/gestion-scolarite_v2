package servlet;

import daogenerique.CrudGeneric;
import email.EmailUtil;
import jpa.Cours;
import jpa.Etudiant;
import jpa.Note;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AjouterNote {

    private final CrudGeneric<Note> noteDAO;
    private final CrudGeneric<Cours> coursDAO;
    private final CrudGeneric<Etudiant> etudiantDAO;

    @Autowired
    public AjouterNote() {
        // Initialisation des DAO
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        noteDAO = new CrudGeneric<>(sessionFactory, Note.class);
        coursDAO = new CrudGeneric<>(sessionFactory, Cours.class);
        etudiantDAO = new CrudGeneric<>(sessionFactory, Etudiant.class);
    }

    // Affichage du formulaire d'ajout de note
    @GetMapping("/AjouterNote")
    public String afficherFormulaire(@RequestParam("idEtudiant") int idEtudiant,
                                     @RequestParam("coursId") int coursId, Model model) {
        // Récupérer l'étudiant et le cours à partir de leurs IDs
        Etudiant etudiant = etudiantDAO.findById(idEtudiant);
        Cours cours = coursDAO.findById(coursId);

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
    public String ajouterNote(@RequestParam("idEtudiant") int idEtudiant,
                              @RequestParam("coursId") int coursId,
                              @RequestParam("note") double noteValue,
                              RedirectAttributes redirectAttributes) {
        Etudiant etudiant = etudiantDAO.findById(idEtudiant);
        Cours cours = coursDAO.findById(coursId);

        if (etudiant != null && cours != null) {
            // Création de la nouvelle note
            Note newNote = new Note();
            newNote.setEtudiant(etudiant);
            newNote.setCours(cours);
            newNote.setNote(noteValue);

            // Sauvegarde de la note
            noteDAO.create(newNote);

            // Envoi d'un email après l'ajout de la note
            String destinataire = etudiant.getEmail();
            String sujet = "Nouvelle note ajoutée";
            String contenu = "Bonjour " + etudiant.getPrenom() + " " + etudiant.getNom() +
                             ",\n\nUne nouvelle note a été ajoutée pour le cours " + cours.getNom() + ".\n" +
                             "Note : " + noteValue + "\n\nCordialement,\nL'équipe de gestion des études.";

            EmailUtil.envoyerEmail(destinataire, sujet, contenu);

            // Message de succès après l'ajout
            redirectAttributes.addFlashAttribute("message", "La note a été ajoutée avec succès.");
            return "redirect:/GestionNotes?coursId=" + coursId;
        } else {
            redirectAttributes.addFlashAttribute("error", "Étudiant ou cours introuvable.");
            return "redirect:/error";
        }
    }
}

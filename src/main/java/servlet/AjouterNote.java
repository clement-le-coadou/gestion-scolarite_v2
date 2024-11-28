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
        // R�cup�rer l'�tudiant et le cours � partir de leurs IDs
        Etudiant etudiant = etudiantDAO.findById(idEtudiant);
        Cours cours = coursDAO.findById(coursId);

        if (etudiant != null && cours != null) {
            model.addAttribute("etudiant", etudiant);
            model.addAttribute("cours", cours);
            model.addAttribute("coursId", coursId);
            return "AjouterNote"; // Page JSP ou template Thymeleaf
        } else {
            model.addAttribute("error", "�tudiant ou cours introuvable.");
            return "error"; // Page d'erreur si �tudiant ou cours est introuvable
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
            // Cr�ation de la nouvelle note
            Note newNote = new Note();
            newNote.setEtudiant(etudiant);
            newNote.setCours(cours);
            newNote.setNote(noteValue);

            // Sauvegarde de la note
            noteDAO.create(newNote);

            // Envoi d'un email apr�s l'ajout de la note
            String destinataire = etudiant.getEmail();
            String sujet = "Nouvelle note ajout�e";
            String contenu = "Bonjour " + etudiant.getPrenom() + " " + etudiant.getNom() +
                             ",\n\nUne nouvelle note a �t� ajout�e pour le cours " + cours.getNom() + ".\n" +
                             "Note : " + noteValue + "\n\nCordialement,\nL'�quipe de gestion des �tudes.";

            EmailUtil.envoyerEmail(destinataire, sujet, contenu);

            // Message de succ�s apr�s l'ajout
            redirectAttributes.addFlashAttribute("message", "La note a �t� ajout�e avec succ�s.");
            return "redirect:/GestionNotes?coursId=" + coursId;
        } else {
            redirectAttributes.addFlashAttribute("error", "�tudiant ou cours introuvable.");
            return "redirect:/error";
        }
    }
}

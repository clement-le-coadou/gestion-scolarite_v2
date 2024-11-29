package mainApp.servlet;

import dao.CrudGeneric;
import mainApp.model.Cours;
import mainApp.model.Enseignant;
import mainApp.model.Etudiant;
import mainApp.model.Inscription;
import mainApp.model.Note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class Notes {

    @Autowired
    private CrudGeneric<Cours> coursDAO;

    @Autowired
    private CrudGeneric<Note> noteDAO;

    @Autowired
    private CrudGeneric<Inscription> inscriptionDAO;

    @GetMapping("/notes")
    public String redirectToNotes(HttpSession session, Model model) {
        String role = (String) session.getAttribute("role");
        if (role == null) {
            return "redirect:/Connexion";
        }

        Object utilisateur = session.getAttribute("username");

        if ("Enseignant".equals(role)) {
            Enseignant enseignant = (Enseignant) utilisateur;

            List<Cours> coursList = coursDAO.findAll().stream()
                    .filter(cours -> cours.getEnseignant() != null && cours.getEnseignant().getId().equals(enseignant.getId()))
                    .collect(Collectors.toList());

            model.addAttribute("coursList", coursList);
            return "GestionNotes"; // Vue pour la gestion des notes par les enseignants
        } else if ("Etudiant".equals(role)) {
            Etudiant etudiant = (Etudiant) utilisateur;

            List<Inscription> inscriptions = inscriptionDAO.findAll().stream()
                    .filter(inscription -> inscription.getEtudiant().getId().equals(etudiant.getId()))
                    .collect(Collectors.toList());

            List<Cours> coursList = inscriptions.stream()
                    .map(Inscription::getCours)
                    .distinct()
                    .collect(Collectors.toList());

            List<Note> notesList = noteDAO.findAll();

            model.addAttribute("coursList", coursList);
            model.addAttribute("notesList", notesList);
            return "AfficherNotes"; // Vue pour afficher les notes de l'�tudiant
        } else {
            return "redirect:/Connexion";
        }
    }
}

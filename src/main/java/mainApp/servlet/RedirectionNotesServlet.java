package mainApp.servlet;

import mainApp.model.Cours;
import mainApp.model.Etudiant;
import mainApp.model.Inscription;
import mainApp.model.Enseignant;
import mainApp.model.Note;
import mainApp.dao.CoursRepository;
import mainApp.dao.NoteRepository;
import mainApp.dao.InscriptionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class RedirectionNotesServlet {

    @Autowired
    private CoursRepository coursRepository;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private InscriptionRepository inscriptionRepository;

    // Handle GET request for the /notes page
    @GetMapping("/RedirectionNotesServlet")
    public String redirectToNotes(HttpSession session, Model model) {
        // Get the role of the logged-in user from the session
        String role = (String) session.getAttribute("role");
        System.out.println(role);
        // If role is not set, redirect to login page
        if (role == null) {
        	return "redirect:/Login";  // Cela effectue une redirection vers l'URL /login

        }

        // Get the logged-in user object (can be either Enseignant or Etudiant)
        Object utilisateur = session.getAttribute("username");

        // If the role is Enseignant, handle accordingly
        if ("Enseignant".equals(role)) {
            Enseignant enseignant = (Enseignant) utilisateur;

            // Fetch courses assigned to the logged-in teacher
            List<Cours> coursList = coursRepository.findAll().stream()
                    .filter(cours -> cours.getEnseignant() != null && cours.getEnseignant().getId().equals(enseignant.getId()))
                    .collect(Collectors.toList());

            // Add courses to the model and forward to GestionNotes view
            model.addAttribute("coursList", coursList);
            return "GestionNotes"; // View to manage notes for the teacher

        } else if ("Etudiant".equals(role)) {
            Etudiant etudiant = (Etudiant) utilisateur;

            // Fetch all inscriptions for the logged-in student
            List<Inscription> inscriptions = inscriptionRepository.findAll().stream()
                    .filter(inscription -> inscription.getEtudiant().getId().equals(etudiant.getId()))
                    .collect(Collectors.toList());

            // Get the list of courses the student is enrolled in
            List<Cours> coursList = inscriptions.stream()
                    .map(Inscription::getCours)
                    .distinct()
                    .collect(Collectors.toList());

            // Fetch all notes and filter for relevant ones (based on the student's courses)
            List<Note> notesList = noteRepository.findAll().stream()
                    .filter(note -> coursList.stream().anyMatch(cours -> cours.getId().equals(note.getCours().getId())))
                    .collect(Collectors.toList());

            // Add courses and notes to the model and forward to AfficherNotes view
            model.addAttribute("coursList", coursList);
            model.addAttribute("notesList", notesList);
            return "AfficherNotes"; // View to display the student's notes

        } else {
            // If the role is not recognized, redirect to login page
        	return "redirect:/Login";  // Cela effectue une redirection vers l'URL /login

        }
    }
}

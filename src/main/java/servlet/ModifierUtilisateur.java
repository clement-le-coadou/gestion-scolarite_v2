package controller;

import jpa.Etudiant;
import jpa.Enseignant;
import jpa.Administrateur;
import daogenerique.CrudGeneric;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/modifierUtilisateur")
@SessionAttributes("username")
public class ModifierUtilisateurController {

    private final CrudGeneric<Etudiant> etudiantDAO;
    private final CrudGeneric<Enseignant> enseignantDAO;
    private final CrudGeneric<Administrateur> adminDAO;

    @Autowired
    public ModifierUtilisateurController() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        this.etudiantDAO = new CrudGeneric<>(sessionFactory, Etudiant.class);
        this.enseignantDAO = new CrudGeneric<>(sessionFactory, Enseignant.class);
        this.adminDAO = new CrudGeneric<>(sessionFactory, Administrateur.class);
    }

    /**
     * Affiche les informations de l'utilisateur pour modification.
     */
    @GetMapping
    public String showUserInfo(
            @RequestParam("userType") String userType,
            @RequestParam("id") Long userId,
            Model model) {

        Object user = null;
        if ("etudiant".equalsIgnoreCase(userType)) {
            user = etudiantDAO.read(userId);
        } else if ("enseignant".equalsIgnoreCase(userType)) {
            user = enseignantDAO.read(userId);
        } else if ("administrateur".equalsIgnoreCase(userType)) {
            user = adminDAO.read(userId);
        }

        model.addAttribute("user", user);
        model.addAttribute("userType", userType);

        return "AfficherInfos"; // Vue pour afficher les infos utilisateur
    }

    /**
     * Met à jour les informations de l'utilisateur en fonction de son type.
     */
    @PostMapping
    public String updateUser(
            @RequestParam("userType") String userType,
            @RequestParam("id") Long userId,
            @RequestParam("nom") String nom,
            @RequestParam("prenom") String prenom,
            @RequestParam("email") String email,
            @RequestParam(value = "contact", required = false) String contact,
            @RequestParam(value = "dateNaissance", required = false) String dateNaissance,
            HttpSession session) {

        if ("etudiant".equalsIgnoreCase(userType)) {
            updateEtudiant(userId, nom, prenom, email, contact, dateNaissance, session);
        } else if ("enseignant".equalsIgnoreCase(userType)) {
            updateEnseignant(userId, nom, prenom, email, contact, session);
        } else if ("administrateur".equalsIgnoreCase(userType)) {
            updateAdministrateur(userId, nom, prenom, email, session);
        }

        return "redirect:/AfficherInfos"; // Redirection après modification
    }

    private void updateEtudiant(Long id, String nom, String prenom, String email, String contact, String dateNaissance, HttpSession session) {
        Etudiant etudiant = etudiantDAO.read(id);

        etudiant.setNom(nom);
        etudiant.setPrenom(prenom);
        etudiant.setEmail(email);

        if (dateNaissance != null && !dateNaissance.isEmpty()) {
            LocalDate parsedDate = LocalDate.parse(dateNaissance, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            etudiant.setDateNaissance(java.sql.Date.valueOf(parsedDate));
        }

        etudiant.setContact(contact);
        etudiantDAO.update(etudiant);

        session.setAttribute("username", etudiant);
    }

    private void updateEnseignant(Long id, String nom, String prenom, String email, String contact, HttpSession session) {
        Enseignant enseignant = enseignantDAO.read(id);

        enseignant.setNom(nom);
        enseignant.setPrenom(prenom);
        enseignant.setEmail(email);
        enseignant.setContact(contact);

        enseignantDAO.update(enseignant);

        session.setAttribute("username", enseignant);
    }

    private void updateAdministrateur(Long id, String nom, String prenom, String email, HttpSession session) {
        Administrateur admin = adminDAO.read(id);

        admin.setNom(nom);
        admin.setPrenom(prenom);
        admin.setEmail(email);

        adminDAO.update(admin);

        session.setAttribute("username", admin);
    }
}

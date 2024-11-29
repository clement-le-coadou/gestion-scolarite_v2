package mainApp.servlet;

import daogenerique.CrudGeneric;
import mainApp.model.Etudiant;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/modifierEtudiant")
public class ModifierEtudiant {

    private final SessionFactory sessionFactory;

    @Autowired
    public ModifierEtudiant() {
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    @GetMapping
    public String showEditForm(@RequestParam("id") Long etudiantId, Model model) {
        CrudGeneric<Etudiant> etudiantDAO = new CrudGeneric<>(sessionFactory, Etudiant.class);

        // R�cup�rer l'�tudiant par ID
        Etudiant etudiant = etudiantDAO.read(etudiantId);

        // Ajouter l'�tudiant au mod�le pour le formulaire
        model.addAttribute("etudiant", etudiant);

        return "ModifierEtudiant"; // Vue JSP/Thymeleaf � afficher
    }

    @PostMapping
    public String updateEtudiant(
            @RequestParam("id") Long etudiantId,
            @RequestParam("nom") String nom,
            @RequestParam("prenom") String prenom,
            @RequestParam("dateNaissance") String dateNaissanceString,
            @RequestParam("email") String email,
            @RequestParam("contact") String contact) {

        CrudGeneric<Etudiant> etudiantDAO = new CrudGeneric<>(sessionFactory, Etudiant.class);

        // R�cup�rer l'�tudiant par ID et mettre � jour les informations
        Etudiant etudiant = etudiantDAO.read(etudiantId);
        etudiant.setNom(nom);
        etudiant.setPrenom(prenom);

        // Convertir la date de naissance de String � Date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dateNaissance = sdf.parse(dateNaissanceString);
            etudiant.setDateNaissance(dateNaissance);
        } catch (ParseException e) {
            e.printStackTrace(); // G�rer l'exception de mani�re appropri�e
        }

        etudiant.setEmail(email);
        etudiant.setContact(contact);

        // Enregistrer les modifications
        etudiantDAO.update(etudiant);

        // Rediriger apr�s la modification
        return "redirect:/gestionEtudiants"; // URL pour la page de gestion des �tudiants
    }
}

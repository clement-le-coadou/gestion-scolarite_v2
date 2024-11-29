package controller;

import model.Etudiant;
import daogenerique.CrudGeneric;
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

        // Récupérer l'étudiant par ID
        Etudiant etudiant = etudiantDAO.read(etudiantId);

        // Ajouter l'étudiant au modèle pour le formulaire
        model.addAttribute("etudiant", etudiant);

        return "ModifierEtudiant"; // Vue JSP/Thymeleaf à afficher
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

        // Récupérer l'étudiant par ID et mettre à jour les informations
        Etudiant etudiant = etudiantDAO.read(etudiantId);
        etudiant.setNom(nom);
        etudiant.setPrenom(prenom);

        // Convertir la date de naissance de String à Date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dateNaissance = sdf.parse(dateNaissanceString);
            etudiant.setDateNaissance(dateNaissance);
        } catch (ParseException e) {
            e.printStackTrace(); // Gérer l'exception de manière appropriée
        }

        etudiant.setEmail(email);
        etudiant.setContact(contact);

        // Enregistrer les modifications
        etudiantDAO.update(etudiant);

        // Rediriger après la modification
        return "redirect:/gestionEtudiants"; // URL pour la page de gestion des étudiants
    }
}

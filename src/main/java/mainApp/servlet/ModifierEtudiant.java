package mainApp.servlet;

import mainApp.model.Etudiant;
import mainApp.service.EtudiantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/modifierEtudiant")
public class ModifierEtudiant {

    private final EtudiantService etudiantService;

    @Autowired
    public ModifierEtudiant(EtudiantService etudiantService) {
        this.etudiantService = etudiantService;
    }

    @GetMapping
    public String showEditForm(@RequestParam("id") Long etudiantId, Model model) {
        // Récupérer l'étudiant par ID
        Etudiant etudiant = etudiantService.findEtudiantById(etudiantId);

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

        // Récupérer l'étudiant par ID et mettre à jour les informations
        Etudiant etudiant = etudiantService.findEtudiantById(etudiantId);
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
        etudiantService.updateEtudiant(etudiant);

        // Rediriger après la modification
        return "redirect:/gestionEtudiants"; // URL pour la page de gestion des étudiants
    }
}

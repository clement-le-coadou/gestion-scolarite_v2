package com.example.demo.controller;

import com.example.demo.dao.CrudGeneric;
import com.example.demo.jpa.Cours;
import com.example.demo.jpa.Etudiant;
import com.example.demo.jpa.Inscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class InscrireEtudiantController {

    @Autowired
    private CrudGeneric<Etudiant> etudiantDAO;

    @Autowired
    private CrudGeneric<Cours> coursDAO;

    @Autowired
    private CrudGeneric<Inscription> inscriptionDAO;

    // Méthode pour gérer l'inscription d'un étudiant à un cours
    @PostMapping("/InscrireEtudiant")
    public String inscrireEtudiant(@RequestParam("etudiantId") Long etudiantId, 
                                    @RequestParam("coursId") Long coursId, 
                                    Model model) {

        try {
            // Récupérer l'étudiant et le cours à partir des IDs
            Etudiant etudiant = etudiantDAO.read(etudiantId);
            Cours cours = coursDAO.read(coursId);

            if (etudiant == null || cours == null) {
                model.addAttribute("errorMessage", "L'étudiant ou le cours n'existe pas.");
                return "error"; // Redirige vers une page d'erreur si l'étudiant ou le cours n'est pas trouvé
            }

            // Créer une nouvelle inscription
            Inscription inscription = new Inscription();
            inscription.setEtudiant(etudiant);
            inscription.setCours(cours);

            // Enregistrer l'inscription
            inscriptionDAO.create(inscription);

            // Ajouter un message de succès
            model.addAttribute("successMessage", "L'étudiant a été inscrit au cours avec succès.");

            // Rediriger vers la page d'affichage des cours
            return "redirect:/AfficherCours?page=gestion"; // Page où les cours sont affichés

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Une erreur est survenue lors de l'inscription.");
            return "error"; // Page d'erreur
        }
    }
}

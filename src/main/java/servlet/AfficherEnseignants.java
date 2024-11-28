package servlet;

import daogenerique.CrudGeneric;
import jpa.Enseignant;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AfficherEnseignants {

    @GetMapping("/AfficherEnseignants")
    public String afficherEnseignants(Model model) {
        // Configurer la SessionFactory pour Hibernate
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        CrudGeneric<Enseignant> enseignantDAO = new CrudGeneric<>(sessionFactory, Enseignant.class);

        // Récupérer tous les enseignants
        List<Enseignant> enseignants = enseignantDAO.findAll();
        
        // Debugging : Affichage des ID de chaque enseignant dans la console
        for (Enseignant enseignant : enseignants) {
            System.out.println("Enseignant ID: " + enseignant.getId() + ", Nom: " + enseignant.getNom());
        }

        // Passer la liste des enseignants à la JSP via le modèle
        model.addAttribute("enseignants", enseignants);

        // Retourner le nom de la page JSP pour l'affichage
        return "GestionEnseignants"; // Cette page JSP doit être dans le dossier WEB-INF/jsp/
    }
}

package servlet;

import daogenerique.CrudGeneric;
import model.Cours;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AfficherCours {

    @GetMapping("/AfficherCours")
    public String afficherCours(@RequestParam(value = "page", required = false) String page, Model model) {
        // Configurer la SessionFactory pour Hibernate
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        CrudGeneric<Cours> coursDAO = new CrudGeneric<>(sessionFactory, Cours.class);

        // Récupérer tous les cours
        List<Cours> coursList = coursDAO.findAll();
        
        // Debugging : Affichage des ID de chaque cours dans la console
        for (Cours cours : coursList) {
            System.out.println("Cours ID: " + cours.getId() + ", Nom: " + cours.getNom());
        }

        // Passer la liste des cours à la JSP
        model.addAttribute("coursList", coursList);

        // Rediriger vers la page appropriée
        if ("gestion".equals(page)) {
            return "GestionCours"; // Page de gestion des cours
        } else {
            return "AfficherCours"; // Page d'affichage des cours
        }
    }
}

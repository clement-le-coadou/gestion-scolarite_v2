package servlet;

import daogenerique.CrudGeneric;
import jpa.Etudiant;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AfficherEtudiants {

    @GetMapping("/AfficherEtudiants")
    public String afficherEtudiants(Model model) {
        // Configurer la SessionFactory pour Hibernate
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        CrudGeneric<Etudiant> etudiantDAO = new CrudGeneric<>(sessionFactory, Etudiant.class);

        // R�cup�rer tous les �tudiants
        List<Etudiant> etudiants = etudiantDAO.findAll();
        
        // Debugging : Affichage des ID de chaque �tudiant dans la console
        for (Etudiant etudiant : etudiants) {
            System.out.println("Etudiant ID: " + etudiant.getId());
        }

        // Passer la liste des �tudiants au mod�le
        model.addAttribute("etudiants", etudiants);

        // Retourner le nom de la page JSP pour l'affichage
        return "GestionEtudiants"; // Cette page JSP doit �tre dans le dossier WEB-INF/jsp/
    }
}

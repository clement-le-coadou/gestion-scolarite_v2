package mainApp.servlet;

import daogenerique.CrudGeneric;
import mainApp.model.Cours;
import mainApp.model.Etudiant;
import mainApp.model.Inscription;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ConsulterCours {

    @Autowired
    private SessionFactory sessionFactory;

    private CrudGeneric<Etudiant> etudiantDAO;
    private CrudGeneric<Inscription> inscriptionDAO;
    private CrudGeneric<Cours> coursDAO;

    // Constructeur pour l'initialisation de la SessionFactory et des DAOs
    public ConsulterCours() {
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
        this.etudiantDAO = new CrudGeneric<>(sessionFactory, Etudiant.class);
        this.inscriptionDAO = new CrudGeneric<>(sessionFactory, Inscription.class);
        this.coursDAO = new CrudGeneric<>(sessionFactory, Cours.class);
    }

    @GetMapping("/ConsulterCours")
    public String consulterCours(@RequestParam("id") Long etudiantId, Model model) {
        // R�cup�rer l'�tudiant avec l'id donn�
        Etudiant etudiant = etudiantDAO.findById(etudiantId.intValue());

        if (etudiant == null) {
            model.addAttribute("error", "�tudiant non trouv�");
            return "error"; // Redirige vers une page d'erreur si l'�tudiant n'existe pas
        }

        // R�cup�rer la liste des inscriptions de l'�tudiant
        List<Inscription> inscriptions = inscriptionDAO.findAll()
                .stream()
                .filter(inscription -> inscription.getEtudiant().getId().equals(etudiant.getId()))
                .collect(Collectors.toList());

        // R�cup�rer les cours associ�s � l'�tudiant
        List<Cours> coursList = inscriptions.stream()
                .map(Inscription::getCours)
                .distinct()
                .collect(Collectors.toList());

        // Passer la liste des cours � la vue
        model.addAttribute("coursList", coursList);
        return "ConsulterCours"; // Retourne la vue ConsulterCours (JSP ou Thymeleaf)
    }
}

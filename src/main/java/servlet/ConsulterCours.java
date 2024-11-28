package servlet;

import jpa.Cours;
import jpa.Etudiant;
import jpa.Inscription;
import daogenerique.CrudGeneric;
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
public class ConsulterCoursController {

    @Autowired
    private SessionFactory sessionFactory;

    private CrudGeneric<Etudiant> etudiantDAO;
    private CrudGeneric<Inscription> inscriptionDAO;
    private CrudGeneric<Cours> coursDAO;

    // Constructeur pour l'initialisation de la SessionFactory et des DAOs
    public ConsulterCoursController() {
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
        this.etudiantDAO = new CrudGeneric<>(sessionFactory, Etudiant.class);
        this.inscriptionDAO = new CrudGeneric<>(sessionFactory, Inscription.class);
        this.coursDAO = new CrudGeneric<>(sessionFactory, Cours.class);
    }

    @GetMapping("/ConsulterCours")
    public String consulterCours(@RequestParam("id") Long etudiantId, Model model) {
        // Récupérer l'étudiant avec l'id donné
        Etudiant etudiant = etudiantDAO.findById(etudiantId.intValue());

        if (etudiant == null) {
            model.addAttribute("error", "Étudiant non trouvé");
            return "error"; // Redirige vers une page d'erreur si l'étudiant n'existe pas
        }

        // Récupérer la liste des inscriptions de l'étudiant
        List<Inscription> inscriptions = inscriptionDAO.findAll()
                .stream()
                .filter(inscription -> inscription.getEtudiant().getId().equals(etudiant.getId()))
                .collect(Collectors.toList());

        // Récupérer les cours associés à l'étudiant
        List<Cours> coursList = inscriptions.stream()
                .map(Inscription::getCours)
                .distinct()
                .collect(Collectors.toList());

        // Passer la liste des cours à la vue
        model.addAttribute("coursList", coursList);
        return "ConsulterCours"; // Retourne la vue ConsulterCours (JSP ou Thymeleaf)
    }
}

package mainApp.servlet;

import model.Cours;
import model.Etudiant;
import model.Enseignant;
import model.Inscription;
import daogenerique.CrudGeneric;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MesCours {

    private final SessionFactory sessionFactory;

    @Autowired
    public MesCours() {
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    @GetMapping("/mesCours")
    public String getMesCours(@SessionAttribute("username") Object utilisateur, Model model) {
        CrudGeneric<Cours> coursDAO = new CrudGeneric<>(sessionFactory, Cours.class);

        List<Cours> coursList = null;

        if (utilisateur instanceof Etudiant) {
            Etudiant etudiant = (Etudiant) utilisateur;

            CrudGeneric<Inscription> inscriptionDAO = new CrudGeneric<>(sessionFactory, Inscription.class);
            List<Inscription> inscriptions = inscriptionDAO.findAll()
                    .stream()
                    .filter(inscription -> inscription.getEtudiant().getId().equals(etudiant.getId()))
                    .collect(Collectors.toList());

            coursList = inscriptions.stream()
                    .map(Inscription::getCours)
                    .distinct()
                    .collect(Collectors.toList());

        } else if (utilisateur instanceof Enseignant) {
            Enseignant enseignant = (Enseignant) utilisateur;

            coursList = coursDAO.findAll()
                    .stream()
                    .filter(cours -> cours.getEnseignant() != null && cours.getEnseignant().getId().equals(enseignant.getId()))
                    .collect(Collectors.toList());
        }

        model.addAttribute("coursList", coursList);
        return "AfficherCours";  // View name (Thymeleaf template)
    }
}

package service;

import model.Cours;
import model.Etudiant;
import model.Enseignant;
import model.Inscription;
import daogenerique.CrudGeneric;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoursService {

    private final CrudGeneric<Cours> coursDAO;
    private final CrudGeneric<Inscription> inscriptionDAO;

    @Autowired
    public CoursService(SessionFactory sessionFactory) {
        this.coursDAO = new CrudGeneric<>(sessionFactory, Cours.class);
        this.inscriptionDAO = new CrudGeneric<>(sessionFactory, Inscription.class);
    }

    public List<Cours> getCoursForUtilisateur(Object utilisateur) {
        List<Cours> coursList = null;

        if (utilisateur instanceof Etudiant) {
            Etudiant etudiant = (Etudiant) utilisateur;
            // Récupérer les cours auxquels l'étudiant est inscrit
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
            // Récupérer les cours enseignés par l'enseignant
            coursList = coursDAO.findAll()
                    .stream()
                    .filter(cours -> cours.getEnseignant() != null && cours.getEnseignant().getId().equals(enseignant.getId()))
                    .collect(Collectors.toList());
        }

        return coursList;
    }
}

package mainApp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mainApp.model.Cours;
import mainApp.model.Etudiant;
import mainApp.model.Inscription;

import java.util.List;

@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, Long> {
    
    // Lire une inscription par son ID
    Inscription findInscriptionById(Long id);

    // Lister toutes les inscriptions
    List<Inscription> findAll();

    // Supprimer une inscription par ID
    void deleteById(Long id);

    // Requête pour trouver toutes les inscriptions d'un étudiant
    List<Inscription> findByEtudiantId(Long etudiantId);

    // Requête personnalisée avec SQL pour vérifier si un étudiant est déjà inscrit à un cours
    @Query("SELECT CASE WHEN COUNT(i) > 0 THEN true ELSE false END FROM Inscription i WHERE i.etudiant = :etudiant AND i.cours = :cours")
    boolean isAlreadyInscribed(Etudiant etudiant, Cours cours);

    // Trouver une inscription par un étudiant et un cours
    List<Inscription> findByEtudiantAndCours(Etudiant etudiant, Cours cours);

    // Trouver une inscription par ID d'étudiant et ID de cours
    List<Inscription> findByEtudiantIdAndCoursId(Long etudiantId, Long coursId);
}

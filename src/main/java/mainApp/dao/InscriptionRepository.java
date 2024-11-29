package mainApp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mainApp.model.Cours;
import mainApp.model.Etudiant;
import mainApp.model.Inscription;

import java.util.List;
import java.util.Optional;

@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, Long> {
    // Read - finding by ID
    Inscription findInscriptionById(Long id);

    // List all inscriptions
    List<Inscription> findAll();

    // Delete
    void deleteById(Long id);

    // Custom query to find inscriptions for a specific student (Etudiant)
    List<Inscription> findByEtudiantId(Long etudiantId);

    public boolean isAlreadyInscribed(Etudiant etudiant, Cours cours);

	List<Inscription> findByEtudiantAndCours(Etudiant etudiant, Cours cours);

	List<Inscription> findByEtudiantIdAndCoursId(Long etudiantId, Long coursId);
}

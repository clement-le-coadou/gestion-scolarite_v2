package dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import model.Inscription;

import java.util.List;

@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, Long> {

    // The following methods are automatically implemented by Spring Data JPA:

    // Create - done with `save()` automatically
    // public Inscription save(Inscription inscription);

    // Read - finding by ID
    Inscription findInscriptionById(Long id);

    // List all inscriptions
    List<Inscription> findAll();

    // Update - done with `save()` as it merges entities
    // public Inscription save(Inscription inscription);

    // Delete
    void deleteById(Long id);

    // Custom query to find inscriptions for a specific student (Etudiant)
    List<Inscription> findByEtudiantId(Long etudiantId);
}

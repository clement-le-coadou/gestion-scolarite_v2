package dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import model.Enseignant;

import java.util.List;

@Repository
public interface EnseignantRepository extends JpaRepository<Enseignant, Long> {

    // The following methods are automatically implemented by Spring Data JPA:

    // Create - done with `save()` automatically
    // public Enseignant save(Enseignant enseignant);

    // Read - finding by ID
    Enseignant findEnseignantById(Long id);

    // List all
    List<Enseignant> findAll();

    // Update - done with `save()` as it merges entities
    // public Enseignant save(Enseignant enseignant);

    // Delete
    void deleteById(Long id);
}

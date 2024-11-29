package mainApp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mainApp.model.Etudiant;

import java.util.List;

@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {

    // The following methods are automatically implemented by Spring Data JPA:

    // Create - done with `save()` automatically
    // public Etudiant save(Etudiant etudiant);

    // Read - finding by ID
    Etudiant findEtudiantById(Long id);

    // List all
    List<Etudiant> findAll();

    // Update - done with `save()` as it merges entities
    // public Etudiant save(Etudiant etudiant);

    // Delete
    void deleteById(Long id);
    
    public Etudiant findByEmail(String email);
}

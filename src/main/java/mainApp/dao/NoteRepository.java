package mainApp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mainApp.model.Note;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    // The following methods are automatically implemented by Spring Data JPA:

    // Create - done with `save()` automatically
    // public Note save(Note note);

    // Read - finding by ID
    Note findNoteById(Long id);

    // List all notes
    List<Note> findAll();

    // Update - done with `save()` as it merges entities
    // public Note save(Note note);

    // Delete
    void deleteById(Long id);

    // Custom query to find notes by a specific student (Etudiant)
    List<Note> findByEtudiantId(Long etudiantId);
}

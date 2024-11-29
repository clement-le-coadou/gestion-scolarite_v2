package mainApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mainApp.dao.NoteRepository;
import mainApp.model.Note;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    // Create a new Note
    public Note createNote(Note note) {
        return noteRepository.save(note);
    }

    // Find Note by ID
    public Note findNoteById(Long id) {
        Optional<Note> note = noteRepository.findById(id);
        return note.orElse(null);  // Return null if not found
    }

    // Find all Notes
    public List<Note> findAllNotes() {
        return noteRepository.findAll();
    }

    // Update an existing Note
    public Note updateNote(Note note) {
        if (noteRepository.existsById(note.getId())) {
            return noteRepository.save(note);  // Will update if exists
        } else {
            return null;  // Return null if the entity doesn't exist
        }
    }

    // Delete Note by ID
    public void deleteNote(Long id) {
        if (noteRepository.existsById(id)) {
            noteRepository.deleteById(id);
        }
    }
}

package mainApp.service;

import mainApp.dao.NoteRepository;
import mainApp.dao.InscriptionRepository;

import mainApp.model.Note;
import mainApp.model.Cours;
import mainApp.model.Inscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PdfService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private InscriptionRepository inscriptionRepository;

    public List<Cours> getCoursForEtudiant(Long etudiantId) {
        List<Inscription> inscriptions = inscriptionRepository.findByEtudiantId(etudiantId);
        return inscriptions.stream()
                .map(Inscription::getCours)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Note> getNotesForEtudiant(Long etudiantId) {
        return noteRepository.findAll().stream()
                .filter(note -> note.getEtudiant().getId().equals(etudiantId))
                .collect(Collectors.toList());
    }
}

package mainApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mainApp.dao.InscriptionRepository;
import mainApp.model.Cours;
import mainApp.model.Etudiant;
import mainApp.model.Inscription;

import java.util.List;
import java.util.Optional;

@Service
public class InscriptionService {

    private final InscriptionRepository inscriptionRepository;

    @Autowired
    public InscriptionService(InscriptionRepository inscriptionRepository) {
        this.inscriptionRepository = inscriptionRepository;
    }

    // Create a new Inscription
    public Inscription createInscription(Inscription inscription) {
        return inscriptionRepository.save(inscription);
    }

    // Find Inscription by ID
    public Inscription findInscriptionById(Long id) {
        return inscriptionRepository.findById(id).orElse(null); // Return null if not found
    }

    // Find all Inscriptions
    public List<Inscription> findAllInscriptions() {
        return inscriptionRepository.findAll();
    }

    // Update an existing Inscription
    public Inscription updateInscription(Inscription inscription) {
        if (inscriptionRepository.existsById(inscription.getId())) {
            return inscriptionRepository.save(inscription); // Save updates if exists
        } else {
            throw new IllegalArgumentException("Inscription with ID " + inscription.getId() + " does not exist.");
        }
    }

    // Delete Inscription by ID
    public void deleteInscription(Long id) {
        if (inscriptionRepository.existsById(id)) {
            inscriptionRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Inscription with ID " + id + " does not exist.");
        }
    }

    // Find all inscriptions for a specific Etudiant
    public List<Inscription> findInscriptionsByEtudiantId(Long etudiantId) {
        return inscriptionRepository.findByEtudiantId(etudiantId);
    }

    // Find all inscriptions for a specific Etudiant and Cours
    public List<Inscription> findByEtudiantAndCours(Long etudiantId, Long coursId) {
        return inscriptionRepository.findByEtudiantIdAndCoursId(etudiantId, coursId);
    }

    // Check if an Etudiant is already inscribed in a Cours
    public boolean isAlreadyInscribed(Etudiant etudiant, Cours cours) {
        return !inscriptionRepository.findByEtudiantAndCours(etudiant, cours).isEmpty();
    }

    // Helper method to manage inscription creation with validation
    public Inscription createInscriptionIfNotExists(Etudiant etudiant, Cours cours) {
        if (isAlreadyInscribed(etudiant, cours)) {
            throw new IllegalArgumentException("Etudiant is already inscribed in the course.");
        }
        Inscription inscription = new Inscription();
        inscription.setEtudiant(etudiant);
        inscription.setCours(cours);
        return inscriptionRepository.save(inscription);
    }
}

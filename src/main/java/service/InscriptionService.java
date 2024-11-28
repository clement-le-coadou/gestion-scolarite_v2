package com.yourcompany.yourprojectname.service;

import com.yourcompany.yourprojectname.dao.InscriptionRepository;
import com.yourcompany.yourprojectname.model.Inscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Optional<Inscription> inscription = inscriptionRepository.findById(id);
        return inscription.orElse(null);  // Return null if not found
    }

    // Find all Inscriptions
    public List<Inscription> findAllInscriptions() {
        return inscriptionRepository.findAll();
    }

    // Update an existing Inscription
    public Inscription updateInscription(Inscription inscription) {
        if (inscriptionRepository.existsById(inscription.getId())) {
            return inscriptionRepository.save(inscription);  // Will update if exists
        } else {
            return null;  // Return null if the entity doesn't exist
        }
    }

    // Delete Inscription by ID
    public void deleteInscription(Long id) {
        if (inscriptionRepository.existsById(id)) {
            inscriptionRepository.deleteById(id);
        }
    }
}

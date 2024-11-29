package mainApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mainApp.dao.InscriptionRepository;
import mainApp.model.Cours;
import mainApp.model.Etudiant;
import mainApp.model.Inscription;

import java.util.List;

@Service
public class InscriptionService {

    private final InscriptionRepository inscriptionRepository;

    @Autowired
    public InscriptionService(InscriptionRepository inscriptionRepository) {
        this.inscriptionRepository = inscriptionRepository;
    }

    // Créer une nouvelle inscription
    public Inscription createInscription(Inscription inscription) {
        return inscriptionRepository.save(inscription);
    }

    // Trouver une inscription par ID
    public Inscription findInscriptionById(Long id) {
        return inscriptionRepository.findById(id).orElse(null); // Retourne null si non trouvé
    }

    // Trouver toutes les inscriptions
    public List<Inscription> findAllInscriptions() {
        return inscriptionRepository.findAll();
    }

    // Mettre à jour une inscription existante
    public Inscription updateInscription(Inscription inscription) {
        if (inscriptionRepository.existsById(inscription.getId())) {
            return inscriptionRepository.save(inscription); // Sauvegarde la mise à jour si elle existe
        } else {
            throw new IllegalArgumentException("Inscription avec l'ID " + inscription.getId() + " n'existe pas.");
        }
    }

    // Supprimer une inscription par ID
    public void deleteInscription(Long id) {
        if (inscriptionRepository.existsById(id)) {
            inscriptionRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Inscription avec l'ID " + id + " n'existe pas.");
        }
    }

    // Trouver toutes les inscriptions pour un étudiant spécifique
    public List<Inscription> findInscriptionsByEtudiantId(Long etudiantId) {
        return inscriptionRepository.findByEtudiantId(etudiantId);
    }

    // Vérifier si un étudiant est déjà inscrit à un cours donné
    public boolean isAlreadyInscribed(Etudiant etudiant, Cours cours) {
        return inscriptionRepository.isAlreadyInscribed(etudiant, cours);
    }

    // Méthode pour créer une inscription si elle n'existe pas déjà
    public Inscription createInscriptionIfNotExists(Etudiant etudiant, Cours cours) {
        if (isAlreadyInscribed(etudiant, cours)) {
            throw new IllegalArgumentException("L'étudiant est déjà inscrit à ce cours.");
        }
        Inscription inscription = new Inscription();
        inscription.setEtudiant(etudiant);
        inscription.setCours(cours);
        return inscriptionRepository.save(inscription);
    }
}

package mainApp.service;


import mainApp.model.EmploiDuTempsEleve;
import mainApp.model.EmploiDuTempsEnseignant;
import mainApp.dao.EmploiDuTempsEleveRepository;
import mainApp.dao.EmploiDuTempsEnseignantRepository;
import mainApp.emploiDuTemps.JourSemaine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class EmploiDuTempsEnseignantService {

    @Autowired
    private EmploiDuTempsEnseignantRepository emploiDuTempsEnseignantRepository;
    
    @Autowired
    private EmploiDuTempsEleveRepository emploiDuTempsEleveRepository;

    // Méthode pour ajouter un emploi du temps
    public EmploiDuTempsEnseignant ajouterEmploiDuTemps(EmploiDuTempsEnseignant emploiDuTemps) {
        return emploiDuTempsEnseignantRepository.save(emploiDuTemps);
    }

    // Méthode pour récupérer un emploi du temps par l'ID de l'enseignant
    public List<EmploiDuTempsEnseignant> getEmploiDuTempsParEnseignant(Long enseignantId) {
        return emploiDuTempsEnseignantRepository.findByEnseignantId(enseignantId);
    }

    // Méthode pour récupérer un emploi du temps par jour de la semaine
    public List<EmploiDuTempsEnseignant> getEmploiDuTempsParJour(JourSemaine jourSemaine) {
        return emploiDuTempsEnseignantRepository.findByJourSemaine(jourSemaine);
    }

    // Méthode pour récupérer un emploi du temps spécifique par ID
    public Optional<EmploiDuTempsEnseignant> getEmploiDuTempsById(Long id) {
        return emploiDuTempsEnseignantRepository.findById(id);
    }

    // Méthode pour supprimer un emploi du temps
    public void supprimerEmploiDuTemps(Long id) {
        emploiDuTempsEnseignantRepository.deleteById(id);
    }
    
    public boolean verifierDisponibiliteEnseignant(Long enseignantId, LocalTime dateHeureDebut, int duree) {
        // Vérifier si l'enseignant a déjà un emploi du temps à cette heure
        List<EmploiDuTempsEnseignant> emploisDuTempsEnseignant = emploiDuTempsEnseignantRepository.findByEnseignantIdAndDateHeureDebutBetween(enseignantId, dateHeureDebut, dateHeureDebut.plusMinutes(duree));
        return emploisDuTempsEnseignant.isEmpty();
    }
    
    public boolean verifierDisponibiliteEtudiant(Long etudiantId, LocalTime dateHeureDebut, int duree) {
        // Vérifier si l'étudiant a déjà un emploi du temps à cette heure
        List<EmploiDuTempsEleve> emploisDuTempsEtudiant = emploiDuTempsEleveRepository
                .findByEleveIdAndDateHeureDebutBetween(etudiantId, dateHeureDebut, dateHeureDebut.plusMinutes(duree));
        return emploisDuTempsEtudiant.isEmpty();
    }
    
    public boolean verifierDisponibiliteSalle(String salle, LocalTime dateHeureDebut, int duree) {
        // Vérifier s'il existe déjà un emploi du temps avec cette salle à la même heure
        List<EmploiDuTempsEleve> emploisDuTempsSalle = emploiDuTempsEleveRepository.findBySalleAndDateHeureDebutBetween(salle, dateHeureDebut, dateHeureDebut.plusMinutes(duree));
        return emploisDuTempsSalle.isEmpty();
    }

}

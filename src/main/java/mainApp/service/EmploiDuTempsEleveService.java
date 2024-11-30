package mainApp.service;


import mainApp.model.EmploiDuTempsEleve;
import mainApp.dao.EmploiDuTempsEleveRepository;
import mainApp.emploiDuTemps.JourSemaine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class EmploiDuTempsEleveService {

    @Autowired
    private EmploiDuTempsEleveRepository emploiDuTempsEleveRepository;

    // Méthode pour ajouter un emploi du temps
    public EmploiDuTempsEleve ajouterEmploiDuTemps(EmploiDuTempsEleve emploiDuTemps) {
        return emploiDuTempsEleveRepository.save(emploiDuTemps);
    }

    // Méthode pour récupérer un emploi du temps par l'ID de l'élève
    public List<EmploiDuTempsEleve> getEmploiDuTempsParEleve(Long eleveId) {
        return emploiDuTempsEleveRepository.findByEleveId(eleveId);
    }

    // Méthode pour récupérer un emploi du temps par jour de la semaine
    public List<EmploiDuTempsEleve> getEmploiDuTempsParJour(JourSemaine jourSemaine) {
        return emploiDuTempsEleveRepository.findByJourSemaine(jourSemaine);
    }

    // Méthode pour récupérer un emploi du temps spécifique par ID
    public Optional<EmploiDuTempsEleve> getEmploiDuTempsById(Long id) {
        return emploiDuTempsEleveRepository.findById(id);
    }

    // Méthode pour supprimer un emploi du temps
    public void supprimerEmploiDuTemps(Long id) {
        emploiDuTempsEleveRepository.deleteById(id);
    }
    
    public boolean verifierDisponibiliteSalle(String salle, LocalTime dateHeureDebut, int duree) {
        // Vérifier s'il existe déjà un emploi du temps avec cette salle à la même heure
        List<EmploiDuTempsEleve> emploisDuTempsSalle = emploiDuTempsEleveRepository.findBySalleAndDateHeureDebutBetween(salle, dateHeureDebut, dateHeureDebut.plusMinutes(duree));
        return emploisDuTempsSalle.isEmpty();
    }
    
    public boolean verifierDisponibiliteEtudiant(Long etudiantId, LocalTime dateHeureDebut, int duree) {
        // Vérifier si l'étudiant a déjà un emploi du temps à cette heure
        List<EmploiDuTempsEleve> emploisDuTempsEtudiant = emploiDuTempsEleveRepository.findByEleveIdAndDateHeureDebutBetween(etudiantId, dateHeureDebut, dateHeureDebut.plusMinutes(duree));
        return emploisDuTempsEtudiant.isEmpty();
    }


}


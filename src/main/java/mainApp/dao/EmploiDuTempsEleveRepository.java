package mainApp.dao;


import mainApp.emploiDuTemps.JourSemaine;
import mainApp.model.EmploiDuTempsEleve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface EmploiDuTempsEleveRepository extends JpaRepository<EmploiDuTempsEleve, Long> {
    // Vous pouvez ajouter des méthodes spécifiques pour rechercher des emplois du temps d'un élève par exemple :
    List<EmploiDuTempsEleve> findByEleveId(Long eleveId);

    List<EmploiDuTempsEleve> findByJourSemaine(JourSemaine jourSemaine);

	List<EmploiDuTempsEleve> findBySalleAndDateHeureDebutBetween(String salle, LocalTime dateHeureDebut,
			LocalTime localTime);

	List<EmploiDuTempsEleve> findByEleveIdAndDateHeureDebutBetween(Long eleveId, LocalTime dateHeureDebut, LocalTime dateHeureFin);

}

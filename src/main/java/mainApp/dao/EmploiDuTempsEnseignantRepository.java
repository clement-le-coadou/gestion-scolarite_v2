package mainApp.dao;

import mainApp.emploiDuTemps.JourSemaine;
import mainApp.model.EmploiDuTempsEnseignant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface EmploiDuTempsEnseignantRepository extends JpaRepository<EmploiDuTempsEnseignant, Long> {
    // Vous pouvez ajouter des méthodes spécifiques pour rechercher des emplois du temps d'un enseignant par exemple :
    List<EmploiDuTempsEnseignant> findByEnseignantId(Long enseignantId);

    List<EmploiDuTempsEnseignant> findByJourSemaine(JourSemaine jourSemaine);

	List<EmploiDuTempsEnseignant> findByEnseignantIdAndDateHeureDebutBetween(Long enseignantId,
			LocalTime dateHeureDebut, LocalTime localTime);
}


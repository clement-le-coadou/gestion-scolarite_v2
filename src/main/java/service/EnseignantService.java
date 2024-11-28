package ervice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yourcompany.yourprojectname.dao.EnseignantRepository;
import com.yourcompany.yourprojectname.model.Enseignant;

import java.util.List;

@Service
public class EnseignantService {

    @Autowired
    private EnseignantRepository enseignantRepository;

    public void createEnseignant(Enseignant enseignant) {
        enseignantRepository.save(enseignant); // `save()` will insert or update the entity
    }

    public Enseignant findEnseignantById(Long id) {
        return enseignantRepository.findEnseignantById(id);
    }

    public List<Enseignant> findAllEnseignants() {
        return enseignantRepository.findAll();
    }

    public void updateEnseignant(Enseignant enseignant) {
        enseignantRepository.save(enseignant); // `save()` will update if the entity already exists
    }

    public void deleteEnseignant(Long id) {
        enseignantRepository.deleteById(id);
    }
}

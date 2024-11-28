package service;

import com.yourcompany.yourprojectname.dao.EtudiantRepository;
import com.yourcompany.yourprojectname.model.Etudiant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EtudiantService {

    private final EtudiantRepository etudiantRepository;

    @Autowired
    public EtudiantService(EtudiantRepository etudiantRepository) {
        this.etudiantRepository = etudiantRepository;
    }

    // Create a new Etudiant
    public Etudiant createEtudiant(Etudiant etudiant) {
        return etudiantRepository.save(etudiant);
    }

    // Find Etudiant by ID
    public Etudiant findEtudiantById(Long id) {
        Optional<Etudiant> etudiant = etudiantRepository.findById(id);
        return etudiant.orElse(null);  // Returns null if not found
    }

    // Find all Etudiants
    public List<Etudiant> findAllEtudiants() {
        return etudiantRepository.findAll();
    }

    // Update Etudiant
    public Etudiant updateEtudiant(Etudiant etudiant) {
        if (etudiantRepository.existsById(etudiant.getId())) {
            return etudiantRepository.save(etudiant);  // Will update if already exists
        } else {
            return null;  // Return null if the entity doesn't exist
        }
    }

    // Delete Etudiant by ID
    public void deleteEtudiant(Long id) {
        if (etudiantRepository.existsById(id)) {
            etudiantRepository.deleteById(id);
        }
    }
}

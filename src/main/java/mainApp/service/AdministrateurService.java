package mainApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mainApp.dao.AdministrateurRepository;
import mainApp.model.Administrateur;

import java.util.List;
import java.util.Optional;

@Service
public class AdministrateurService {

    private final AdministrateurRepository administrateurRepository;

    @Autowired
    public AdministrateurService(AdministrateurRepository administrateurRepository) {
        this.administrateurRepository = administrateurRepository;
    }

    // Create or update an Administrateur
    public Administrateur saveAdministrateur(Administrateur administrateur) {
        return administrateurRepository.save(administrateur);
    }

    // Find an Administrateur by ID
    public Administrateur findAdministrateurById(Long id) {
        Optional<Administrateur> administrateur = administrateurRepository.findById(id);
        return administrateur.orElse(null);  // Return null if not found
    }

    // Find all Administrateurs
    public List<Administrateur> findAllAdministrateurs() {
        return administrateurRepository.findAll();
    }

    // Delete an Administrateur by ID
    public void deleteAdministrateur(Long id) {
        administrateurRepository.deleteById(id);
    }

    public Administrateur findByEmail(String email) {
        return administrateurRepository.findByEmail(email);
    }
}

package mainApp.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import mainApp.model.Administrateur;

public interface AdministrateurRepository extends JpaRepository<Administrateur, Long> {
    // Custom query methods can be defined here if needed.
    // Example: List<Administrateur> findByNom(String nom);
}

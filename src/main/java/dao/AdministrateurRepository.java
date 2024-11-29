package dao;


import org.springframework.data.jpa.repository.JpaRepository;
import model.Administrateur;

public interface AdministrateurRepository extends JpaRepository<Administrateur, Long> {
    // Custom query methods can be defined here if needed.
    // Example: List<Administrateur> findByNom(String nom);
}

package dao;

package com.yourcompany.yourprojectname.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.yourcompany.yourprojectname.model.Administrateur;

public interface AdministrateurRepository extends JpaRepository<Administrateur, Long> {
    // Custom query methods can be defined here if needed.
    // Example: List<Administrateur> findByNom(String nom);
}

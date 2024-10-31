package test;

import daogenerique.CrudGeneric;
import jpa.Administrateur;
import jpa.Cours;
import jpa.Enseignant;
import jpa.Etudiant;
import jpa.Inscription;

import java.util.List;
import java.sql.Date;
import java.time.LocalDate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        // Initialize DAO instances
        CrudGeneric<Enseignant> enseignantDAO = new CrudGeneric<>(sessionFactory, Enseignant.class);
        CrudGeneric<Etudiant> etudiantDAO = new CrudGeneric<>(sessionFactory, Etudiant.class);
        CrudGeneric<Cours> coursDAO = new CrudGeneric<>(sessionFactory, Cours.class);
        CrudGeneric<Inscription> inscriptionDAO = new CrudGeneric<>(sessionFactory, Inscription.class);

        /*
        // Step 1: Create and save Enseignant
        Enseignant enseignant = new Enseignant();
        enseignant.setNom("John");
        enseignant.setPrenom("Doe");
        enseignant.setEmail("john.test@example.com");
        enseignant.setContact("0767021234");
        enseignant.setMotDePasse("secret");
        enseignantDAO.create(enseignant);  // Save Enseignant first

        // Step 2: Create and save Etudiant
        Etudiant etudiant = new Etudiant();
        etudiant.setNom("Alice");
        etudiant.setPrenom("Smith");
        etudiant.setDateNaissance(Date.valueOf("2003-10-05")); // Ensure date format is correct
        etudiant.setEmail("alice.test@example.com");
        etudiant.setContact("0767025678");
        etudiant.setMotDePasse("password");
        etudiantDAO.create(etudiant);  // Save Etudiant next  */
        Enseignant enseignant = new Enseignant();
        List<Enseignant> listeEnseignants = enseignantDAO.findAll();
        for (Enseignant enseignanttemp : listeEnseignants) {
            System.out.println("Enseignant ID: " + enseignanttemp.getId());
            enseignant = enseignanttemp;
        }
        
        Etudiant etudiant = new Etudiant();
        List<Etudiant> listeEtudiant = etudiantDAO.findAll();
        for (Etudiant etudianttemp : listeEtudiant) {
            System.out.println("Enseignant ID: " + etudianttemp.getId());
            etudiant = etudianttemp;
        }
         
        

        // Step 3: Create and save Cours, associating it with the saved Enseignant
        Cours cours = new Cours();
        cours.setNom("Mathematics");
        cours.setDescription("An introductory course to Mathematics.");
        cours.setEnseignant(enseignant); // Associate with saved Enseignant
        coursDAO.create(cours);  // Save Cours after Enseignant
        
        cours = coursDAO.read(9L);
        

        // Step 4: Create and save Inscription, associating it with saved Etudiant and Cours
        Inscription inscription = new Inscription();
        inscription.setEtudiant(etudiant); // Associate with saved Etudiant
        inscription.setCours(cours);       // Associate with saved Cours
        inscriptionDAO.create(inscription);  // Save Inscription last

        // Cleanup
        sessionFactory.close();
    }
}

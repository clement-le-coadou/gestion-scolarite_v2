package test;

import dao.EnseignantDAO;
import jpa.Enseignant;

public class Main {
    public static void main(String[] args) {
        EnseignantDAO enseignantDAO = new EnseignantDAO();
        
        // Test pour créer un nouvel enseignant
        Enseignant nouvelEnseignant = new Enseignant();
        nouvelEnseignant.setNom("Dupont");
        nouvelEnseignant.setPrenom("Jean");
        nouvelEnseignant.setEmail("jean.dupont@example.com");
        nouvelEnseignant.setMotDePasse("mdp");

        // Essayez d'ajouter un enseignant
        enseignantDAO.createEnseignant(nouvelEnseignant);

        // Affichez tous les enseignants pour vérifier l'insertion
        enseignantDAO.findAllEnseignants().forEach(enseignant -> 
            System.out.println(enseignant.getNom() + " " + enseignant.getPrenom())
        );

        enseignantDAO.close();
    }
}

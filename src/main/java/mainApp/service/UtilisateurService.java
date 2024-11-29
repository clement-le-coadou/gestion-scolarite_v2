package mainApp.service;

import mainApp.model.Administrateur;
import mainApp.model.Enseignant;
import mainApp.model.Etudiant;
import mainApp.dao.AdministrateurRepository;
import mainApp.dao.EnseignantRepository;
import mainApp.dao.EtudiantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpSession;

@Service
public class UtilisateurService {

    private final EtudiantRepository etudiantRepository;
    private final EnseignantRepository enseignantRepository;
    private final AdministrateurRepository administrateurRepository;

    @Autowired
    public UtilisateurService(EtudiantRepository etudiantRepository, EnseignantRepository enseignantRepository, AdministrateurRepository administrateurRepository) {
        this.etudiantRepository = etudiantRepository;
        this.enseignantRepository = enseignantRepository;
        this.administrateurRepository = administrateurRepository;
    }

    // Fetch user by type and id
    public Object getUserById(String userType, Long userId) {
        switch (userType.toLowerCase()) {
            case "etudiant":
                return etudiantRepository.findById(userId).orElse(null);
            case "enseignant":
                return enseignantRepository.findById(userId).orElse(null);
            case "administrateur":
                return administrateurRepository.findById(userId).orElse(null);
            default:
                return null;
        }
    }

    // Update user based on the type
    public void updateUser(String userType, Long userId, String nom, String prenom, String email, String contact, String dateNaissance, HttpSession session) {
        switch (userType.toLowerCase()) {
            case "etudiant":
                updateEtudiant(userId, nom, prenom, email, contact, dateNaissance, session);
                break;
            case "enseignant":
                updateEnseignant(userId, nom, prenom, email, contact, session);
                break;
            case "administrateur":
                updateAdministrateur(userId, nom, prenom, email, session);
                break;
        }
    }

    private void updateEtudiant(Long id, String nom, String prenom, String email, String contact, String dateNaissance, HttpSession session) {
        Etudiant etudiant = etudiantRepository.findById(id).orElse(null);
        if (etudiant != null) {
            etudiant.setNom(nom);
            etudiant.setPrenom(prenom);
            etudiant.setEmail(email);

            if (dateNaissance != null && !dateNaissance.isEmpty()) {
                LocalDate parsedDate = LocalDate.parse(dateNaissance, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                etudiant.setDateNaissance(java.sql.Date.valueOf(parsedDate));
            }

            etudiant.setContact(contact);
            etudiantRepository.save(etudiant);

            session.setAttribute("username", etudiant);
        }
    }

    private void updateEnseignant(Long id, String nom, String prenom, String email, String contact, HttpSession session) {
        Enseignant enseignant = enseignantRepository.findById(id).orElse(null);
        if (enseignant != null) {
            enseignant.setNom(nom);
            enseignant.setPrenom(prenom);
            enseignant.setEmail(email);
            enseignant.setContact(contact);
            enseignantRepository.save(enseignant);

            session.setAttribute("username", enseignant);
        }
    }

    private void updateAdministrateur(Long id, String nom, String prenom, String email, HttpSession session) {
        Administrateur admin = administrateurRepository.findById(id).orElse(null);
        if (admin != null) {
            admin.setNom(nom);
            admin.setPrenom(prenom);
            admin.setEmail(email);
            administrateurRepository.save(admin);

            session.setAttribute("username", admin);
        }
    }
}

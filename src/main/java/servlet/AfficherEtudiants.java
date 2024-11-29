package servlet;

import service.EtudiantService;
import model.Etudiant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AfficherEtudiants {

    private final EtudiantService etudiantService;

    @Autowired
    public AfficherEtudiants(EtudiantService etudiantService) {
        this.etudiantService = etudiantService;
    }

    @GetMapping("/AfficherEtudiants")
    public String afficherEtudiants(Model model) {
        // Récupérer tous les étudiants
        List<Etudiant> etudiants = etudiantService.findAllEtudiants();

        // Debugging : Affichage des ID de chaque étudiant dans la console
        for (Etudiant etudiant : etudiants) {
            System.out.println("Etudiant ID: " + etudiant.getId());
        }

        // Passer la liste des étudiants au modèle
        model.addAttribute("etudiants", etudiants);

        // Retourner le nom de la page JSP pour l'affichage
        return "GestionEtudiants"; // Cette page JSP doit être dans le dossier WEB-INF/jsp/
    }
}

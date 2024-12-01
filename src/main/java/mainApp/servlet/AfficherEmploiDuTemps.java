package mainApp.servlet;

import mainApp.model.EmploiDuTempsEleve;
import mainApp.model.EmploiDuTempsEnseignant;
import mainApp.model.Enseignant;
import mainApp.model.Etudiant;
import mainApp.service.EmploiDuTempsEleveService;
import mainApp.service.EmploiDuTempsEnseignantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
public class AfficherEmploiDuTemps {

    private final EmploiDuTempsEleveService emploiDuTempsEleveService;
    private final EmploiDuTempsEnseignantService emploiDuTempsEnseignantService;

    @Autowired
    public AfficherEmploiDuTemps(EmploiDuTempsEleveService emploiDuTempsEleveService,
                                 EmploiDuTempsEnseignantService emploiDuTempsEnseignantService) {
        this.emploiDuTempsEleveService = emploiDuTempsEleveService;
        this.emploiDuTempsEnseignantService = emploiDuTempsEnseignantService;
    }

    @GetMapping("/AfficherEmploiDuTemps")
    public String afficherEmploiDuTemps(HttpSession session, Model model,HttpServletRequest request,HttpServletResponse response) {
        if (session.getAttribute("role") == null) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/Login");  // Forward to accueil page
            try {
				dispatcher.forward(request, response);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
        }
        // Vérification du type d'utilisateur dans la session
        String userType = (String) session.getAttribute("role");
        // Récupération de l'emploi du temps en fonction du type d'utilisateur
        if (userType.equals("Etudiant")) {
        	
        	Long userId = ((Etudiant) session.getAttribute("username")).getId();
        	
            List<EmploiDuTempsEleve> emploiDuTempsEleve = emploiDuTempsEleveService.getEmploiDuTempsParEleve(userId);

            // Ajouter l'emploi du temps de l'étudiant dans le modèle
            if (emploiDuTempsEleve != null && !emploiDuTempsEleve.isEmpty()) {
                model.addAttribute("emploiDuTemps", emploiDuTempsEleve);
            } else {
                model.addAttribute("message", "Aucun emploi du temps trouvé pour cet étudiant.");
            }

            // Rediriger vers la vue des étudiants
            return "EmploiDuTempsEtudiant";
        } else if (userType.equals("Enseignant")) {
        	
        	Long userId = ((Enseignant) session.getAttribute("username")).getId();
            List<EmploiDuTempsEnseignant> emploiDuTempsEnseignant = emploiDuTempsEnseignantService.getEmploiDuTempsParEnseignant(userId);

            // Ajouter l'emploi du temps de l'enseignant dans le modèle
            if (emploiDuTempsEnseignant != null && !emploiDuTempsEnseignant.isEmpty()) {
                model.addAttribute("emploiDuTemps", emploiDuTempsEnseignant);
            } else {
                model.addAttribute("message", "Aucun emploi du temps trouvé pour cet enseignant.");
            }

            // Rediriger vers la vue des enseignants
            return "EmploiDuTempsEnseignant";
        } else {
            // Si le type d'utilisateur est inconnu
            model.addAttribute("message", "Type d'utilisateur inconnu.");
            return "Login";
        }
    }
}

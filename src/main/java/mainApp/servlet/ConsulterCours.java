package mainApp.servlet;

import mainApp.model.Cours;
import mainApp.model.Etudiant;
import mainApp.service.CoursService;
import mainApp.service.EtudiantService;
import mainApp.service.InscriptionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ConsulterCours {

    @Autowired
    private EtudiantService etudiantService;

    @Autowired
    private InscriptionService inscriptionService;

    @Autowired
    private CoursService coursService;

    @GetMapping("/ConsulterCours")
    public String consulterCours(@RequestParam("id") Long etudiantId, Model model) {
        // Récupérer l'étudiant
        Etudiant etudiant = etudiantService.findEtudiantById(etudiantId);

        if (etudiant == null) {
            model.addAttribute("error", "Étudiant non trouvé");
            return "error"; // Page d'erreur
        }

        // Récupérer les cours de l'étudiant via ses inscriptions
        List<Cours> coursList = inscriptionService.findInscriptionsByEtudiantId(etudiantId).stream()
                .map(inscription -> inscription.getCours())
                .distinct()
                .collect(Collectors.toList());

        model.addAttribute("coursList", coursList);
        return "ConsulterCours"; // Vue JSP pour afficher les cours
    }
}

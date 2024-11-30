package mainApp.servlet;

import mainApp.model.EmploiDuTempsEleve;
import mainApp.model.EmploiDuTempsEnseignant;
import mainApp.model.Cours;
import mainApp.model.Etudiant;
import mainApp.model.Enseignant;
import mainApp.service.EmploiDuTempsEleveService;
import mainApp.service.EmploiDuTempsEnseignantService;
import mainApp.service.CoursService;
import mainApp.service.EtudiantService;
import mainApp.service.EnseignantService;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AjouterCours {

    private final EmploiDuTempsEleveService emploiDuTempsEleveService;
    private final EmploiDuTempsEnseignantService emploiDuTempsEnseignantService;
    private final CoursService coursService;
    private final EtudiantService etudiantService;
    private final EnseignantService enseignantService;

    @Autowired
    public AjouterCours(EmploiDuTempsEleveService emploiDuTempsEleveService,
                        EmploiDuTempsEnseignantService emploiDuTempsEnseignantService,
                        CoursService coursService,
                        EtudiantService etudiantService,
                        EnseignantService enseignantService) {
        this.emploiDuTempsEleveService = emploiDuTempsEleveService;
        this.emploiDuTempsEnseignantService = emploiDuTempsEnseignantService;
        this.coursService = coursService;
        this.etudiantService = etudiantService;
        this.enseignantService = enseignantService;
    }

    @GetMapping("/AjouterEmploiDuTemps")
    public String afficherFormulaire(Model model) {
        // Récupération des listes nécessaires via les services
        List<Cours> coursList = coursService.findAllCours();
        List<Etudiant> etudiants = etudiantService.findAllEtudiants();
        List<Enseignant> enseignants = enseignantService.findAllEnseignants();
        

        // Ajout des listes au modèle
        model.addAttribute("coursList", coursList);
        model.addAttribute("etudiants", etudiants);
        model.addAttribute("enseignants", enseignants);

        return "AjouterEmploiDuTemps"; // Affiche le formulaire d'ajout d'emploi du temps
    }


    @PostMapping("/AjouterEmploiDuTemps")
    public String ajouterEmploiDuTemps(@RequestParam("coursId") Long coursId,
                                       @RequestParam("etudiantId") Long etudiantId,
                                       @RequestParam("enseignantId") Long enseignantId,
                                       @RequestParam("dateHeureDebut") String dateHeureDebut,
                                       @RequestParam("duree") int duree,
                                       @RequestParam("salle") String salle,
                                       RedirectAttributes redirectAttributes) {

        // Récupération du cours, étudiant et enseignant
        Cours cours = coursService.findCoursById(coursId);
        Etudiant etudiant = etudiantService.findEtudiantById(etudiantId);
        Enseignant enseignant = enseignantService.findEnseignantById(enseignantId);

        // Vérification de l'existence du cours, de l'étudiant et de l'enseignant
        if (cours == null || etudiant == null || enseignant == null) {
            redirectAttributes.addFlashAttribute("message", "Erreur : Cours, étudiant ou enseignant non trouvé.");
            return "redirect:/AjouterEmploiDuTemps"; // Redirection en cas d'erreur
        }
        // Conversion de la chaîne "dateHeureDebut" en un objet LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime dateHeureDebutParsed = LocalDateTime.parse(dateHeureDebut, formatter);

        // Extraire l'heure (LocalTime) à partir de LocalDateTime
        LocalTime heureDebut = dateHeureDebutParsed.toLocalTime();

        // Vérification de la disponibilité de la salle
        boolean salleDisponible = emploiDuTempsEleveService.verifierDisponibiliteSalle(salle, heureDebut, duree) && 
                                  emploiDuTempsEnseignantService.verifierDisponibiliteSalle(salle, heureDebut, duree);
        if (!salleDisponible) {
            redirectAttributes.addFlashAttribute("message", "Erreur : La salle est déjà occupée à cette heure.");
            return "redirect:/AjouterEmploiDuTemps";
        }

        // Vérification de la disponibilité de l'étudiant
        boolean etudiantDisponible = emploiDuTempsEleveService.verifierDisponibiliteEtudiant(etudiantId, heureDebut, duree);
        if (!etudiantDisponible) {
            redirectAttributes.addFlashAttribute("message", "Erreur : L'étudiant a déjà un cours à cette heure.");
            return "redirect:/AjouterEmploiDuTemps";
        }

        // Vérification de la disponibilité de l'enseignant
        boolean enseignantDisponible = emploiDuTempsEnseignantService.verifierDisponibiliteEnseignant(enseignantId, heureDebut, duree);
        if (!enseignantDisponible) {
            redirectAttributes.addFlashAttribute("message", "Erreur : L'enseignant a déjà un cours à cette heure.");
            return "redirect:/AjouterEmploiDuTemps";
        }

        // Création de l'EmploiDuTemps pour l'étudiant
        EmploiDuTempsEleve emploiDuTempsEleve = new EmploiDuTempsEleve();
        emploiDuTempsEleve.setCours(cours);
        emploiDuTempsEleve.setHeureDebut(heureDebut);
        emploiDuTempsEleve.setDuree(duree);
        emploiDuTempsEleve.setEleve(etudiant);
        emploiDuTempsEleve.setSalle(salle);

        // Création de l'EmploiDuTemps pour l'enseignant
        EmploiDuTempsEnseignant emploiDuTempsEnseignant = new EmploiDuTempsEnseignant();
        emploiDuTempsEnseignant.setCours(cours);
        emploiDuTempsEnseignant.setHeureDebut(heureDebut);
        emploiDuTempsEnseignant.setDuree(duree);
        emploiDuTempsEnseignant.setEnseignant(enseignant);
        emploiDuTempsEnseignant.setSalle(salle);

        // Sauvegarde des emplois du temps dans la base de données
        emploiDuTempsEleveService.ajouterEmploiDuTemps(emploiDuTempsEleve);
        emploiDuTempsEnseignantService.ajouterEmploiDuTemps(emploiDuTempsEnseignant);

        // Message de succès
        redirectAttributes.addFlashAttribute("message", "Emploi du temps ajouté avec succès.");
        return "redirect:/AfficherEmploiDuTemps"; // Redirection vers la page d'affichage
    }
}

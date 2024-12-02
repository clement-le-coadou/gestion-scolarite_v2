package mainApp.servlet;

import mainApp.model.EmploiDuTempsEleve;
import mainApp.model.EmploiDuTempsEnseignant;
import mainApp.emploiDuTemps.JourSemaine;
import mainApp.model.Cours;
import mainApp.model.Etudiant;
import mainApp.model.Inscription;
import mainApp.model.Enseignant;
import mainApp.service.EmploiDuTempsEleveService;
import mainApp.service.EmploiDuTempsEnseignantService;
import mainApp.service.CoursService;
import mainApp.service.EtudiantService;
import mainApp.service.EnseignantService;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Controller
public class AjouterEmploiDuTemps {

    private final EmploiDuTempsEleveService emploiDuTempsEleveService;
    private final EmploiDuTempsEnseignantService emploiDuTempsEnseignantService;
    private final CoursService coursService;
    private final EtudiantService etudiantService;
    private final EnseignantService enseignantService;

    @Autowired
    public AjouterEmploiDuTemps(EmploiDuTempsEleveService emploiDuTempsEleveService,
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
                                       @RequestParam("jourSemaine") String jourSemaine, 
                                       @RequestParam("heureDebut") String heureDebutBefore,
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

        // Vérification que l'enseignant enseigne bien le cours
        List<Cours> coursEnseignant = coursService.findByEnseignantId(enseignantId);
        boolean enseignantEnseigneLeCours = coursEnseignant.stream().anyMatch(c -> c.getId().equals(coursId));
        if (!enseignantEnseigneLeCours) {
            redirectAttributes.addFlashAttribute("message", "Erreur : L'enseignant ne donne pas ce cours.");
            return "redirect:/AjouterEmploiDuTemps";
        }

        // Vérification que l'étudiant est bien inscrit au cours
        Set<Inscription> inscriptions = etudiant.getInscriptions(); // Obtenez les inscriptions de l'étudiant
        boolean estInscrit = false;
        for (Inscription inscription : inscriptions) {
            if (inscription.getCours().equals(cours)) {
                estInscrit = true;
                break;
            }
        }

        if (!estInscrit) {
            redirectAttributes.addFlashAttribute("message", "Erreur : L'étudiant n'est pas inscrit à ce cours.");
            return "redirect:/AjouterEmploiDuTemps";
        }

        LocalTime heureDebut = LocalTime.parse(heureDebutBefore);
        

        // Vérification de la plage horaire (8h à 17h)
        LocalTime heureDebutPlageMin = LocalTime.of(8, 0);
        LocalTime heureDebutPlageMax = LocalTime.of(17, 0);
        if (heureDebut.isBefore(heureDebutPlageMin) || heureDebut.isAfter(heureDebutPlageMax)) {
            redirectAttributes.addFlashAttribute("message", "Erreur : L'heure de début doit être entre 08h00 et 17h00.");
            return "redirect:/AjouterEmploiDuTemps";
        }
        
        // Vérification de la durée du cours (maximum 60 minutes)
        if (duree > 60) {
            redirectAttributes.addFlashAttribute("message", "Erreur : La durée du cours ne peut pas dépasser 60 minutes.");
            return "redirect:/AjouterEmploiDuTemps";
        }else if(duree < 0) {
            redirectAttributes.addFlashAttribute("message", "Erreur : La durée du cours ne peut pas être négative.");
            return "redirect:/AjouterEmploiDuTemps";        	
        }

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
        emploiDuTempsEleve.setJourSemaine(JourSemaine.valueOf(jourSemaine)); // Enumération
        emploiDuTempsEleve.setDuree(duree);
        emploiDuTempsEleve.setEleve(etudiant);
        emploiDuTempsEleve.setSalle(salle);

        // Création de l'EmploiDuTemps pour l'enseignant
        EmploiDuTempsEnseignant emploiDuTempsEnseignant = new EmploiDuTempsEnseignant();
        emploiDuTempsEnseignant.setCours(cours);
        emploiDuTempsEnseignant.setHeureDebut(heureDebut);
        emploiDuTempsEnseignant.setJourSemaine(JourSemaine.valueOf(jourSemaine)); // Enumération
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

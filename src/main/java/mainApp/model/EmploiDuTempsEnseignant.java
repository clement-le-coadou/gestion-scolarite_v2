package mainApp.model;

import jakarta.persistence.*;
import mainApp.emploiDuTemps.JourSemaine;

import java.time.LocalTime;

@Entity
@Table(name = "emploi_du_temps_enseignant")
public class EmploiDuTempsEnseignant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "enseignant_id", referencedColumnName = "id", nullable = false)
    private Enseignant enseignant;

    @ManyToOne
    @JoinColumn(name = "cours_id", referencedColumnName = "id", nullable = false)
    private Cours cours;

    @Column(name = "jour_semaine", nullable = false)
    @Enumerated(EnumType.STRING)
    private JourSemaine jourSemaine;

    @Column(name = "heure_debut", nullable = false)
    private LocalTime dateHeureDebut;

    @Column(name = "duree", nullable = false)
    private int duree;

    @Column(name = "salle", nullable = false)
    private String salle; // Ajout de l'attribut salle

    // Constructeurs, getters et setters
    public EmploiDuTempsEnseignant() {
    }

    public EmploiDuTempsEnseignant(long id, Enseignant enseignant, Cours cours, JourSemaine jourSemaine, LocalTime heureDebut, int duree, String salle) {
        this.id = id;
        this.enseignant = enseignant;
        this.cours = cours;
        this.jourSemaine = jourSemaine;
        this.dateHeureDebut = heureDebut;
        this.duree = duree;
        this.salle = salle; // Initialisation de la salle
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Enseignant getEnseignant() {
        return enseignant;
    }

    public void setEnseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
    }

    public Cours getCours() {
        return cours;
    }

    public void setCours(Cours cours) {
        this.cours = cours;
    }

    public JourSemaine getJourSemaine() {
        return jourSemaine;
    }

    public void setJourSemaine(JourSemaine jourSemaine) {
        this.jourSemaine = jourSemaine;
    }

    public LocalTime getHeureDebut() {
        return dateHeureDebut;
    }

    public void setHeureDebut(LocalTime heureDebut) {
        this.dateHeureDebut = heureDebut;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public String getSalle() {
        return salle; // Getter pour la salle
    }

    public void setSalle(String salle) {
        this.salle = salle; // Setter pour la salle
    }
}

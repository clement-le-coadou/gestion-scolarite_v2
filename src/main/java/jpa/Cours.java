package jpa;
import jakarta.persistence.*;
import java.util.Set;

@Entity
public class Cours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "enseignant_id")
    private Enseignant enseignant;

    @OneToMany(mappedBy = "cours", cascade = CascadeType.ALL)
    private Set<Inscription> inscriptions;

    @OneToMany(mappedBy = "cours", cascade = CascadeType.ALL)
    private Set<Note> notes;

  
        // Constructeur par défaut
        public Cours() {
        }

        // Constructeur avec paramètres
        public Cours(String nom, String description, Enseignant enseignant) {
            this.nom = nom;
            this.description = description;
            this.enseignant = enseignant;
        }

    
    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Enseignant getEnseignant() { return enseignant; }
    public void setEnseignant(Enseignant enseignant) { this.enseignant = enseignant; }

    public Set<Inscription> getInscriptions() { return inscriptions; }
    public void setInscriptions(Set<Inscription> inscriptions) { this.inscriptions = inscriptions; }

    public Set<Note> getNotes() { return notes; }
    public void setNotes(Set<Note> notes) { this.notes = notes; }
}

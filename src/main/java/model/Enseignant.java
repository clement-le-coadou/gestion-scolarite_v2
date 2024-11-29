package model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class Enseignant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;

    @Column(nullable = false, unique = true)
    private String email;

    private String contact;

    @Column(name = "mot_de_passe", nullable = false)
    private String motDePasse;

    @OneToMany(mappedBy = "enseignant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Cours> coursEnseignes;

    // Default constructor
    public Enseignant() {
    }

    // Constructor with parameters
    public Enseignant(String nom, String prenom, String email, String contact, String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.contact = contact;
        this.motDePasse = motDePasse;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public Set<Cours> getCoursEnseignes() {
        return coursEnseignes;
    }

    public void setCoursEnseignes(Set<Cours> coursEnseignes) {
        this.coursEnseignes = coursEnseignes;
    }
}

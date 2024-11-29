package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Administrateur;
import model.Enseignant;
import model.Etudiant;

import java.io.IOException;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import daogenerique.CrudGeneric;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Login {

    private SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    @PostMapping("/login")
    public String login(@RequestParam("user_type") String userType,
                        @RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session, Model model) {

        CrudGeneric<?> user;
        
        if (userType != null) {
            if (userType.equals("etudiant")) {
                user = new CrudGeneric<>(sessionFactory, Etudiant.class);
                Etudiant etudiant = (Etudiant) user.findByEmail(username);
                if (etudiant != null) {
                    if (etudiant.getMotDePasse().equals(password)) {
                        session.setAttribute("username", etudiant);
                        session.setAttribute("role", "Etudiant");
                    } else {
                        model.addAttribute("errorMessage", "Mot de passe incorrect");
                        return "Connexion"; // Affiche la page de connexion avec un message d'erreur
                    }
                } else {
                    model.addAttribute("errorMessage", "Adresse mail incorrecte");
                    return "Connexion"; // Affiche la page de connexion avec un message d'erreur
                }
            } else if (userType.equals("administrateur")) {
                user = new CrudGeneric<>(sessionFactory, Administrateur.class);
                Administrateur administrateur = (Administrateur) user.findByEmail(username);
                if (administrateur != null) {
                    if (administrateur.getMotDePasse().equals(password)) {
                        session.setAttribute("username", administrateur);
                        session.setAttribute("role", "Administrateur");
                    } else {
                        model.addAttribute("errorMessage", "Mot de passe incorrect");
                        return "Connexion"; // Affiche la page de connexion avec un message d'erreur
                    }
                } else {
                    model.addAttribute("errorMessage", "Adresse mail incorrecte");
                    return "Connexion"; // Affiche la page de connexion avec un message d'erreur
                }
            } else if (userType.equals("enseignant")) {
                user = new CrudGeneric<>(sessionFactory, Enseignant.class);
                Enseignant enseignant = (Enseignant) user.findByEmail(username);
                if (enseignant != null) {
                    if (enseignant.getMotDePasse().equals(password)) {
                        session.setAttribute("username", enseignant);
                        session.setAttribute("role", "Enseignant");
                    } else {
                        model.addAttribute("errorMessage", "Mot de passe incorrect");
                        return "Connexion"; // Affiche la page de connexion avec un message d'erreur
                    }
                } else {
                    model.addAttribute("errorMessage", "Adresse mail incorrecte");
                    return "Connexion"; // Affiche la page de connexion avec un message d'erreur
                }
            }
        } else {
            model.addAttribute("errorMessage", "Veuillez sélectionner le type de votre compte");
            return "Connexion"; // Affiche la page de connexion avec un message d'erreur
        }

        return "redirect:/accueil"; // Si l'authentification est réussie, redirige vers la page d'accueil
    }
}

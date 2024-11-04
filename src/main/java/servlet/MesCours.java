package servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jpa.Cours;
import jpa.Etudiant;
import jpa.Enseignant;
import jpa.Inscription;
import daogenerique.CrudGeneric;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/MesCours")
public class MesCours extends HttpServlet {
    private SessionFactory sessionFactory;

    @Override
    public void init() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Object utilisateur = session.getAttribute("username");
        
        CrudGeneric<Cours> coursDAO = new CrudGeneric<>(sessionFactory, Cours.class);

        List<Cours> coursList = null;

        if (utilisateur instanceof Etudiant) {
            Etudiant etudiant = (Etudiant) utilisateur;

            CrudGeneric<Inscription> inscriptionDAO = new CrudGeneric<>(sessionFactory, Inscription.class);
            List<Inscription> inscriptions = inscriptionDAO.findAll()
                    .stream()
                    .filter(inscription -> inscription.getEtudiant().getId().equals(etudiant.getId()))
                    .collect(Collectors.toList());

            coursList = inscriptions.stream()
                    .map(Inscription::getCours)
                    .distinct()
                    .collect(Collectors.toList());

        } else if (utilisateur instanceof Enseignant) {
            Enseignant enseignant = (Enseignant) utilisateur;

            coursList = coursDAO.findAll()
                    .stream()
                    .filter(cours -> cours.getEnseignant() != null && cours.getEnseignant().getId().equals(enseignant.getId()))
                    .collect(Collectors.toList());
        }

        request.setAttribute("coursList", coursList);

        RequestDispatcher dispatcher = request.getRequestDispatcher("AfficherCours.jsp");
        dispatcher.forward(request, response);
    }

 
}

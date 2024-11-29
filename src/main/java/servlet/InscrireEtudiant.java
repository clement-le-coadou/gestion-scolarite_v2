package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import daogenerique.CrudGeneric;
import email.EmailUtil;
import jpa.Etudiant;
import jpa.Cours;
import jpa.Inscription;

import java.io.IOException;

@WebServlet("/InscrireEtudiant")
public class InscrireEtudiant extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long etudiantId = Long.parseLong(request.getParameter("etudiantId"));
        Long coursId = Long.parseLong(request.getParameter("coursId"));

        CrudGeneric<Etudiant> etudiantDAO = new CrudGeneric<>(sessionFactory, Etudiant.class);
        CrudGeneric<Cours> coursDAO = new CrudGeneric<>(sessionFactory, Cours.class);

        // Récupérer l'étudiant et le cours
        Etudiant etudiant = etudiantDAO.read(etudiantId);
        Cours cours = coursDAO.read(coursId);

        // Créer une nouvelle inscription
        Inscription inscription = new Inscription();
        inscription.setEtudiant(etudiant);
        inscription.setCours(cours);

        // Enregistrer l'inscription
        CrudGeneric<Inscription> inscriptionDAO = new CrudGeneric<>(sessionFactory, Inscription.class);
        inscriptionDAO.create(inscription);
        
        // Envoi d'un email apr�s la modification de la note
        String destinataire = inscription.getEtudiant().getEmail(); // Assurez-vous que l'objet Etudiant contient l'email
        String sujet = "Inscription au cours";
        String contenu = "Bonjour " + inscription.getEtudiant().getPrenom() + " " + inscription.getEtudiant().getNom() +
                         ",\n\nVotre inscription pour le cours " + inscription.getCours().getNom() + " a été validé.\n" +
                         "\n\nCordialement,\nL'équipe de gestion des études.";

        EmailUtil.envoyerEmail(destinataire, sujet, contenu);
        
        
        // Rediriger vers la page de confirmation ou liste des cours
        response.sendRedirect("AfficherCours?page=gestion");
    }
}

package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;

import jpa.Note;
import jpa.Etudiant;
import jpa.Cours;
import daogenerique.CrudGeneric;
import email.EmailUtil;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
@WebServlet("/AjouterNote")
public class AjouterNote extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CrudGeneric<Note> noteDAO;
    private CrudGeneric<Cours> coursDAO;
    private CrudGeneric<Etudiant> etudiantDAO;

    @Override
    public void init() throws ServletException {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        noteDAO = new CrudGeneric<>(sessionFactory, Note.class);
        coursDAO = new CrudGeneric<>(sessionFactory, Cours.class);
        etudiantDAO = new CrudGeneric<>(sessionFactory, Etudiant.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Récupération des paramètres de la requête
            int idEtudiant = Integer.parseInt(request.getParameter("idEtudiant"));
            int coursId = Integer.parseInt(request.getParameter("coursId"));

            // Récupérer les informations sur l'étudiant et le cours pour les afficher dans le formulaire
            Etudiant etudiant = etudiantDAO.findById(idEtudiant);
            Cours cours = coursDAO.findById(coursId);

            if (etudiant != null && cours != null) {
                // Mettre les données nécessaires dans la requête
                request.setAttribute("etudiant", etudiant);
                request.setAttribute("cours", cours);
                request.setAttribute("coursId", coursId);
                // Rediriger vers la page JSP du formulaire
                RequestDispatcher dispatcher = request.getRequestDispatcher("AjouterNote.jsp");
                dispatcher.forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Étudiant ou cours introuvable.");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Données invalides.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int idEtudiant = Integer.parseInt(request.getParameter("idEtudiant"));
            int coursId = Integer.parseInt(request.getParameter("coursId"));
            double noteValue = Double.parseDouble(request.getParameter("note"));

            // Récupération de l'étudiant et du cours
            Etudiant etudiant = etudiantDAO.findById(idEtudiant);
            Cours cours = coursDAO.findById(coursId);

            if (etudiant != null && cours != null) {
                // Création d'une nouvelle note
                Note newNote = new Note();
                newNote.setEtudiant(etudiant);
                newNote.setCours(cours);
                newNote.setNote(noteValue);

                // Sauvegarde de la note
                noteDAO.create(newNote);
                
             // Envoi d'un email apr�s l'ajout de la note
                String destinataire = etudiant.getEmail(); // Assurez-vous que l'objet Etudiant contient l'email
                String sujet = "Nouvelle note ajout�e";
                String contenu = "Bonjour " + etudiant.getPrenom() + " " + etudiant.getNom() +
                                 ",\n\nUne nouvelle note a �t� ajout�e pour le cours " + cours.getNom() + ".\n" +
                                 "Note : " + noteValue + "\n\nCordialement,\nL'�quipe de gestion des �tudes.";

                EmailUtil.envoyerEmail(destinataire, sujet, contenu);


                // Redirection après l'ajout
                response.sendRedirect("GestionNotes?coursId=" + coursId);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Étudiant ou cours introuvable.");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Données invalides.");
        }
    }
}

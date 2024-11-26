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
import jpa.Inscription;
import jpa.Enseignant;
import jpa.Note;


import dao.CoursDAO;
import dao.NoteDAO;
import daogenerique.CrudGeneric;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@WebServlet("/RedirectionNotesServlet")
public class RedirectionNotesServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SessionFactory sessionFactory;

    @Override
    public void init() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        System.out.println(role);
        if(role == null) {
        	response.sendRedirect("Connexion.jsp");
        	return;
        }
        Object utilisateur = session.getAttribute("username");
        CrudGeneric<Note> noteDAO = new CrudGeneric<>(sessionFactory, Note.class);
        CrudGeneric<Cours> coursDAO = new CrudGeneric<>(sessionFactory, Cours.class);
        if (role.equals("Enseignant")) {
        	Enseignant enseignant = (Enseignant) utilisateur;

            List<Cours> coursList = coursDAO.findAll()
                    .stream()
                    .filter(cours -> cours.getEnseignant() != null && cours.getEnseignant().getId().equals(enseignant.getId()))
                    .collect(Collectors.toList());
            request.setAttribute("coursList", coursList);
            request.getRequestDispatcher("GestionNotes.jsp").forward(request, response);
        } else if (role.equals("Etudiant")) {
            Etudiant etudiant = (Etudiant) utilisateur;

            CrudGeneric<Inscription> inscriptionDAO = new CrudGeneric<>(sessionFactory, Inscription.class);
            List<Inscription> inscriptions = inscriptionDAO.findAll()
                    .stream()
                    .filter(inscription -> inscription.getEtudiant().getId().equals(etudiant.getId()))
                    .collect(Collectors.toList());

            List<Cours> coursList = inscriptions.stream()
                    .map(Inscription::getCours)
                    .distinct()
                    .collect(Collectors.toList());
            
            List<Note> notesList = noteDAO.findAll();
                   
            request.setAttribute("coursList", coursList);
            request.setAttribute("notesList", notesList);
            request.getRequestDispatcher("AfficherNotes.jsp").forward(request, response);
        } else {
            response.sendRedirect("Connexion.jsp");
        }
    }
}

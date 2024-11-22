package servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import jpa.Note;
import jpa.Cours;
import jpa.Etudiant;
import jpa.Inscription;
import dao.NoteDAO;
import daogenerique.CrudGeneric;
import dao.CoursDAO;
import dao.EtudiantDAO;
@WebServlet("/GestionNotes")
public class GestionNotes extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CrudGeneric<Inscription> inscriptionDAO;
	private CrudGeneric<Note> noteDAO;
    @Override
    public void init() throws ServletException {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        inscriptionDAO = new CrudGeneric<>(sessionFactory, Inscription.class);
        noteDAO = new CrudGeneric<>(sessionFactory, Note.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Redirige les appels GET vers doPost pour réutiliser la logique
        doPost(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String coursIdParam = request.getParameter("coursId");

        if (coursIdParam != null && !coursIdParam.isEmpty()) {
            try {
                int coursId = Integer.parseInt(coursIdParam);

                // Récupérer toutes les inscriptions et filtrer celles pour le cours donné
                List<Inscription> inscriptions = inscriptionDAO.findAll();
                List<Etudiant> etudiantList = inscriptions.stream()
                        .filter(inscription -> inscription.getCours().getId() == coursId)
                        .map(Inscription::getEtudiant)
                        .collect(Collectors.toList());
                List<Note> notes = new ArrayList<>();
                
                
                for (Etudiant etudiant : etudiantList) {
                    notes = noteDAO.findAll().stream()
                            .filter(note -> note.getEtudiant().getId().equals(etudiant.getId()) 
                                          && note.getCours().getId() == coursId) // Ajouter la condition sur le cours
                            .toList();
                }
                
                
                request.setAttribute("notes", notes);
                request.setAttribute("etudiantList", etudiantList);
                request.setAttribute("coursId", coursId);
            
                request.getRequestDispatcher("AfficherNoteEnseignant.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de cours invalide.");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Aucun cours sélectionné.");
        }
    }
}

package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jpa.Note;

import java.io.IOException;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import daogenerique.CrudGeneric;

/**
 * Servlet implementation class ModifierNotes
 */
@WebServlet("/ModifierNote")
public class ModifierNote extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CrudGeneric<Note> noteDAO;

    @Override
    public void init() throws ServletException {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        noteDAO = new CrudGeneric<>(sessionFactory, Note.class);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idNote = Integer.parseInt(request.getParameter("idNote"));
        Note note = noteDAO.findById(idNote);

        if (note != null) {
            request.setAttribute("note", note);
            request.getRequestDispatcher("ModifierNote.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Note introuvable.");
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idNote = Integer.parseInt(request.getParameter("idNote"));
        double newNoteValue = Double.parseDouble(request.getParameter("note"));

        Note note = noteDAO.findById(idNote);
        if (note != null) {
            note.setNote(newNoteValue);
            noteDAO.update(note);
        }

        response.sendRedirect("GestionNotes?coursId=" + note.getCours().getId());
    }
}

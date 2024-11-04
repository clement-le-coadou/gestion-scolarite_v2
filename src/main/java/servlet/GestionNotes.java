package servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jpa.Note;
import daogenerique.CrudGeneric;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;

@WebServlet("/GestionNotes")
public class GestionNotes extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long noteId = Long.valueOf(request.getParameter("id"));
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        CrudGeneric<Note> noteDAO = new CrudGeneric<>(sessionFactory, Note.class);
        Note note = noteDAO.read(noteId);

        request.setAttribute("note", note);
        RequestDispatcher dispatcher = request.getRequestDispatcher("ModifierNote.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long noteId = Long.valueOf(request.getParameter("id"));
        Double nouvelleNote = Double.valueOf(request.getParameter("note"));

        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        CrudGeneric<Note> noteDAO = new CrudGeneric<>(sessionFactory, Note.class);
        Note note = noteDAO.read(noteId);
        note.setNote(nouvelleNote);

        noteDAO.update(note);
        
        
        request.setAttribute("noteList", noteDAO.findAll());
        
        response.sendRedirect("GestionNotes.jsp");
    }
}

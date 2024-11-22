package servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jpa.Enseignant;
import jpa.Etudiant;

import java.io.IOException;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import daogenerique.CrudGeneric;

/**
 * Servlet implementation class SupprimerEnseignant
 */
@WebServlet("/SupprimerEnseignant")
public class SupprimerEnseignant extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
	private CrudGeneric<Enseignant> enseignantDAO = new CrudGeneric<>(sessionFactory, Enseignant.class);   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SupprimerEnseignant() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long id = Long.parseLong(request.getParameter("id"));
        
        Enseignant enseignant = enseignantDAO.read(id);
        if (enseignant != null) {
        	enseignantDAO.delete(enseignant);
        }
        response.sendRedirect("AfficherEnseignants");
	}

}

package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jpa.Cours;
import jpa.Etudiant;

import java.io.IOException;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import dao.EtudiantDAO;
import daogenerique.CrudGeneric;
import daogenerique.GenericDAO;

/**
 * Servlet implementation class SupprimerEtudiant
 */
@WebServlet("/SupprimerEtudiant")
public class SupprimerEtudiant extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
	private CrudGeneric<Etudiant> etudiantDAO = new CrudGeneric<>(sessionFactory, Etudiant.class);
    
	/**
     * @see HttpServlet#HttpServlet()
     */
    public SupprimerEtudiant() {
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
        
        Etudiant etudiant = etudiantDAO.read(id);
        if (etudiant != null) {
        	etudiantDAO.delete(etudiant);
        }

        response.sendRedirect("AfficherEtudiants");
	}

}

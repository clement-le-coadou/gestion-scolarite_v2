package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jpa.Administrateur;
import jpa.Enseignant;
import jpa.Etudiant;

import java.io.IOException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import dao.EtudiantDAO;
import daogenerique.CrudGeneric;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user_type=request.getParameter("user_type");
		String username = request.getParameter("username");
	    String password = request.getParameter("password");
		
	    SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
	    HttpSession session = request.getSession();
	    CrudGeneric<?> user;
	    
	    if(user_type!=null) {
	    	if(user_type.equals("etudiant")) {
				user = new CrudGeneric<>(sessionFactory, Etudiant.class);
				Etudiant test= new Etudiant();
				test=(Etudiant)user.findByEmail(username);	
				if(test!=null) {
					if(test.getMotDePasse().equals(password)) {
						session.setAttribute("username", test);
					}else{
						session.setAttribute("errorMessage", "Mot de passe incorrect");
						request.getRequestDispatcher("Connexion.jsp").forward(request, response);
						return;
					}
				}else{
					session.setAttribute("errorMessage", "Adresse mail incorrecte");
					request.getRequestDispatcher("Connexion.jsp").forward(request, response);
					return;
				}
			}else if(user_type.equals("administrateur")) {
				user = new CrudGeneric<>(sessionFactory, Administrateur.class);
				Administrateur test= new Administrateur();
				test=(Administrateur)user.findByEmail(username);
				if(test!=null) {
					if(test.getMotDePasse().equals(password)) {
						session.setAttribute("username", test);
					}else{
						session.setAttribute("errorMessage", "Mot de passe incorrect");
						request.getRequestDispatcher("Connexion.jsp").forward(request, response);
						return;
					}
				}else{
					session.setAttribute("errorMessage", "Adresse mail incorrecte");
					request.getRequestDispatcher("Connexion.jsp").forward(request, response);
					return;
				}
			}else if(user_type.equals("enseignant")) {
				user = new CrudGeneric<>(sessionFactory, Enseignant.class);
				Enseignant test= new Enseignant();
				test=(Enseignant)user.findByEmail(username);
				if(test!=null) {
					if(test.getMotDePasse().equals(password)) {
						session.setAttribute("username", test);
					}else{
						session.setAttribute("errorMessage", "Mot de passe incorrect");
						request.getRequestDispatcher("Connexion.jsp").forward(request, response);
						return;
					}
				}else{
					session.setAttribute("errorMessage", "Adresse mail incorrecte");
					request.getRequestDispatcher("Connexion.jsp").forward(request, response);
					return;
				}
			}
	    }else {
	    	session.setAttribute("errorMessage", "Veuillez sélectionner le type de votre compte");
			request.getRequestDispatcher("Connexion.jsp").forward(request, response);
			return;
	    }
	    
        request.getRequestDispatcher("accueil.jsp");
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

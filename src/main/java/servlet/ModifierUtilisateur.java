package servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jpa.Etudiant;
import jpa.Enseignant;
import jpa.Administrateur;
import daogenerique.CrudGeneric;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@WebServlet("/ModifierUtilisateur")
public class ModifierUtilisateur extends HttpServlet {
    private SessionFactory sessionFactory;

    @Override
    public void init() throws ServletException {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = request.getParameter("userType");
        Long userId = Long.valueOf(request.getParameter("id"));

        Object user = null;
        if ("etudiant".equalsIgnoreCase(type)) {
            CrudGeneric<Etudiant> etudiantDAO = new CrudGeneric<>(sessionFactory, Etudiant.class);
            user = etudiantDAO.read(userId);
        } else if ("enseignant".equalsIgnoreCase(type)) {
            CrudGeneric<Enseignant> enseignantDAO = new CrudGeneric<>(sessionFactory, Enseignant.class);
            user = enseignantDAO.read(userId);
        } else if ("administrateur".equalsIgnoreCase(type)) {
            CrudGeneric<Administrateur> adminDAO = new CrudGeneric<>(sessionFactory, Administrateur.class);
            user = adminDAO.read(userId);
        }

        request.setAttribute("user", user);
        request.setAttribute("userType", type);

        RequestDispatcher dispatcher = request.getRequestDispatcher("AfficherInfos.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	System.out.println("debug");
        String type = request.getParameter("userType");
        System.out.println(type);
        Long userId = Long.valueOf(request.getParameter("id"));
        System.out.println(userId);
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");

        if ("etudiant".equalsIgnoreCase(type)) {
            updateEtudiant(request, userId, nom, prenom, email);
        } else if ("enseignant".equalsIgnoreCase(type)) {
            updateEnseignant(request, userId, nom, prenom, email);
        } else if ("administrateur".equalsIgnoreCase(type)) {
            updateAdministrateur(request, userId, nom, prenom, email);
        }
        response.sendRedirect("AfficherInfos.jsp");
    }

    private void updateEtudiant(HttpServletRequest request, Long id, String nom, String prenom, String email) {
	    HttpSession session = request.getSession();
    	System.out.println("etudiant");
        CrudGeneric<Etudiant> etudiantDAO = new CrudGeneric<>(sessionFactory, Etudiant.class);
        Etudiant etudiant = etudiantDAO.read(id);

        etudiant.setNom(nom);
        etudiant.setPrenom(prenom);
        etudiant.setEmail(email);
        System.out.println(etudiant.getNom());
        System.out.println(etudiant.getPrenom());
        System.out.println(etudiant.getEmail());
        System.out.println(etudiant.getContact());
        System.out.println(etudiant.getMotDePasse());
        // Parse date of birth for Etudiant
        String dateNaissanceString = request.getParameter("dateNaissance");
        if (dateNaissanceString != null && !dateNaissanceString.isEmpty()) {
            	 LocalDate dateNaissance = LocalDate.parse(dateNaissanceString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                 etudiant.setDateNaissance(java.sql.Date.valueOf(dateNaissance));
        }
        
       

        // Set contact for Etudiant
        String contact = request.getParameter("contact");
        etudiant.setContact(contact);

        etudiantDAO.update(etudiant);
        session.setAttribute("username", etudiant);
    }

    private void updateEnseignant(HttpServletRequest request, Long id, String nom, String prenom, String email) {
	    HttpSession session = request.getSession();
    	System.out.println("enseignant");
        CrudGeneric<Enseignant> enseignantDAO = new CrudGeneric<>(sessionFactory, Enseignant.class);
        Enseignant enseignant = enseignantDAO.read(id);

        enseignant.setNom(nom);
        enseignant.setPrenom(prenom);
        enseignant.setEmail(email);

        // Set contact for Enseignant
        String contact = request.getParameter("contact");
        enseignant.setContact(contact);

        enseignantDAO.update(enseignant);
        session.setAttribute("username", enseignant);
    }

    private void updateAdministrateur(HttpServletRequest request, Long id, String nom, String prenom, String email) {
	    HttpSession session = request.getSession();
    	System.out.println("admin");
        CrudGeneric<Administrateur> adminDAO = new CrudGeneric<>(sessionFactory, Administrateur.class);
        Administrateur admin = adminDAO.read(id);

        admin.setNom(nom);
        admin.setPrenom(prenom);
        admin.setEmail(email);

        // Administrateur does not have contact or date of birth

        adminDAO.update(admin);
        session.setAttribute("username", admin);
    }

    @Override
    public void destroy() {
        sessionFactory.close();
    }
}

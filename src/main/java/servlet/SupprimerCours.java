package servlet;

import daogenerique.CrudGeneric;
import jpa.Cours;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


@WebServlet("/SupprimerCours")
public class SupprimerCours extends HttpServlet {

    private SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    private CrudGeneric<Cours> coursDAO = new CrudGeneric<>(sessionFactory, Cours.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        
        // Récupérer le cours par ID
        Cours cours = coursDAO.read(id);
        if (cours != null) {
            coursDAO.delete(cours);
        }

        response.sendRedirect("GestionCours.jsp");
    }
}

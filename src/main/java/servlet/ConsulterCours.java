package servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jpa.Cours;
import jpa.Etudiant;
import jpa.Inscription;
import daogenerique.CrudGeneric;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/ConsulterCours")
public class ConsulterCours extends HttpServlet {
    private SessionFactory sessionFactory;

    @Override
    public void init() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupération du paramètre id depuis l'URL
        String idParam = request.getParameter("id");
        Long etudiantId = null;

        try {
            // Conversion du paramètre en Long (id de l'étudiant)
            if (idParam != null) {
                etudiantId = Long.valueOf(idParam);
            }
        } catch (NumberFormatException e) {
            // Si l'id est invalide, renvoyer une erreur ou une page par défaut
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de l'étudiant invalide");
            return;
        }

        // Récupérer l'étudiant avec l'id donné
        CrudGeneric<Etudiant> etudiantDAO = new CrudGeneric<>(sessionFactory, Etudiant.class);
        Etudiant etudiant = etudiantDAO.findById(etudiantId.intValue());

        if (etudiant == null) {
            // Si l'étudiant n'existe pas, renvoyer une erreur ou une page par défaut
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Étudiant non trouvé");
            return;
        }

        // Récupérer la liste des inscriptions de l'étudiant
        CrudGeneric<Inscription> inscriptionDAO = new CrudGeneric<>(sessionFactory, Inscription.class);
        List<Inscription> inscriptions = inscriptionDAO.findAll()
                .stream()
                .filter(inscription -> inscription.getEtudiant().getId().equals(etudiant.getId()))
                .collect(Collectors.toList());

        // Récupérer les cours associés à l'étudiant
        List<Cours> coursList = inscriptions.stream()
                .map(Inscription::getCours)
                .distinct()
                .collect(Collectors.toList());

        // Passer la liste des cours à la JSP
        request.setAttribute("coursList", coursList);

        // Rediriger vers la page JSP "ConsulterCours.jsp"
        RequestDispatcher dispatcher = request.getRequestDispatcher("ConsulterCours.jsp");
        dispatcher.forward(request, response);
    }
}

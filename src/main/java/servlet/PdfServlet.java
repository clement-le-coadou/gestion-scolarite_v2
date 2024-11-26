package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.Collectors;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.element.Cell;

import daogenerique.CrudGeneric;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import jpa.Cours;
import jpa.Etudiant;
import jpa.Inscription;
import jpa.Note;

@WebServlet("/generatePdf")
public class PdfServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CrudGeneric<Note> noteDAO;
    private CrudGeneric<Cours> coursDAO;
    private CrudGeneric<Inscription> inscriptionDAO;

    public PdfServlet() {
        super();
    }

    public void init() throws ServletException {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        inscriptionDAO = new CrudGeneric<>(sessionFactory, Inscription.class);
        noteDAO = new CrudGeneric<>(sessionFactory, Note.class);
        coursDAO = new CrudGeneric<>(sessionFactory, Cours.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Etudiant etudiant = (Etudiant) session.getAttribute("username");

        List<Note> notesList = noteDAO.findAll();
        List<Inscription> inscriptions = inscriptionDAO.findAll()
                .stream()
                .filter(inscription -> inscription.getEtudiant().getId().equals(etudiant.getId()))
                .collect(Collectors.toList());

        List<Cours> coursList = inscriptions.stream()
                .map(Inscription::getCours)
                .distinct()
                .collect(Collectors.toList());

        if (coursList == null || notesList == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Les données sont introuvables.");
            return;
        }

        response.setContentType("application/pdf");
        OutputStream outputStream = response.getOutputStream();
        
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        // En-tête du document
        document.add(new Paragraph("Relevé de Notes")
                .setBold()
                .setFontSize(18)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(10));

        // Table des notes (sans la date d'inscription)
        Table table = new Table(2); // 2 colonnes : Cours et Note
        table.addHeaderCell(new Cell().add(new Paragraph("Cours")).setBold().setTextAlignment(TextAlignment.CENTER));
        table.addHeaderCell(new Cell().add(new Paragraph("Note")).setBold().setTextAlignment(TextAlignment.CENTER));

        // Remplissage de la table avec les données
        for (Cours cours : coursList) {
            Note note = notesList.stream().filter(n -> n.getCours().getId().equals(cours.getId())).findFirst().orElse(null);
            String noteValue = (note != null) ? String.valueOf(note.getNote()) : "Pas de note";

            table.addCell(new Cell().add(new Paragraph(cours.getNom())).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell().add(new Paragraph(noteValue)).setTextAlignment(TextAlignment.CENTER));
        }

        // Ajouter la table au document
        document.add(table);

        // Ne pas ajouter de footer avec la date
        document.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

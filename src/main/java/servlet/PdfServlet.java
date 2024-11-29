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

import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;

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
    private CrudGeneric<Inscription> inscriptionDAO;

    public PdfServlet() {
        super();
    }

    public void init() throws ServletException {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        inscriptionDAO = new CrudGeneric<>(sessionFactory, Inscription.class);
        noteDAO = new CrudGeneric<>(sessionFactory, Note.class);
        new CrudGeneric<>(sessionFactory, Cours.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Etudiant etudiant = (Etudiant) session.getAttribute("username");

        List<Note> notesList = noteDAO.findAll().stream()
                .filter(inscription -> inscription.getEtudiant().getId().equals(etudiant.getId()))
                .collect(Collectors.toList());
        List<Inscription> inscriptions = inscriptionDAO.findAll()
                .stream()
                .filter(inscription -> inscription.getEtudiant().getId().equals(etudiant.getId()))
                .collect(Collectors.toList());

        List<Cours> coursList = inscriptions.stream()
                .map(Inscription::getCours)
                .distinct()
                .collect(Collectors.toList());

        if (coursList == null || notesList == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Les donn�es sont introuvables.");
            return;
        }

        // D�finir le type de contenu comme PDF
        response.setContentType("application/pdf");
        OutputStream outputStream = response.getOutputStream();
        
        // Cr�er un PdfWriter et un PdfDocument
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        // Charger les polices (polices de base et personnalis�es)
        PdfFont fontBold = PdfFontFactory.createFont("Helvetica-Bold");
        PdfFont fontRegular = PdfFontFactory.createFont("Helvetica");

        // Titre du document (header) avec style et couleur
        Paragraph header = new Paragraph("Relev� de Notes - CY Tech")
                .setFont(fontBold)
                .setFontSize(22)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20)
                .setBackgroundColor(new com.itextpdf.kernel.colors.DeviceRgb(0, 123, 255))  // bleu
                .setFontColor(ColorConstants.WHITE); // texte blanc
        document.add(header);

        // Informations de l'�tudiant
        Paragraph studentInfo = new Paragraph("Nom : " + etudiant.getNom() + "\n" +
                                              "Pr�nom : " + etudiant.getPrenom() + "\n" +
                                              "Email : " + etudiant.getEmail())
                .setFont(fontRegular)
                .setFontSize(12)
                .setTextAlignment(TextAlignment.LEFT)
                .setMarginBottom(20);
        document.add(studentInfo);

        // Cr�er une table pour les r�sultats
        Table table = new Table(2);  // 2 colonnes : "Cours" et "Note"
        table.setWidth(UnitValue.createPercentValue(100));  // Table pleine largeur

        // Ajouter les en-t�tes de table avec une couleur de fond et un texte centr�
        table.addHeaderCell(new Cell().add(new Paragraph("Cours"))
                .setBackgroundColor(new com.itextpdf.kernel.colors.DeviceRgb(0, 123, 255))  // bleu
                .setTextAlignment(TextAlignment.CENTER)
                .setFont(fontBold)
                .setFontColor(ColorConstants.WHITE));
        table.addHeaderCell(new Cell().add(new Paragraph("Note"))
                .setBackgroundColor(new com.itextpdf.kernel.colors.DeviceRgb(0, 123, 255))  // bleu
                .setTextAlignment(TextAlignment.CENTER)
                .setFont(fontBold)
                .setFontColor(ColorConstants.WHITE));

        // Remplir la table avec les donn�es
        for (Cours cours : coursList) {
            Note note = notesList.stream().filter(n -> n.getCours().getId().equals(cours.getId())).findFirst().orElse(null);
            String noteValue = (note != null) ? String.valueOf(note.getNote()) : "Pas de note";

            // Ajouter chaque ligne dans la table
            table.addCell(new Cell().add(new Paragraph(cours.getNom()))
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFont(fontRegular)
                    .setPadding(8));
            table.addCell(new Cell().add(new Paragraph(noteValue))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFont(fontRegular)
                    .setPadding(8)
                    .setBackgroundColor(ColorConstants.LIGHT_GRAY));  // Fond gris clair pour les notes
        }

        // Ajouter la table au document
        document.add(table);

        // Ajouter une section de pied de page pour l'email ou des informations suppl�mentaires si n�cessaire
        Paragraph footer = new Paragraph("Pour plus d'informations, contactez nous � : contact@cytech.com")
                .setTextAlignment(TextAlignment.CENTER)
                .setFont(fontRegular)
                .setFontSize(10)
                .setMarginTop(30)
                .setFontColor(new com.itextpdf.kernel.colors.DeviceRgb(0, 123, 255));  // bleu
        document.add(footer);

        // Fermer le document PDF
        document.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

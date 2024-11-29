package mainApp.servlet;

import mainApp.service.PdfService;
import mainApp.model.Etudiant;
import mainApp.model.Note;
import mainApp.model.Cours;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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

import java.io.IOException;
import java.util.List;

@Controller
public class PdfServlet {

    @Autowired
    private PdfService pdfService;

    @GetMapping("/generatePdf")
    public void generatePdf(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Retrieve Etudiant from session
    	HttpSession session = request.getSession();
    	Etudiant etudiant;
    	if(session.getAttribute("role")!=null && session.getAttribute("role").equals("Etudiant")) {
    		etudiant = (Etudiant) session.getAttribute("username");
    	}
    	else{
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Utilisateur non authentifié.");
    		return;
    	}


        // If Etudiant is not found in session, send error
        if (etudiant == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Utilisateur non authentifié.");
            return;
        }

        // Fetch data for the Etudiant
        List<Cours> coursList = pdfService.getCoursForEtudiant(etudiant.getId());
        List<Note> notesList = pdfService.getNotesForEtudiant(etudiant.getId());

        // Handle case if no courses or notes are found
        if (coursList == null || notesList == null || coursList.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Les données sont introuvables.");
            return;
        }

        // Set content type for PDF
        response.setContentType("application/pdf");

        // Create OutputStream for the response PDF
        ServletOutputStream outputStream = response.getOutputStream();
        
        // Create PdfWriter and PdfDocument
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        // Load fonts
        PdfFont fontBold = PdfFontFactory.createFont("Helvetica-Bold");
        PdfFont fontRegular = PdfFontFactory.createFont("Helvetica");

        // Title (Header) with styling
        Paragraph header = new Paragraph("Relevé de Notes - CY Tech")
                .setFont(fontBold)
                .setFontSize(22)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20)
                .setBackgroundColor(new com.itextpdf.kernel.colors.DeviceRgb(0, 123, 255))  // Blue color
                .setFontColor(ColorConstants.WHITE);
        document.add(header);

        // Add student information (name, email, etc.)
        Paragraph studentInfo = new Paragraph("Nom : " + etudiant.getNom() + "\n" +
                                              "Prénom : " + etudiant.getPrenom() + "\n" +
                                              "Email : " + etudiant.getEmail())
                .setFont(fontRegular)
                .setFontSize(12)
                .setTextAlignment(TextAlignment.LEFT)
                .setMarginBottom(20);
        document.add(studentInfo);

        // Create a table for results (Cours and Notes)
        Table table = new Table(2);  // 2 columns: "Cours" and "Note"
        table.setWidth(UnitValue.createPercentValue(100));  // Full width table

        // Add table headers with background color and centered text
        table.addHeaderCell(new Cell().add(new Paragraph("Cours"))
                .setBackgroundColor(new com.itextpdf.kernel.colors.DeviceRgb(0, 123, 255))  // Blue color
                .setTextAlignment(TextAlignment.CENTER)
                .setFont(fontBold)
                .setFontColor(ColorConstants.WHITE));
        table.addHeaderCell(new Cell().add(new Paragraph("Note"))
                .setBackgroundColor(new com.itextpdf.kernel.colors.DeviceRgb(0, 123, 255))  // Blue color
                .setTextAlignment(TextAlignment.CENTER)
                .setFont(fontBold)
                .setFontColor(ColorConstants.WHITE));

        // Populate table with data
        for (Cours cours : coursList) {
            Note note = notesList.stream()
                    .filter(n -> n.getCours().getId().equals(cours.getId()))
                    .findFirst()
                    .orElse(null);
            String noteValue = (note != null) ? String.valueOf(note.getNote()) : "Pas de note";

            // Add each row to the table
            table.addCell(new Cell().add(new Paragraph(cours.getNom()))
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFont(fontRegular)
                    .setPadding(8));
            table.addCell(new Cell().add(new Paragraph(noteValue))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFont(fontRegular)
                    .setPadding(8)
                    .setBackgroundColor(ColorConstants.LIGHT_GRAY));  // Light gray background for notes
        }

        // Add table to document
        document.add(table);

        // Add footer (email or additional info)
        Paragraph footer = new Paragraph("Pour plus d'informations, contactez nous à : contact@cytech.com")
                .setTextAlignment(TextAlignment.CENTER)
                .setFont(fontRegular)
                .setFontSize(10)
                .setMarginTop(30)
                .setFontColor(new com.itextpdf.kernel.colors.DeviceRgb(0, 123, 255));  // Blue color
        document.add(footer);

        // Close the document PDF
        document.close();
    }
}

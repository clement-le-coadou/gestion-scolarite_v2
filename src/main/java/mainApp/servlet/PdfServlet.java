package mainApp.servlet;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import mainApp.model.Cours;
import mainApp.model.Note;

import java.io.OutputStream;
import java.util.List;

public class PdfUtil {

    public static void generateRelevePdf(OutputStream outputStream, String nomEtudiant, String prenomEtudiant, String emailEtudiant,
                                         List<Cours> coursList, List<Note> notesList) throws Exception {
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        // Charger les polices
        PdfFont fontBold = PdfFontFactory.createFont("Helvetica-Bold");
        PdfFont fontRegular = PdfFontFactory.createFont("Helvetica");

        // Titre
        Paragraph header = new Paragraph("Relev� de Notes - CY Tech")
                .setFont(fontBold)
                .setFontSize(22)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20)
                .setBackgroundColor(ColorConstants.BLUE)
                .setFontColor(ColorConstants.WHITE);
        document.add(header);

        // Infos de l'�tudiant
        Paragraph studentInfo = new Paragraph("Nom : " + nomEtudiant + "\n" +
                                              "Pr�nom : " + prenomEtudiant + "\n" +
                                              "Email : " + emailEtudiant)
                .setFont(fontRegular)
                .setFontSize(12)
                .setMarginBottom(20);
        document.add(studentInfo);

        // Table des r�sultats
        Table table = new Table(UnitValue.createPercentArray(new float[]{70, 30}))
                .setWidth(UnitValue.createPercentValue(100));

        // En-t�tes
        table.addHeaderCell(new Cell().add(new Paragraph("Cours"))
                .setBackgroundColor(ColorConstants.BLUE)
                .setFont(fontBold)
                .setFontColor(ColorConstants.WHITE)
                .setTextAlignment(TextAlignment.CENTER));
        table.addHeaderCell(new Cell().add(new Paragraph("Note"))
                .setBackgroundColor(ColorConstants.BLUE)
                .setFont(fontBold)
                .setFontColor(ColorConstants.WHITE)
                .setTextAlignment(TextAlignment.CENTER));

        // Lignes de donn�es
        for (Cours cours : coursList) {
            Note note = notesList.stream()
                    .filter(n -> n.getCours().getId().equals(cours.getId()))
                    .findFirst()
                    .orElse(null);
            String noteValue = (note != null) ? String.valueOf(note.getNote()) : "Pas de note";

            table.addCell(new Cell().add(new Paragraph(cours.getNom()))
                    .setFont(fontRegular)
                    .setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell().add(new Paragraph(noteValue))
                    .setFont(fontRegular)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBackgroundColor(ColorConstants.LIGHT_GRAY));
        }

        document.add(table);

        // Footer
        Paragraph footer = new Paragraph("Pour plus d'informations, contactez nous � : contact@cytech.com")
                .setTextAlignment(TextAlignment.CENTER)
                .setFont(fontRegular)
                .setFontSize(10)
                .setMarginTop(30)
                .setFontColor(ColorConstants.BLUE);
        document.add(footer);

        document.close();
    }
}

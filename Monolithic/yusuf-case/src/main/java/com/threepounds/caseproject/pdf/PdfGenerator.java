package com.threepounds.caseproject.pdf;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.threepounds.caseproject.controller.resource.AdvertResource;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class PdfGenerator {


    public static void generate(HttpServletResponse response, List<AdvertResource> advertResources) throws DocumentException, IOException {


        Document document = new Document(PageSize.A4);

        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        Font fontTiltle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        fontTiltle.setSize(20);

        Paragraph paragraph = new Paragraph("List Of Adverts", fontTiltle);


        paragraph.setAlignment(Paragraph.ALIGN_CENTER);


        document.add(paragraph);

        // Creating a table of 3 columns
        PdfPTable table = new PdfPTable(7);

        // Setting width of table, its columns and spacing
        table.setWidthPercentage(100f);
        table.setWidths(new int[] { 5, 3, 5 , 3, 3 , 3, 3 });
        table.setSpacingBefore(5);

        // Create Table Cells for table header
        PdfPCell cell = new PdfPCell();

        // Setting the background color and padding
        cell.setBackgroundColor(CMYKColor.MAGENTA);
        cell.setPadding(5);

        // Creating font
        // Setting font style and size
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        font.setColor(CMYKColor.WHITE);

        // Adding headings in the created table cell/ header
        // Adding Cell to table
        cell.setPhrase(new Phrase("ID", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Name", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Description", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Price", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("City", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("County", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Street", font));
        table.addCell(cell);


        for (AdvertResource advertResource : advertResources) {
            table.addCell(String.valueOf(advertResource.getId()));
            table.addCell(advertResource.getTitle());
            table.addCell(advertResource.getDescription());
            table.addCell(advertResource.getPrice().toString());
            table.addCell(advertResource.getCity().getName());
            table.addCell(advertResource.getCounty().getName());
            table.addCell(advertResource.getStreet().getName());

        }
        // Adding the created table to document
        document.add(table);

        // Closing the document
        document.close();

    }

}

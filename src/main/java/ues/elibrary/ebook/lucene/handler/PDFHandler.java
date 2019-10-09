package ues.elibrary.ebook.lucene.handler;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.text.PDFTextStripper;
import ues.elibrary.ebook.dto.BookDTO;

import java.io.File;
import java.text.SimpleDateFormat;

public class PDFHandler {

    public static BookDTO extractPDF(File file) {
        BookDTO bookDTO = new BookDTO();
        try {
            PDFTextStripper textStripper = new PDFTextStripper();
            PDDocument pdf = PDDocument.load(file);
            PDDocumentInformation info = pdf.getDocumentInformation();

            bookDTO.setText(textStripper.getText(pdf));
            bookDTO.setTitle(info.getTitle());
            bookDTO.setKeywords(info.getKeywords());
            bookDTO.setAuthor(info.getAuthor());

            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            String date = sdf.format(info.getCreationDate().getTime());
            bookDTO.setCreatedDate(date);

            pdf.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return bookDTO;
    }

}

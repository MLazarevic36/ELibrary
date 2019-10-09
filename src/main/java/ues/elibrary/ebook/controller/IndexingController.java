package ues.elibrary.ebook.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ues.elibrary.ebook.dto.BookDTO;
import ues.elibrary.ebook.entity.Book;
import ues.elibrary.ebook.lucene.Indexer;
import ues.elibrary.ebook.lucene.handler.PDFHandler;
import ues.elibrary.ebook.service.BookService;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;


@RestController
@RequestMapping(value = "api/index")
public class IndexingController {

    @Autowired
    private BookService bookService;

    @PostMapping(value = "/add")
    public ResponseEntity<?> indexBook(@ModelAttribute BookDTO bookDTO) throws IOException {

        String filename = Objects.requireNonNull(bookDTO.getBookFile().getOriginalFilename()).replace(".pdf", "");
        Book ebook = new Book();
        ebook.setFile(bookDTO.getBookFile().getBytes());
        ebook.setCategoryId(bookDTO.getCategoryId());
        ebook.setLanguageId(bookDTO.getLanguageId());
        ebook.setUserId(bookDTO.getUserId());

        File serverFile = saveFileOnServer(bookDTO);
        if (serverFile == null) {
            return new ResponseEntity<String>("Error while uploading file to server!", HttpStatus.BAD_REQUEST);
        }

        BookDTO pdfExtracted = PDFHandler.extractPDF(serverFile);
        if (pdfExtracted == null) {
            return new ResponseEntity<String>("Error while parsing file!", HttpStatus.BAD_REQUEST);
        }

        ebook.setFileName(filename);
        ebook.setCreatedDate(pdfExtracted.getCreatedDate());
        ebook.setTitle(pdfExtracted.getTitle());
        ebook.setAuthor(pdfExtracted.getAuthor());
        ebook.setKeywords(pdfExtracted.getKeywords());
        ebook.setText(pdfExtracted.getText());

        Book savedBook = bookService.save(ebook);

        BookDTO metadata = new BookDTO();
        metadata.setId(savedBook.getId());
        metadata.setFileName(filename);
        metadata.setCreatedDate(ebook.getCreatedDate());
        metadata.setTitle(ebook.getTitle());
        metadata.setAuthor(ebook.getAuthor());
        metadata.setKeywords(ebook.getKeywords());

        return new ResponseEntity<BookDTO>(metadata, HttpStatus.OK);

    }

    @PostMapping(value = "/metadata")
    public ResponseEntity<?> configMetadata(@RequestBody BookDTO bookDTO) {

        Book ebook = bookService.findById(bookDTO.getId());
        ebook.setFileName(bookDTO.getFileName());
        ebook.setCreatedDate(bookDTO.getCreatedDate());
        ebook.setTitle(bookDTO.getTitle());
        ebook.setAuthor(bookDTO.getAuthor());
        ebook.setKeywords(bookDTO.getKeywords());

        try {
            Indexer.indexFile(ebook);
        } catch (IOException e) {
            return new ResponseEntity<String>("Error while indexing file!", HttpStatus.BAD_REQUEST);
        }

        bookService.save(ebook);

        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }


    private File saveFileOnServer(BookDTO bookDTO) {
        File serverFile = null;
        if (!bookDTO.getBookFile().isEmpty()) {
            try {
                byte[] bytes = bookDTO.getBookFile().getBytes();

                String rootPath = System.getProperty("catalina.home");
                File dir = new File(rootPath + File.separator + "tmpFiles");
                if(!dir.exists())
                    dir.mkdirs();
                serverFile = new File(dir.getAbsolutePath() + File.separator + bookDTO.getBookFile().getOriginalFilename());
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();
            } catch (Exception e) {

            }
        }
        return  serverFile;

    }

}

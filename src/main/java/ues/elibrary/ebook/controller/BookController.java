package ues.elibrary.ebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ues.elibrary.ebook.dto.BookDTO;
import ues.elibrary.ebook.entity.Book;
import ues.elibrary.ebook.lucene.Indexer;
import ues.elibrary.ebook.service.BookService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<BookDTO> getById(@PathVariable("id") Long id) {
        Book ebook = bookService.findById(id);
        BookDTO bookDTO = new BookDTO(ebook);

        return new ResponseEntity<BookDTO>(bookDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/category/{catId}")
    public ResponseEntity<List<BookDTO>> getAllByCategoryId(@PathVariable("catId") Long id) {

        List<BookDTO> bookDTOS = new ArrayList<>();
        List<Book> ebooks = bookService.findByCategoryId(id);
        for(Book ebook: ebooks) {
            bookDTOS.add(new BookDTO(ebook));
        }

        return new ResponseEntity<List<BookDTO>>(bookDTOS, HttpStatus.OK);

    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) throws IOException {
        bookService.remove(id);
        Indexer.deleteFileFromIndex(id);
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }

    @GetMapping(value = "/download/{id}")
    public ResponseEntity<Resource> downloadBook(@PathVariable Long id) {
        Book ebook = bookService.findById(id);

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + ebook.getFileName() +
                                ".pdf\"")
                        .body(new ByteArrayResource(ebook.getFile()));
    }

    @PostMapping(value = "/change")
    public  ResponseEntity<?> changeBook(@RequestBody BookDTO bookDTO) throws IOException {
        Book ebook = bookService.findById(bookDTO.getId());
        ebook.setTitle(bookDTO.getTitle());
        ebook.setAuthor(bookDTO.getAuthor());
        ebook.setKeywords(bookDTO.getKeywords());
        bookService.save(ebook);

        Indexer.deleteFileFromIndex(ebook.getId());
        Indexer.indexFile(ebook);

        return new ResponseEntity<Long>(ebook.getId(), HttpStatus.OK);
    }







}

package ues.elibrary.ebook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ues.elibrary.ebook.entity.Book;
import ues.elibrary.ebook.repository.BookRepository;
import ues.elibrary.ebook.service.service.impl.BookServiceImpl;

import java.util.List;

@Service
public class BookService implements BookServiceImpl {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> findByCategoryId(Long id) {
        return bookRepository.findByCategoryId(id);
    }

    @Override
    public Book findById(Long id) {
        return bookRepository.getOne(id);
    }

    @Override
    public Book save(Book ebook) {
        return bookRepository.save(ebook);
    }

    @Override
    public void remove(Long id) {
        bookRepository.deleteById(id);

    }
}

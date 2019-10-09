package ues.elibrary.ebook.service.service.impl;

import ues.elibrary.ebook.entity.Book;


import java.util.List;

public interface BookServiceImpl {

    List<Book> findByCategoryId(Long id);

    Book findById(Long id);

    Book save(Book ebook);

    void remove(Long id);
}

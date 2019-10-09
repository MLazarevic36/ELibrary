package ues.elibrary.ebook.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ues.elibrary.ebook.dto.CategoryDTO;
import ues.elibrary.ebook.entity.Book;
import ues.elibrary.ebook.entity.Category;
import ues.elibrary.ebook.entity.User;
import ues.elibrary.ebook.lucene.Indexer;
import ues.elibrary.ebook.service.CategoryService;
import ues.elibrary.ebook.service.BookService;
import ues.elibrary.ebook.service.UserService;


import javax.servlet.http.HttpSession;


@RestController
@RequestMapping(value = "/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @GetMapping()
    public ResponseEntity<List<CategoryDTO>> getAll() {
        List<CategoryDTO> categoryDTOs = new ArrayList<>();
        List<Category> categories = categoryService.findAll();
        for (Category category : categories) {
            categoryDTOs.add(new CategoryDTO(category));
        }

        return new ResponseEntity<List<CategoryDTO>>(categoryDTOs, HttpStatus.OK);
    }

    @PostMapping(value = "/newcategory")
    public ResponseEntity<?> newCategory(@RequestBody CategoryDTO catDTO) {

        Category category = new Category();
        category.setName(catDTO.getName());
        categoryService.save(category);

        return new ResponseEntity<String>("Successfully added!", HttpStatus.OK);

    }

    @GetMapping(value = "/subscribe/{username}/{catId}")
    public ResponseEntity<?> subscribeUser(@PathVariable("username") String username,
                                           @PathVariable("catId") Long categoryId) {

        User user = userService.findByUsername(username);
        categoryService.addSubscribe(user.getId(), categoryId);
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);

    }

    @GetMapping(value = "/subsribecheck/{bookId}")
    public ResponseEntity<?> subscribeInfo(@PathVariable("bookId") Long bookId, HttpSession session) {
        Book ebook = bookService.findById(bookId);
        Long userId = (Long) session.getAttribute("userId");
        Long catId = categoryService.checkSubscribe(userId, ebook.getCategoryId());
        if (catId != null) {
            return new ResponseEntity<HttpStatus>(HttpStatus.OK);
        }
        return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") Long catId) throws IOException {
        List<Book> ebooks = bookService.findByCategoryId(catId);
        for (Book ebook : ebooks) {
            Indexer.deleteFileFromIndex(ebook.getId());
            bookService.remove(ebook.getId());
        }
        categoryService.remove(catId);
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }

}
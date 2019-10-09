package ues.elibrary.ebook.dto;


import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import ues.elibrary.ebook.entity.Book;


import java.io.Serializable;

@Getter
@Setter
public class BookDTO implements Serializable {

    private Long id;
    private String text;
    private MultipartFile bookFile;
    private Long languageId;
    private Long userId;
    private Long categoryId;
    private String title;
    private String author;
    private String keywords;
    private String createdDate;
    private String fileName;

    public BookDTO() {

    }

    public BookDTO(Long id, String text, MultipartFile bookFile, Long languageId, Long userId,
                   Long categoryId, String title, String author, String keywords,
                   String createdDate, String fileName) {
        super();
        this.id = id;
        this.text = text;
        this.bookFile = bookFile;
        this.userId = userId;
        this.categoryId = categoryId;
        this.languageId = languageId;
        this.title = title;
        this.author = author;
        this.keywords = keywords;
        this.createdDate = createdDate;
        this.fileName = fileName;
    }

    public BookDTO(Book book) {
        this(book.getId(), book.getText(), null, book.getLanguageId(), book.getUserId(), book.getCategoryId(),
                book.getTitle(), book.getAuthor(), book.getKeywords(), book.getCreatedDate(), book.getFileName());
    }

    public BookDTO(Long id, String text, Long languageId, String title, String author, String keywords,
                   String createdDate, String fileName) {
        super();
        this.id = id;
        this.text = text;
        this.languageId = languageId;
        this.title = title;
        this.author = author;
        this.keywords = keywords;
        this.createdDate = createdDate;
        this.fileName = fileName;
    }


}
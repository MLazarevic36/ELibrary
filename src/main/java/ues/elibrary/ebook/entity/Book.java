package ues.elibrary.ebook.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "keywords")
    private String keywords;

    @Column(name = "created_date")
    private String createdDate;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "text", nullable = false, columnDefinition = "TEXT")
    private String text;

    @Lob
    @Column(name = "file", nullable = false, columnDefinition = "LONGBLOB")
    private byte[] file;

    @Column(name = "language_id", nullable = false)
    private Long languageId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

}

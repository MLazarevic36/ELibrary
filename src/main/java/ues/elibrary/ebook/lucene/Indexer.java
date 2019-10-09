package ues.elibrary.ebook.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;

import ues.elibrary.ebook.entity.Book;
import ues.elibrary.ebook.lucene.analyzer.CustomAnalyzer;
import ues.elibrary.ebook.lucene.analyzer.SerbianAnalyzer;


import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class Indexer {

    public static Path luceneDir = new File("src/main/resources/lucene/").toPath();

    public static void indexFile(Book ebook) throws IOException {

        Directory directory = new SimpleFSDirectory(luceneDir);

        Analyzer analyzer = null;
        if (ebook.getLanguageId() == 1) {
            analyzer = new SerbianAnalyzer();
        }else {
            analyzer = new CustomAnalyzer();
        }

        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);

        try (IndexWriter indexWriter = new IndexWriter(directory, config)){

            Document document = new Document();
            document.add(new StringField("id", String.valueOf(ebook.getId()), Field.Store.YES));
            document.add(new TextField("fileName", ebook.getFileName(), Field.Store.YES));
            document.add(new TextField("title", ebook.getTitle(), Field.Store.YES));
            document.add(new TextField("author", ebook.getAuthor(), Field.Store.YES));
            document.add(new TextField("text", ebook.getText(), Field.Store.YES));
            document.add(new TextField("keywords", ebook.getKeywords(), Field.Store.YES));
            document.add(new TextField("createdDate", ebook.getCreatedDate(), Field.Store.YES));
            document.add(new TextField("language", ebook.getLanguageId().toString(), Field.Store.YES));

            indexWriter.addDocument(document);

        }
    }

    public static void deleteFileFromIndex(Long id) throws IOException {
        Directory directory = new SimpleFSDirectory(luceneDir);

        IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);

        IndexWriter indexWriter = new IndexWriter(directory, config);
        Term term = new Term("id", String.valueOf(id));
        Query query = new TermQuery(term);
        indexWriter.deleteDocuments(query);
        indexWriter.close();

    }

}

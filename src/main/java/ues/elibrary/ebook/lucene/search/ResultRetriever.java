package ues.elibrary.ebook.lucene.search;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;

import ues.elibrary.ebook.dto.BookDTO;
import ues.elibrary.ebook.lucene.Indexer;

import java.util.ArrayList;
import java.util.List;

public class ResultRetriever {

    private TopScoreDocCollector collector;

    public ResultRetriever() {
        collector = TopScoreDocCollector.create(10);
    }

    public List<BookDTO> returnResults(Query query) {
        List<BookDTO> ebooks = new ArrayList<>();

        try {
            Directory fsDir = new SimpleFSDirectory(Indexer.luceneDir);
            DirectoryReader reader = DirectoryReader.open(fsDir);
            IndexSearcher is = new IndexSearcher(reader);
            is.search(query,collector);

            ScoreDoc[] hits = collector.topDocs().scoreDocs;

            for (int i = 0; i < collector.getTotalHits(); i++) {
                int docId = hits[i].doc;
                Document document = is.doc(docId);
                BookDTO bookDTO = new BookDTO(Long.parseLong(document.get("id")), document.get("text"),
                                                Long.parseLong(document.get("language")), document.get("title"),
                                                document.get("author"), document.get("keywords"), document.get("createdDate"),
                                                document.get("fileName"));

                ebooks.add(bookDTO);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ebooks;
    }



}

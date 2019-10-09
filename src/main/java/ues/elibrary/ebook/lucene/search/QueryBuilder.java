package ues.elibrary.ebook.lucene.search;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import ues.elibrary.ebook.dto.AdvancedQueryDTO;
import ues.elibrary.ebook.dto.SimpleQueryDTO;

public class QueryBuilder {

    public static Query buildSimpleQuery (SimpleQueryDTO simpleQueryDTO) {
        Query query = null;

        Term term = new Term(simpleQueryDTO.getField(), simpleQueryDTO.getValue());

        if (simpleQueryDTO.getSearchType().equals("term")) {
            query = new TermQuery(term);
        }else if (simpleQueryDTO.getSearchType().equals("phrase")) {
            query = new PhraseQuery.Builder().add(term).build();
        } else if (simpleQueryDTO.getSearchType().equals("fuzzy")) {
            query = new FuzzyQuery(term);
        }

        return query;
    }

    public static Query buildAdvancedQuery(AdvancedQueryDTO advancedQueryDTO) {
        Query query = null;

        Term titleTerm = new Term("title", advancedQueryDTO.getTitle().value);
        Term authorTerm = new Term ("author", advancedQueryDTO.getAuthor().value);
        Term keywordTerm = new Term ("keywords", advancedQueryDTO.getKeywords().value);
        Term textTerm = new Term("text", advancedQueryDTO.getText().value);
        Term languageTerm = new Term("language", advancedQueryDTO.getLanguage().value);

        Query titleQuery = null;
        Query authorQuery = null;
        Query keywordsQuery = null;
        Query textQuery = null;
        Query languageQuery = null;

        if (advancedQueryDTO.getQueryType().equals("term")) {
            titleQuery = new TermQuery(titleTerm);
            authorQuery = new TermQuery(authorTerm);
            keywordsQuery = new TermQuery(keywordTerm);
            textQuery = new TermQuery(textTerm);
            languageQuery = new TermQuery(languageTerm);
        }else if (advancedQueryDTO.getQueryType().equals("phrase")) {
            titleQuery = new PhraseQuery.Builder().add(titleTerm).build();
            authorQuery = new PhraseQuery.Builder().add(authorTerm).build();
            keywordsQuery = new PhraseQuery.Builder().add(keywordTerm).build();
            textQuery = new PhraseQuery.Builder().add(textTerm).build();
            languageQuery = new PhraseQuery.Builder().add(languageTerm).build();
        }

        BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();

        if (!advancedQueryDTO.getTitle().value.equals("")) {
            booleanQuery.add(titleQuery, advancedQueryDTO.getTitle().occur);
        }
        if (!advancedQueryDTO.getAuthor().value.equals("")) {
            booleanQuery.add(authorQuery, advancedQueryDTO.getAuthor().occur);
        }
        if (!advancedQueryDTO.getKeywords().value.equals("")) {
            booleanQuery.add(keywordsQuery, advancedQueryDTO.getKeywords().occur);
        }
        if (!advancedQueryDTO.getText().value.equals("")) {
            booleanQuery.add(textQuery, advancedQueryDTO.getText().occur);
        }
        if (!advancedQueryDTO.getLanguage().value.equals("0")) {
            booleanQuery.add(languageQuery, advancedQueryDTO.getLanguage().occur);
        }

        query = booleanQuery.build();
        return query;


    }

}

package ues.elibrary.ebook.dto;

import org.apache.lucene.search.BooleanClause;

public class AdvancedQueryDTO {

    private Title title;
    private Author author;
    private Keywords keywords;
    private Text text;
    private Language language;
    private String queryType;

    public Title getTitle() {
        if (title.operator.equals("AND")) {
            title.occur = BooleanClause.Occur.MUST;
        }
        if (title.operator.equals("OR")) {
            title.occur = BooleanClause.Occur.SHOULD;
        }
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Author getAuthor() {
        if (author.operator.equals("AND")) {
            author.occur = BooleanClause.Occur.MUST;
        }
        if (author.operator.equals("OR")) {
            author.occur = BooleanClause.Occur.SHOULD;
        }
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Keywords getKeywords() {
        if (keywords.operator.equals("AND")) {
            keywords.occur = BooleanClause.Occur.MUST;
        }
        if (keywords.operator.equals("OR")) {
            keywords.occur = BooleanClause.Occur.SHOULD;
        }
        return keywords;
    }

    public void setKeywords(Keywords keywords) {
        this.keywords = keywords;
    }

    public Text getText() {
        if (text.operator.equals("AND")) {
            text.occur = BooleanClause.Occur.MUST;
        }
        if (text.operator.equals("OR")) {
            text.occur = BooleanClause.Occur.SHOULD;
        }
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public Language getLanguage() {
        if (language.operator.equals("AND")) {
            language.occur = BooleanClause.Occur.MUST;
        }
        if (language.operator.equals("OR")) {
            language.occur = BooleanClause.Occur.SHOULD;
        }
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }




    public class Title {
        public String value;
        public String operator;
        public BooleanClause.Occur occur;
    }

    public class Author {
        public String value;
        public String operator;
        public BooleanClause.Occur occur;
    }

    public class Keywords {
        public String value;
        public String operator;
        public BooleanClause.Occur occur;
    }

    public class Text {
        public String value;
        public String operator;
        public BooleanClause.Occur occur;
    }

    public class Language {
        public String value;
        public String operator;
        public BooleanClause.Occur occur;
    }
}

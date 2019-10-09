package ues.elibrary.ebook.lucene.analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;

public class CustomAnalyzer extends Analyzer {

    @Override
    protected TokenStreamComponents createComponents(String s) {
        Tokenizer tokenizer = new StandardTokenizer();
        TokenStream tokenStream = new LowerCaseFilter(tokenizer);

        return new TokenStreamComponents(tokenizer, tokenStream);
    }
}

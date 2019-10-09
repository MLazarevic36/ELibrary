package ues.elibrary.ebook.lucene.analyzer;

import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import ues.elibrary.ebook.lucene.filter.CyrilicToLatinFilter;

public class SerbianAnalyzer extends Analyzer {

    public static final String[] STOP_WORDS = {"i", "a", "ili", "ali", "pa", "te", "da", "u", "po", "na"};

    @Override
    protected TokenStreamComponents createComponents(String arg0) {

        Tokenizer source = new StandardTokenizer();
        TokenStream result = new CyrilicToLatinFilter(source);
        result = new LowerCaseFilter(result);
        result = new StopFilter(result, StopFilter.makeStopSet(STOP_WORDS));

        return new TokenStreamComponents(source,result);
    }
}

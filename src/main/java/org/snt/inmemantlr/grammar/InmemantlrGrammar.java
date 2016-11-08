package org.snt.inmemantlr.grammar;

import org.antlr.runtime.RecognitionException;
import org.antlr.v4.Tool;
import org.antlr.v4.tool.ANTLRToolListener;
import org.antlr.v4.tool.Grammar;
import org.antlr.v4.tool.LexerGrammar;
import org.antlr.v4.tool.ast.GrammarRootAST;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snt.inmemantlr.tool.MemoryTokenVocabParser;

import java.util.Map;

/**
 * Created by julian on 08/11/2016.
 */
public class InmemantlrGrammar extends Grammar {

    private static final Logger LOGGER = LoggerFactory.getLogger(InmemantlrGrammar.class);

    private String tokenVocab = "";

    public InmemantlrGrammar(Tool tool, GrammarRootAST ast) {
        super(tool, ast);
    }

    public InmemantlrGrammar(String grammarText) throws RecognitionException {
        super(grammarText);
    }

    public InmemantlrGrammar(String grammarText, LexerGrammar tokenVocabSource) throws RecognitionException {
        super(grammarText, tokenVocabSource);
    }

    public InmemantlrGrammar(String grammarText, ANTLRToolListener listener) throws RecognitionException {
        super(grammarText, listener);
    }

    public InmemantlrGrammar(String fileName, String grammarText) throws RecognitionException {
        super(fileName, grammarText);
    }

    public InmemantlrGrammar(String fileName, String grammarText, ANTLRToolListener listener) throws RecognitionException {
        super(fileName, grammarText, listener);
    }

    public InmemantlrGrammar(String fileName, String grammarText, Grammar tokenVocabSource, ANTLRToolListener listener) throws RecognitionException {
        super(fileName, grammarText, tokenVocabSource, listener);
    }


    public void setTokenVocab(String tokenVocab) {
        this.tokenVocab = tokenVocab;
    }

    /**
     * We want to touch as little ANTR code as possible. We overload this
     * function to pretend the existence of the token vocab parser
     */
    @Override
    public void importTokensFromTokensFile() {
        if(!tokenVocab.isEmpty()) {
            MemoryTokenVocabParser vparser = new MemoryTokenVocabParser(this, tokenVocab);
            Map<String,Integer> tokens = vparser.load();
            for (String t : tokens.keySet()) {
                if ( t.charAt(0)=='\'' ) defineStringLiteral(t, tokens.get(t));
                else defineTokenName(t, tokens.get(t));
            }
        }
    }
}
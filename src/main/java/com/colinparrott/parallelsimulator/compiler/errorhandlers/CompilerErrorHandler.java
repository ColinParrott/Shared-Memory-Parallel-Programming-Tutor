package com.colinparrott.parallelsimulator.compiler.errorhandlers;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.BitSet;

public class CompilerErrorHandler implements ANTLRErrorListener
{

    private boolean hasErrors;
    private ArrayList<String> errorMessages;
    private final Logger logger = LoggerFactory.getLogger(CompilerErrorHandler.class);

    public CompilerErrorHandler()
    {
        errorMessages = new ArrayList<>();
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e)
    {
        String errorMsg = formatErrorString(msg, line, charPositionInLine);
        logger.info(errorMsg);

        hasErrors = true;
        errorMessages.add(errorMsg);
    }

    @Override
    public void reportAmbiguity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, boolean exact, BitSet ambigAlts, ATNConfigSet configs)
    {

    }

    @Override
    public void reportAttemptingFullContext(Parser recognizer, DFA dfa, int startIndex, int stopIndex, BitSet conflictingAlts, ATNConfigSet configs)
    {

    }

    @Override
    public void reportContextSensitivity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, int prediction, ATNConfigSet configs)
    {

    }

    private String formatErrorString(String msg, int line, int charPos)
    {
        String template = "%s (%d:%d)";
        return String.format(template, msg, line, charPos);
    }

    public boolean hasErrors()
    {
        return hasErrors;
    }

    public ArrayList<String> getErrorMessages()
    {
        return errorMessages;
    }
}

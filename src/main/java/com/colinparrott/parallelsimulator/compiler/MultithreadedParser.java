package com.colinparrott.parallelsimulator.compiler;

import com.colinparrott.parallelsimulator.compiler.errorhandlers.CompilationError;
import com.colinparrott.parallelsimulator.compiler.errorhandlers.CompilerErrorHandler;
import com.colinparrott.parallelsimulator.compiler.multithreadcompiler.MultipleThreadsLanguageLexer;
import com.colinparrott.parallelsimulator.compiler.multithreadcompiler.MultipleThreadsLanguageParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;

import static org.antlr.v4.runtime.CharStreams.fromString;

@SuppressWarnings("Duplicates")
public class MultithreadedParser
{

    public MultithreadedParserResult parseProgram(String code)
    {
        CharStream cs = fromString(code);
        MultipleThreadsLanguageLexer lexer = new MultipleThreadsLanguageLexer(cs);

        CompilerErrorHandler lexerErrorHandler = new CompilerErrorHandler();
        lexer.removeErrorListeners();
        lexer.addErrorListener(lexerErrorHandler);

        MultipleThreadsLanguageParser parser = new MultipleThreadsLanguageParser((new CommonTokenStream(lexer)));
        CompilerErrorHandler parserErrorHandler = new CompilerErrorHandler();
        parser.removeErrorListeners();
        parser.addErrorListener(parserErrorHandler);

        ParseTree tree = parser.topProgram(); // parse the content and get the tree

        if (lexerErrorHandler.hasErrors())
        {
//            return new Pair<>(null, lexerErrorHandler.getErrorMessages());
            return new MultithreadedParserResult(null, lexerErrorHandler.getErrorMessages());
        }

        if (parserErrorHandler.hasErrors())
        {
//            return new Pair<>(null, parserErrorHandler.getErrorMessages());
            return new MultithreadedParserResult(null, parserErrorHandler.getErrorMessages());
        }

        ArrayList<String> threadCodes = new ArrayList<>();
        int programCount = 0;
        for (int i = 0; i < tree.getChildCount(); i++)
        {
            if (tree.getChild(i) instanceof MultipleThreadsLanguageParser.ProgramContext)
            {
                programCount++;
                threadCodes.add(tree.getChild(i).getText());
            }

        }

        if (programCount <= 4)
        {
            return new MultithreadedParserResult(threadCodes, new ArrayList<>());
        }
        else
        {
            CompilationError error = new CompilationError("The simulator only supports 4 threads (" + programCount + " threads present in the code)", -1, -1);
            ArrayList<CompilationError> errors = new ArrayList<>();
            errors.add(error);
            return new MultithreadedParserResult(new ArrayList<>(), errors);
        }

    }
}

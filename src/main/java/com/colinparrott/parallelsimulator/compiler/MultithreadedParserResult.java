package com.colinparrott.parallelsimulator.compiler;

import com.colinparrott.parallelsimulator.compiler.errorhandlers.CompilationError;

import java.util.ArrayList;

public class MultithreadedParserResult
{
    private ArrayList<String> threadCode;
    private ArrayList<CompilationError> errors;
    private boolean hasErrors;

    public MultithreadedParserResult(ArrayList<String> assemblyLines, ArrayList<CompilationError> errors)
    {
        this.threadCode = assemblyLines;
        this.errors = errors;
        this.hasErrors = errors.size() > 0;
    }

    public ArrayList<String> getThreadCode()
    {
        return threadCode;
    }

    public ArrayList<CompilationError> getErrors()
    {
        return errors;
    }

    public boolean hasErrors()
    {
        return hasErrors;
    }
}
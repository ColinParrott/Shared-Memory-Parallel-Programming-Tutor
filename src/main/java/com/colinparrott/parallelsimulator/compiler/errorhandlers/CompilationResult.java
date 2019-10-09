package com.colinparrott.parallelsimulator.compiler.errorhandlers;

import java.util.ArrayList;

public class CompilationResult
{
    private ArrayList<String> assemblyLines;
    private ArrayList<CompilationError> errors;
    private boolean hasErrors;

    public CompilationResult(ArrayList<String> assemblyLines, ArrayList<CompilationError> errors)
    {
        this.assemblyLines = assemblyLines;
        this.errors = errors;
        this.hasErrors = errors.size() > 0;
    }

    public ArrayList<String> getAssemblyLines()
    {
        return assemblyLines;
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

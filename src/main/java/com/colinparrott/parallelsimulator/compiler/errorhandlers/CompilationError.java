package com.colinparrott.parallelsimulator.compiler.errorhandlers;

public class CompilationError
{
    private String errorMessage;
    private int line;
    private int character;

    public CompilationError(String errorMessage, int line, int character)
    {
        this.errorMessage = errorMessage;
        this.line = line;
        this.character = character;
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }

    public int getLine()
    {
        return line;
    }

    public int getCharacter()
    {
        return character;
    }
}

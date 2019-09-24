package com.colinparrott.parallelsimulator.engine.compiler.lexer;

import com.colinparrott.parallelsimulator.engine.compiler.util.Position;

public class Token
{

    public enum TokenClass
    {

        // the \ (backslash) is used as an escape character in the regular expression below
        // ' is used to enclose character while " is used to enclose strings

        IDENTIFIER, // ('a'|...|'z'|'A'|...|'Z'|'_')('0'|...|'9'|'a'|...|'z'|'A'|...|'Z'|'_')*
        AWAIT, // await statement

        ASSIGN, // '='

        // delimiters
        LBRA,  // '{' // left brace
        RBRA,  // '}' // right brace
        LPAR,  // '(' // left parenthesis
        RPAR,  // ')' // right parenthesis
        SC,    // ';' // semicolon
        COMMA, // ','

        // types
        INT,  // "int"

        // keywords
        IF,     // "if"
        ELSE,   // "else"
        WHILE,  // "while"

        // literals
        INT_LITERAL,    // ('0'|...|'9')+

        // logical operators
        AND, // "&&"
        OR,  // "||"

        // comparisons
        EQ, // "=="
        NE, // "!="
        LT, // '<'
        GT, // '>'

        // operators
        PLUS,  // '+'
        MINUS, // '-'
        ASTERIX, // '*'  // can be used for multiplication or pointers
        DIV,   // '/'

        // special tokens
        EOF,    // signal end of file
        INVALID // in case we cannot recognise a character as part of a valid token
    }


    public final TokenClass tokenClass;
    public final String data;
    public final Position position;

    public Token(TokenClass type, int lineNum, int colNum)
    {
        this(type, "", lineNum, colNum);
    }

    public Token(TokenClass tokenClass, String data, int lineNum, int colNum)
    {
        assert (tokenClass != null);
        this.tokenClass = tokenClass;
        this.data = data;
        this.position = new Position(lineNum, colNum);
    }


    @Override
    public String toString()
    {
        if (data.equals(""))
            return tokenClass.toString();
        else
            return tokenClass.toString() + "(" + data + ")";
    }

}



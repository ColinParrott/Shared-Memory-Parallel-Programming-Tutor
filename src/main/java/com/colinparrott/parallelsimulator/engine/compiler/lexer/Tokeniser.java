package com.colinparrott.parallelsimulator.engine.compiler.lexer;

import com.colinparrott.parallelsimulator.engine.compiler.lexer.Token.TokenClass;

import java.io.EOFException;
import java.io.IOException;
import java.util.HashMap;

public class Tokeniser
{

    private Scanner scanner;

    private int error = 0;

    public int getErrorCount()
    {
        return this.error;
    }

    public Tokeniser(Scanner scanner)
    {
        this.scanner = scanner;
    }

    private void error(char c, int line, int col)
    {
        System.out.println("Lexing error: unrecognised character (" + c + ") at " + line + ":" + col);
        error++;
    }


    public Token nextToken()
    {
        Token result;
        try
        {
            result = next();
        }
        catch (EOFException eof)
        {
            // end of file, nothing to worry about, just return EOF token
            return new Token(TokenClass.EOF, scanner.getLine(), scanner.getColumn());
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
            // something went horribly wrong, abort
            System.exit(-1);
            return null;
        }
        return result;
    }


    /*
     * To be completed
     *
     */
    private Token next() throws IOException
    {

        int line = scanner.getLine();
        int column = scanner.getColumn();

        // get the next character
        char c = scanner.next();

        // skip white spaces
        if (Character.isWhitespace(c))
            return next();

        // recognises the plus operator
        if (c == '+')
            return new Token(TokenClass.PLUS, line, column);
        else if (c == '-')
            return new Token(TokenClass.MINUS, line, column);
        else if (c == '*')
            return new Token(TokenClass.ASTERIX, line, column);
        else if (c == '{')
            return new Token(TokenClass.LBRA, line, column);
        else if (c == '}')
            return new Token(TokenClass.RBRA, line, column);
        else if (c == '(')
            return new Token(TokenClass.LPAR, line, column);
        else if (c == ')')
            return new Token(TokenClass.RPAR, line, column);
        else if (c == ';')
            return new Token(TokenClass.SC, line, column);
        else if (c == ',')
            return new Token(TokenClass.COMMA, line, column);
        else if (c == '&') // &&
        {
            return handleOperators('&', TokenClass.AND, line, column);
        }
        else if (c == '|') // ||
        {
            return handleOperators('|', TokenClass.OR, line, column);
        }
        else if (c == '!') // !=
        {
            char next;
            if (!scanner.hasNext())
            {
                error(c, line, column);
                return new Token(TokenClass.INVALID, column, line);
            }

            next = scanner.peek();
            if (next == '=')
            {
                // Advance pointer so it doesn't look at '=' next
                scanner.next();
                return new Token(TokenClass.NE, line, column);
            }
            else
            {
                error(c, line, column);
                return new Token(TokenClass.INVALID, line, column);
            }
        }
        else if (c == '>') // >
        {
            return new Token(TokenClass.GT, line, column);
        }
        else if (c == '<') // < or await
        {

            // Loop while receiving char
            if (scanner.hasNext() && scanner.peek() == 'A')
            {
                scanner.next(); // consume 'A'

                String target = "WAIT";
                int i = 0;
                while (i < target.length())
                {
                    if (!scanner.hasNext())
                    {
                        error(c, line, column);
                        return new Token(TokenClass.INVALID, line, column);
                    }

                    if (scanner.next() != target.charAt(i))
                    {
                        error(c, line, column);
                        return new Token(TokenClass.INVALID, line, column);
                    }

                    i++;
                }

                return new Token(TokenClass.AWAIT, line, column);
            }

            return new Token(TokenClass.LT, line, column);
        }
        else if (c == '=') // = or ==
        {
            if (scanner.hasNext())
            {
                char next = scanner.peek();
                if (next == '=')
                {
                    // Advance pointer so it doesn't look at '=' next
                    scanner.next();
                    return new Token(TokenClass.EQ, line, column);
                }
                else
                    return new Token(TokenClass.ASSIGN, line, column);
            }
            else
            {
                return new Token(TokenClass.ASSIGN, line, column);
            }
        }
        // Identifier or keyword (beginning with letter or _) (MUST BE LOWERCASE)
        else if (Character.isLetter(c) && Character.isLowerCase(c) || c == '_')
        {
            // Hashmap of keywords
            HashMap<String, TokenClass> keywords = new HashMap<>();
            keywords.put("int", TokenClass.INT);
            keywords.put("if", TokenClass.IF);
            keywords.put("else", TokenClass.ELSE);
            keywords.put("while", TokenClass.WHILE);
            StringBuilder s = new StringBuilder();
            s.append(c);

            // Loop while receiving char or digit
            while (scanner.hasNext())
            {
                char next = scanner.peek();

                if (Character.isLetterOrDigit(next) || next == '_')
                {
                    s.append(next);
                    scanner.next();

                    // If string matches a keyword return it
                    if (keywords.containsKey(s.toString()))
                    {
                        if (!scanner.hasNext())
                            return new Token(keywords.get(s.toString()), line, column);

                        char peekedChar = scanner.peek();
                        if (!Character.isLetterOrDigit(peekedChar) && peekedChar != '_')
                            return new Token(keywords.get(s.toString()), line, column);
                    }
                }
                else
                {
                    break;
                }
            }

            return new Token(TokenClass.IDENTIFIER, s.toString(), line, column);
        }
        // Int literals
        else if (Character.isDigit(c))
        {
            StringBuilder s = new StringBuilder();
            s.append(c);
            while (scanner.hasNext())
            {
                char next = scanner.peek();

                if (Character.isDigit(next))
                {
                    s.append(next);
                    scanner.next();
                }
                else
                {
                    return new Token(TokenClass.INT_LITERAL, s.toString(), line, column);
                }
            }

            return new Token(TokenClass.INT_LITERAL, s.toString(), line, column);

        }
        // DIV and comments
        else if (c == '/')
        {
            if (!scanner.hasNext()) return new Token(TokenClass.DIV, line, column);

            c = scanner.peek();
            // Single line comment (//)
            if (c == '/')
            {
                // Process all characters on line
                while (c != '\n' && c != '\r')
                {
                    c = scanner.next();
                }

                return next();
            }
            // Multi-line comment (/*)
            else if (c == '*')
            {
                StringBuilder s = new StringBuilder();

                // Loop through until we hit */ or end of file
                while (scanner.hasNext())
                {
                    c = scanner.next();
                    s.append(c);
                    if (s.toString().endsWith("*/"))
                    {
                        return next();
                    }
                }

                // Hit end of file without seeing */ so comment never terminates (invalid)
                error(c, line, column);
                return new Token(TokenClass.INVALID, line, column);
            }
            else
            {
                return new Token(TokenClass.DIV, line, column);
            }
        }

        // if we reach this point, it means we did not recognise a valid token
        error(c, line, column);
        return new Token(TokenClass.INVALID, line, column);
    }

    // Handles OR and AND
    private Token handleOperators(char c, TokenClass tokenClass, int line, int column) throws IOException
    {
        char next;
        if (!scanner.hasNext())
        {
            error(c, line, column);
            return new Token(TokenClass.INVALID, line, column);
        }

        next = scanner.peek();

        if (next == c)
        {
            scanner.next();
            return new Token(tokenClass, line, column);
        }
        else
        {
            error(c, line, column);
            return new Token(TokenClass.INVALID, line, column);
        }
    }


}

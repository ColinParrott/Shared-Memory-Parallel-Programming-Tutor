package com.colinparrott.parallelsimulator.engine.compiler.parser;

import com.colinparrott.parallelsimulator.engine.compiler.ast.Block;
import com.colinparrott.parallelsimulator.engine.compiler.ast.Program;
import com.colinparrott.parallelsimulator.engine.compiler.ast.Stmt;
import com.colinparrott.parallelsimulator.engine.compiler.ast.VarDecl;
import com.colinparrott.parallelsimulator.engine.compiler.lexer.Token;
import com.colinparrott.parallelsimulator.engine.compiler.lexer.Token.TokenClass;
import com.colinparrott.parallelsimulator.engine.compiler.lexer.Tokeniser;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class Parser
{

    private Token token;

    // use for backtracking (useful for distinguishing decls from procs when parsing a program for instance)
    private Queue<Token> buffer = new LinkedList<>();

    private final Tokeniser tokeniser;

    public Parser(Tokeniser tokeniser)
    {
        this.tokeniser = tokeniser;
    }


    public Program parse()
    {
        // get the first token
        nextToken();

        return parseProgram();
    }

    public int getErrorCount()
    {
        return error;
    }

    private int error = 0;
    private Token lastErrorToken;

    private void error(TokenClass... expected)
    {

        if (lastErrorToken == token)
        {
            // skip this error, same token causing trouble
            return;
        }

        StringBuilder sb = new StringBuilder();
        String sep = "";
        for (TokenClass e : expected)
        {
            sb.append(sep);
            sb.append(e);
            sep = "|";
        }
        System.out.println("Parsing error: expected (" + sb + ") found (" + token + ") at " + token.position);

        error++;
        lastErrorToken = token;
    }

    /*
     * Look ahead the i^th element from the stream of token.
     * i should be >= 1
     */
    private Token lookAhead(int i)
    {
        // ensures the buffer has the element we want to look ahead
        while (buffer.size() < i)
            buffer.add(tokeniser.nextToken());
        assert buffer.size() >= i;

        int cnt = 1;
        for (Token t : buffer)
        {
            if (cnt == i)
                return t;
            cnt++;
        }

        assert false; // should never reach this
        return null;
    }


    /*
     * Consumes the next token from the tokeniser or the buffer if not empty.
     */
    private void nextToken()
    {
        if (!buffer.isEmpty())
            token = buffer.remove();
        else
            token = tokeniser.nextToken();

//        System.out.println(token);
    }

    /*
     * If the current token is equals to the expected one, then skip it, otherwise report an error.
     * Returns the expected token or null if an error occurred.
     */
    private Token expect(TokenClass... expected)
    {
        for (TokenClass e : expected)
        {
            if (e == token.tokenClass)
            {
                Token cur = token;
                nextToken();
                return cur;
            }
        }

        error(expected);
        return null;
    }

    /*
     * Returns true if the current token is equals to any of the expected ones.
     */
    private boolean accept(TokenClass... expected)
    {
        boolean result = false;
        for (TokenClass e : expected)
            result |= (e == token.tokenClass);
        return result;
    }


    private Program parseProgram()
    {
        List<VarDecl> vds = parseVarDecls();
        Block block = parseBlock();
        expect(TokenClass.EOF);
        return new Program(vds, block);
    }

    private List<VarDecl> parseVarDecls()
    {
//        System.out.println("parseVarDecls");
        List<VarDecl> varDecls = new ArrayList<>();

        while (accept(TokenClass.INT))
        {
            nextToken();
            Token identifierToken = expect(TokenClass.IDENTIFIER);
            int value = 0;
            if (accept(TokenClass.ASSIGN))
            {
                nextToken();
                // todo: handle too big ints (maybe support negative assigments)
                value = Integer.valueOf(expect(TokenClass.INT_LITERAL).data);
            }

            expect(TokenClass.SC);
            varDecls.add(new VarDecl(identifierToken.data, value));
        }
        return varDecls;
    }

    private Block parseBlock()
    {
        // TODO: finish
        List<Stmt> statements = new ArrayList<Stmt>();

//        while(accept(TokenClass.WHILE, TokenClass.IF, TokenClass.IDENTIFIER, TokenClass.AWAIT))
//        {
//
//        }

        return new Block(statements);
    }


}

package com.colinparrott.parallelsimulator.engine.compiler.parser;

import com.colinparrott.parallelsimulator.engine.compiler.ast.*;
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
        List<Block> blocks = parseAllBlocks();
        expect(TokenClass.EOF);
        return new Program(vds, blocks);
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
                // todo: handle too big ints (maybe support negative assignments)
                Token t = expect(TokenClass.INT_LITERAL);

                if (t != null)
                    value = Integer.parseInt(t.data);
            }

            expect(TokenClass.SC);
            varDecls.add(new VarDecl(identifierToken.data, value));
        }
        return varDecls;
    }

    private List<Block> parseAllBlocks()
    {
        // TODO: finish
        List<Block> blocks = new ArrayList<>();
        boolean lastBlockAtomic = false;
        boolean initialBlockFound = false;

        while (accept(TokenClass.WHILE, TokenClass.IF, TokenClass.IDENTIFIER, TokenClass.AWAIT, TokenClass.LT))
        {
            initialBlockFound = true;
            if (accept(TokenClass.LT))
            {
                lastBlockAtomic = true;
                blocks.add(parseAtomicBlock());
            }
            else
            {
                lastBlockAtomic = false;
                blocks.add(parseBlock());
            }
        }
        return blocks;
    }

    private AtomicBlock parseAtomicBlock()
    {
        List<Stmt> stmts = new ArrayList<>();
        nextToken(); // CONSUME OPENING "<"

        // parse statements
        while (accept(TokenClass.WHILE, TokenClass.IF, TokenClass.IDENTIFIER, TokenClass.AWAIT))
            stmts.add(parseStatement());

        // expect closing ">" to end atomic block
        expect(TokenClass.GT);
        return new AtomicBlock(stmts);
    }

    private Block parseBlock()
    {
        List<Stmt> stmts = new ArrayList<>();

        //todo: finish
        return null;
    }

    private Stmt parseStatement()
    {

        if (accept(TokenClass.WHILE)) // while loops
        {
            nextToken();
            expect(TokenClass.LPAR);
            Expr expr = parseCondExpression();
            expect(TokenClass.RPAR);
            Stmt stmt = parseStatement();
            return new While(expr, stmt);
        }
        else if (accept(TokenClass.IF)) // if-else statements
        {
            return parseIf();
        }
        else if (accept(TokenClass.IDENTIFIER)) // assignments (eg x=5 or x=y*4);
        {
            Token ident = expect(TokenClass.IDENTIFIER); // consume identifier
            expect(TokenClass.ASSIGN); // "="
            Expr mathExpr = parseAdditionExpr();
            expect(TokenClass.SC);
            return new Assign(ident.data, mathExpr);
        }
        else
        {
            error(TokenClass.WHILE, TokenClass.IF, TokenClass.IDENTIFIER, TokenClass.AWAIT);
        }

        return null;
    }

    private Expr parseAdditionExpr()
    {
        //todo: finish
        Token initial = expect(TokenClass.INT_LITERAL, TokenClass.IDENTIFIER, TokenClass.LPAR);


        return null;
    }

    private Stmt parseIf()
    {
        //todo: finish
        return null;
    }


    private Expr parseCondExpression()
    {
        //todo: finish
        return null;
    }


}

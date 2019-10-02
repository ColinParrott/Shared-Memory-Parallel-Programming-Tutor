package com.colinparrott.parallelsimulator.engine.compiler.antlrgen;// Generated from C:/Users/Colin/IdeaProjects/tutor-test\highLanguage.g4 by ANTLR 4.7.2

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class highLanguageParser extends Parser
{
    static
    {
        RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION);
    }

    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
            new PredictionContextCache();
    public static final int
            ASSIGN = 1, LBRA = 2, RBRA = 3, LPAR = 4, RPAR = 5, SC = 6, IF = 7, ELSE = 8, WHILE = 9,
            INT_LITERAL = 10, AND_OP = 11, OR_OP = 12, EQ_OP = 13, NE_OP = 14, LT_OP = 15, GT_OP = 16,
            MULT_MATH_OP = 17, DIV_MATH_OP = 18, ADD_MATH_OP = 19, SUB_MATH_OP = 20, IDENTIFIER = 21,
            AWAIT = 22, WS = 23, COMMENT = 24, LINE_COMMENT = 25;
    public static final int
            RULE_program = 0, RULE_atomicBlock = 1, RULE_block = 2, RULE_stmt = 3,
            RULE_condExp = 4, RULE_whileStmt = 5, RULE_ifStmt = 6, RULE_assignStmt = 7,
            RULE_awaitStmt = 8, RULE_compExp = 9, RULE_valueExp = 10, RULE_additionExp = 11,
            RULE_subExp = 12, RULE_multExp = 13, RULE_divExp = 14, RULE_singleValue = 15;

    private static String[] makeRuleNames()
    {
        return new String[]{
                "program", "atomicBlock", "block", "stmt", "condExp", "whileStmt", "ifStmt",
                "assignStmt", "awaitStmt", "compExp", "valueExp", "additionExp", "subExp",
                "multExp", "divExp", "singleValue"
        };
    }

    public static final String[] ruleNames = makeRuleNames();

    private static String[] makeLiteralNames()
    {
        return new String[]{
                null, "'='", "'{'", "'}'", "'('", "')'", "';'", "'if'", "'else'", "'while'",
                null, "'&&'", "'||'", "'=='", "'!='", "'<'", "'>'", "'*'", "'/'", "'+'",
                "'-'", null, "'<AWAIT'"
        };
    }

    private static final String[] _LITERAL_NAMES = makeLiteralNames();

    private static String[] makeSymbolicNames()
    {
        return new String[]{
                null, "ASSIGN", "LBRA", "RBRA", "LPAR", "RPAR", "SC", "IF", "ELSE", "WHILE",
                "INT_LITERAL", "AND_OP", "OR_OP", "EQ_OP", "NE_OP", "LT_OP", "GT_OP",
                "MULT_MATH_OP", "DIV_MATH_OP", "ADD_MATH_OP", "SUB_MATH_OP", "IDENTIFIER",
                "AWAIT", "WS", "COMMENT", "LINE_COMMENT"
        };
    }

    private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
    public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

    /**
     * @deprecated Use {@link #VOCABULARY} instead.
     */
    @Deprecated
    public static final String[] tokenNames;

    static
    {
        tokenNames = new String[_SYMBOLIC_NAMES.length];
        for (int i = 0; i < tokenNames.length; i++)
        {
            tokenNames[i] = VOCABULARY.getLiteralName(i);
            if (tokenNames[i] == null)
            {
                tokenNames[i] = VOCABULARY.getSymbolicName(i);
            }

            if (tokenNames[i] == null)
            {
                tokenNames[i] = "<INVALID>";
            }
        }
    }

    @Override
    @Deprecated
    public String[] getTokenNames()
    {
        return tokenNames;
    }

    @Override

    public Vocabulary getVocabulary()
    {
        return VOCABULARY;
    }

    @Override
    public String getGrammarFileName()
    {
        return "highLanguage.g4";
    }

    @Override
    public String[] getRuleNames()
    {
        return ruleNames;
    }

    @Override
    public String getSerializedATN()
    {
        return _serializedATN;
    }

    @Override
    public ATN getATN()
    {
        return _ATN;
    }

    public highLanguageParser(TokenStream input)
    {
        super(input);
        _interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    public static class ProgramContext extends ParserRuleContext
    {
        public List<AtomicBlockContext> atomicBlock()
        {
            return getRuleContexts(AtomicBlockContext.class);
        }

        public AtomicBlockContext atomicBlock(int i)
        {
            return getRuleContext(AtomicBlockContext.class, i);
        }

        public List<BlockContext> block()
        {
            return getRuleContexts(BlockContext.class);
        }

        public BlockContext block(int i)
        {
            return getRuleContext(BlockContext.class, i);
        }

        public ProgramContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex()
        {
            return RULE_program;
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof highLanguageListener) ((highLanguageListener) listener).enterProgram(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof highLanguageListener) ((highLanguageListener) listener).exitProgram(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof highLanguageVisitor)
                return ((highLanguageVisitor<? extends T>) visitor).visitProgram(this);
            else return visitor.visitChildren(this);
        }
    }

    public final ProgramContext program() throws RecognitionException
    {
        ProgramContext _localctx = new ProgramContext(_ctx, getState());
        enterRule(_localctx, 0, RULE_program);
        int _la;
        try
        {
            enterOuterAlt(_localctx, 1);
            {
                setState(36);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << IF) | (1L << WHILE) | (1L << LT_OP) | (1L << IDENTIFIER) | (1L << AWAIT))) != 0))
                {
                    {
                        setState(34);
                        _errHandler.sync(this);
                        switch (_input.LA(1))
                        {
                            case LT_OP:
                            {
                                setState(32);
                                atomicBlock();
                            }
                            break;
                            case IF:
                            case WHILE:
                            case IDENTIFIER:
                            case AWAIT:
                            {
                                setState(33);
                                block();
                            }
                            break;
                            default:
                                throw new NoViableAltException(this);
                        }
                    }
                    setState(38);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public static class AtomicBlockContext extends ParserRuleContext
    {
        public TerminalNode LT_OP()
        {
            return getToken(highLanguageParser.LT_OP, 0);
        }

        public TerminalNode GT_OP()
        {
            return getToken(highLanguageParser.GT_OP, 0);
        }

        public List<StmtContext> stmt()
        {
            return getRuleContexts(StmtContext.class);
        }

        public StmtContext stmt(int i)
        {
            return getRuleContext(StmtContext.class, i);
        }

        public AtomicBlockContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex()
        {
            return RULE_atomicBlock;
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof highLanguageListener) ((highLanguageListener) listener).enterAtomicBlock(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof highLanguageListener) ((highLanguageListener) listener).exitAtomicBlock(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof highLanguageVisitor)
                return ((highLanguageVisitor<? extends T>) visitor).visitAtomicBlock(this);
            else return visitor.visitChildren(this);
        }
    }

    public final AtomicBlockContext atomicBlock() throws RecognitionException
    {
        AtomicBlockContext _localctx = new AtomicBlockContext(_ctx, getState());
        enterRule(_localctx, 2, RULE_atomicBlock);
        int _la;
        try
        {
            enterOuterAlt(_localctx, 1);
            {
                setState(39);
                match(LT_OP);
                setState(41);
                _errHandler.sync(this);
                _la = _input.LA(1);
                do
                {
                    {
                        {
                            setState(40);
                            stmt();
                        }
                    }
                    setState(43);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                } while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << IF) | (1L << WHILE) | (1L << IDENTIFIER) | (1L << AWAIT))) != 0));
                setState(45);
                match(GT_OP);
            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public static class BlockContext extends ParserRuleContext
    {
        public List<StmtContext> stmt()
        {
            return getRuleContexts(StmtContext.class);
        }

        public StmtContext stmt(int i)
        {
            return getRuleContext(StmtContext.class, i);
        }

        public BlockContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex()
        {
            return RULE_block;
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof highLanguageListener) ((highLanguageListener) listener).enterBlock(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof highLanguageListener) ((highLanguageListener) listener).exitBlock(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof highLanguageVisitor)
                return ((highLanguageVisitor<? extends T>) visitor).visitBlock(this);
            else return visitor.visitChildren(this);
        }
    }

    public final BlockContext block() throws RecognitionException
    {
        BlockContext _localctx = new BlockContext(_ctx, getState());
        enterRule(_localctx, 4, RULE_block);
        try
        {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(48);
                _errHandler.sync(this);
                _alt = 1;
                do
                {
                    switch (_alt)
                    {
                        case 1:
                        {
                            {
                                setState(47);
                                stmt();
                            }
                        }
                        break;
                        default:
                            throw new NoViableAltException(this);
                    }
                    setState(50);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 3, _ctx);
                } while (_alt != 2 && _alt != ATN.INVALID_ALT_NUMBER);
            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public static class StmtContext extends ParserRuleContext
    {
        public WhileStmtContext whileStmt()
        {
            return getRuleContext(WhileStmtContext.class, 0);
        }

        public IfStmtContext ifStmt()
        {
            return getRuleContext(IfStmtContext.class, 0);
        }

        public AssignStmtContext assignStmt()
        {
            return getRuleContext(AssignStmtContext.class, 0);
        }

        public AwaitStmtContext awaitStmt()
        {
            return getRuleContext(AwaitStmtContext.class, 0);
        }

        public StmtContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex()
        {
            return RULE_stmt;
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof highLanguageListener) ((highLanguageListener) listener).enterStmt(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof highLanguageListener) ((highLanguageListener) listener).exitStmt(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof highLanguageVisitor)
                return ((highLanguageVisitor<? extends T>) visitor).visitStmt(this);
            else return visitor.visitChildren(this);
        }
    }

    public final StmtContext stmt() throws RecognitionException
    {
        StmtContext _localctx = new StmtContext(_ctx, getState());
        enterRule(_localctx, 6, RULE_stmt);
        try
        {
            setState(56);
            _errHandler.sync(this);
            switch (_input.LA(1))
            {
                case WHILE:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(52);
                    whileStmt();
                }
                break;
                case IF:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(53);
                    ifStmt();
                }
                break;
                case IDENTIFIER:
                    enterOuterAlt(_localctx, 3);
                {
                    setState(54);
                    assignStmt();
                }
                break;
                case AWAIT:
                    enterOuterAlt(_localctx, 4);
                {
                    setState(55);
                    awaitStmt();
                }
                break;
                default:
                    throw new NoViableAltException(this);
            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public static class CondExpContext extends ParserRuleContext
    {
        public List<CompExpContext> compExp()
        {
            return getRuleContexts(CompExpContext.class);
        }

        public CompExpContext compExp(int i)
        {
            return getRuleContext(CompExpContext.class, i);
        }

        public TerminalNode AND_OP()
        {
            return getToken(highLanguageParser.AND_OP, 0);
        }

        public TerminalNode OR_OP()
        {
            return getToken(highLanguageParser.OR_OP, 0);
        }

        public CondExpContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex()
        {
            return RULE_condExp;
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof highLanguageListener) ((highLanguageListener) listener).enterCondExp(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof highLanguageListener) ((highLanguageListener) listener).exitCondExp(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof highLanguageVisitor)
                return ((highLanguageVisitor<? extends T>) visitor).visitCondExp(this);
            else return visitor.visitChildren(this);
        }
    }

    public final CondExpContext condExp() throws RecognitionException
    {
        CondExpContext _localctx = new CondExpContext(_ctx, getState());
        enterRule(_localctx, 8, RULE_condExp);
        int _la;
        try
        {
            enterOuterAlt(_localctx, 1);
            {
                setState(58);
                compExp();
                setState(61);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == AND_OP || _la == OR_OP)
                {
                    {
                        setState(59);
                        _la = _input.LA(1);
                        if (!(_la == AND_OP || _la == OR_OP))
                        {
                            _errHandler.recoverInline(this);
                        }
                        else
                        {
                            if (_input.LA(1) == Token.EOF) matchedEOF = true;
                            _errHandler.reportMatch(this);
                            consume();
                        }
                        setState(60);
                        compExp();
                    }
                }

            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public static class WhileStmtContext extends ParserRuleContext
    {
        public TerminalNode WHILE()
        {
            return getToken(highLanguageParser.WHILE, 0);
        }

        public TerminalNode LPAR()
        {
            return getToken(highLanguageParser.LPAR, 0);
        }

        public CondExpContext condExp()
        {
            return getRuleContext(CondExpContext.class, 0);
        }

        public TerminalNode RPAR()
        {
            return getToken(highLanguageParser.RPAR, 0);
        }

        public TerminalNode LBRA()
        {
            return getToken(highLanguageParser.LBRA, 0);
        }

        public TerminalNode RBRA()
        {
            return getToken(highLanguageParser.RBRA, 0);
        }

        public StmtContext stmt()
        {
            return getRuleContext(StmtContext.class, 0);
        }

        public WhileStmtContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex()
        {
            return RULE_whileStmt;
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof highLanguageListener) ((highLanguageListener) listener).enterWhileStmt(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof highLanguageListener) ((highLanguageListener) listener).exitWhileStmt(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof highLanguageVisitor)
                return ((highLanguageVisitor<? extends T>) visitor).visitWhileStmt(this);
            else return visitor.visitChildren(this);
        }
    }

    public final WhileStmtContext whileStmt() throws RecognitionException
    {
        WhileStmtContext _localctx = new WhileStmtContext(_ctx, getState());
        enterRule(_localctx, 10, RULE_whileStmt);
        int _la;
        try
        {
            enterOuterAlt(_localctx, 1);
            {
                setState(63);
                match(WHILE);
                setState(64);
                match(LPAR);
                setState(65);
                condExp();
                setState(66);
                match(RPAR);
                setState(67);
                match(LBRA);
                setState(69);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << IF) | (1L << WHILE) | (1L << IDENTIFIER) | (1L << AWAIT))) != 0))
                {
                    {
                        setState(68);
                        stmt();
                    }
                }

                setState(71);
                match(RBRA);
            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public static class IfStmtContext extends ParserRuleContext
    {
        public TerminalNode IF()
        {
            return getToken(highLanguageParser.IF, 0);
        }

        public TerminalNode LPAR()
        {
            return getToken(highLanguageParser.LPAR, 0);
        }

        public CondExpContext condExp()
        {
            return getRuleContext(CondExpContext.class, 0);
        }

        public TerminalNode RPAR()
        {
            return getToken(highLanguageParser.RPAR, 0);
        }

        public List<TerminalNode> LBRA()
        {
            return getTokens(highLanguageParser.LBRA);
        }

        public TerminalNode LBRA(int i)
        {
            return getToken(highLanguageParser.LBRA, i);
        }

        public List<TerminalNode> RBRA()
        {
            return getTokens(highLanguageParser.RBRA);
        }

        public TerminalNode RBRA(int i)
        {
            return getToken(highLanguageParser.RBRA, i);
        }

        public List<StmtContext> stmt()
        {
            return getRuleContexts(StmtContext.class);
        }

        public StmtContext stmt(int i)
        {
            return getRuleContext(StmtContext.class, i);
        }

        public TerminalNode ELSE()
        {
            return getToken(highLanguageParser.ELSE, 0);
        }

        public IfStmtContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex()
        {
            return RULE_ifStmt;
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof highLanguageListener) ((highLanguageListener) listener).enterIfStmt(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof highLanguageListener) ((highLanguageListener) listener).exitIfStmt(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof highLanguageVisitor)
                return ((highLanguageVisitor<? extends T>) visitor).visitIfStmt(this);
            else return visitor.visitChildren(this);
        }
    }

    public final IfStmtContext ifStmt() throws RecognitionException
    {
        IfStmtContext _localctx = new IfStmtContext(_ctx, getState());
        enterRule(_localctx, 12, RULE_ifStmt);
        int _la;
        try
        {
            enterOuterAlt(_localctx, 1);
            {
                setState(73);
                match(IF);
                setState(74);
                match(LPAR);
                setState(75);
                condExp();
                setState(76);
                match(RPAR);
                setState(77);
                match(LBRA);
                setState(79);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << IF) | (1L << WHILE) | (1L << IDENTIFIER) | (1L << AWAIT))) != 0))
                {
                    {
                        setState(78);
                        stmt();
                    }
                }

                setState(81);
                match(RBRA);
                setState(88);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == ELSE)
                {
                    {
                        setState(82);
                        match(ELSE);
                        setState(83);
                        match(LBRA);
                        setState(85);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << IF) | (1L << WHILE) | (1L << IDENTIFIER) | (1L << AWAIT))) != 0))
                        {
                            {
                                setState(84);
                                stmt();
                            }
                        }

                        setState(87);
                        match(RBRA);
                    }
                }

            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public static class AssignStmtContext extends ParserRuleContext
    {
        public TerminalNode IDENTIFIER()
        {
            return getToken(highLanguageParser.IDENTIFIER, 0);
        }

        public TerminalNode ASSIGN()
        {
            return getToken(highLanguageParser.ASSIGN, 0);
        }

        public ValueExpContext valueExp()
        {
            return getRuleContext(ValueExpContext.class, 0);
        }

        public TerminalNode SC()
        {
            return getToken(highLanguageParser.SC, 0);
        }

        public AssignStmtContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex()
        {
            return RULE_assignStmt;
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof highLanguageListener) ((highLanguageListener) listener).enterAssignStmt(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof highLanguageListener) ((highLanguageListener) listener).exitAssignStmt(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof highLanguageVisitor)
                return ((highLanguageVisitor<? extends T>) visitor).visitAssignStmt(this);
            else return visitor.visitChildren(this);
        }
    }

    public final AssignStmtContext assignStmt() throws RecognitionException
    {
        AssignStmtContext _localctx = new AssignStmtContext(_ctx, getState());
        enterRule(_localctx, 14, RULE_assignStmt);
        try
        {
            enterOuterAlt(_localctx, 1);
            {
                setState(90);
                match(IDENTIFIER);
                setState(91);
                match(ASSIGN);
                setState(92);
                valueExp();
                setState(93);
                match(SC);
            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public static class AwaitStmtContext extends ParserRuleContext
    {
        public TerminalNode AWAIT()
        {
            return getToken(highLanguageParser.AWAIT, 0);
        }

        public CondExpContext condExp()
        {
            return getRuleContext(CondExpContext.class, 0);
        }

        public TerminalNode GT_OP()
        {
            return getToken(highLanguageParser.GT_OP, 0);
        }

        public TerminalNode SC()
        {
            return getToken(highLanguageParser.SC, 0);
        }

        public AwaitStmtContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex()
        {
            return RULE_awaitStmt;
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof highLanguageListener) ((highLanguageListener) listener).enterAwaitStmt(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof highLanguageListener) ((highLanguageListener) listener).exitAwaitStmt(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof highLanguageVisitor)
                return ((highLanguageVisitor<? extends T>) visitor).visitAwaitStmt(this);
            else return visitor.visitChildren(this);
        }
    }

    public final AwaitStmtContext awaitStmt() throws RecognitionException
    {
        AwaitStmtContext _localctx = new AwaitStmtContext(_ctx, getState());
        enterRule(_localctx, 16, RULE_awaitStmt);
        try
        {
            enterOuterAlt(_localctx, 1);
            {
                setState(95);
                match(AWAIT);
                setState(96);
                condExp();
                setState(97);
                match(GT_OP);
                setState(98);
                match(SC);
            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public static class CompExpContext extends ParserRuleContext
    {
        public List<SingleValueContext> singleValue()
        {
            return getRuleContexts(SingleValueContext.class);
        }

        public SingleValueContext singleValue(int i)
        {
            return getRuleContext(SingleValueContext.class, i);
        }

        public TerminalNode EQ_OP()
        {
            return getToken(highLanguageParser.EQ_OP, 0);
        }

        public TerminalNode NE_OP()
        {
            return getToken(highLanguageParser.NE_OP, 0);
        }

        public TerminalNode LT_OP()
        {
            return getToken(highLanguageParser.LT_OP, 0);
        }

        public TerminalNode GT_OP()
        {
            return getToken(highLanguageParser.GT_OP, 0);
        }

        public CompExpContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex()
        {
            return RULE_compExp;
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof highLanguageListener) ((highLanguageListener) listener).enterCompExp(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof highLanguageListener) ((highLanguageListener) listener).exitCompExp(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof highLanguageVisitor)
                return ((highLanguageVisitor<? extends T>) visitor).visitCompExp(this);
            else return visitor.visitChildren(this);
        }
    }

    public final CompExpContext compExp() throws RecognitionException
    {
        CompExpContext _localctx = new CompExpContext(_ctx, getState());
        enterRule(_localctx, 18, RULE_compExp);
        int _la;
        try
        {
            enterOuterAlt(_localctx, 1);
            {
                setState(100);
                singleValue();
                setState(101);
                _la = _input.LA(1);
                if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << EQ_OP) | (1L << NE_OP) | (1L << LT_OP) | (1L << GT_OP))) != 0)))
                {
                    _errHandler.recoverInline(this);
                }
                else
                {
                    if (_input.LA(1) == Token.EOF) matchedEOF = true;
                    _errHandler.reportMatch(this);
                    consume();
                }
                setState(102);
                singleValue();
            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public static class ValueExpContext extends ParserRuleContext
    {
        public SingleValueContext singleValue()
        {
            return getRuleContext(SingleValueContext.class, 0);
        }

        public AdditionExpContext additionExp()
        {
            return getRuleContext(AdditionExpContext.class, 0);
        }

        public SubExpContext subExp()
        {
            return getRuleContext(SubExpContext.class, 0);
        }

        public MultExpContext multExp()
        {
            return getRuleContext(MultExpContext.class, 0);
        }

        public DivExpContext divExp()
        {
            return getRuleContext(DivExpContext.class, 0);
        }

        public ValueExpContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex()
        {
            return RULE_valueExp;
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof highLanguageListener) ((highLanguageListener) listener).enterValueExp(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof highLanguageListener) ((highLanguageListener) listener).exitValueExp(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof highLanguageVisitor)
                return ((highLanguageVisitor<? extends T>) visitor).visitValueExp(this);
            else return visitor.visitChildren(this);
        }
    }

    public final ValueExpContext valueExp() throws RecognitionException
    {
        ValueExpContext _localctx = new ValueExpContext(_ctx, getState());
        enterRule(_localctx, 20, RULE_valueExp);
        try
        {
            setState(109);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 10, _ctx))
            {
                case 1:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(104);
                    singleValue();
                }
                break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(105);
                    additionExp();
                }
                break;
                case 3:
                    enterOuterAlt(_localctx, 3);
                {
                    setState(106);
                    subExp();
                }
                break;
                case 4:
                    enterOuterAlt(_localctx, 4);
                {
                    setState(107);
                    multExp();
                }
                break;
                case 5:
                    enterOuterAlt(_localctx, 5);
                {
                    setState(108);
                    divExp();
                }
                break;
            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public static class AdditionExpContext extends ParserRuleContext
    {
        public List<SingleValueContext> singleValue()
        {
            return getRuleContexts(SingleValueContext.class);
        }

        public SingleValueContext singleValue(int i)
        {
            return getRuleContext(SingleValueContext.class, i);
        }

        public TerminalNode ADD_MATH_OP()
        {
            return getToken(highLanguageParser.ADD_MATH_OP, 0);
        }

        public AdditionExpContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex()
        {
            return RULE_additionExp;
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof highLanguageListener) ((highLanguageListener) listener).enterAdditionExp(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof highLanguageListener) ((highLanguageListener) listener).exitAdditionExp(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof highLanguageVisitor)
                return ((highLanguageVisitor<? extends T>) visitor).visitAdditionExp(this);
            else return visitor.visitChildren(this);
        }
    }

    public final AdditionExpContext additionExp() throws RecognitionException
    {
        AdditionExpContext _localctx = new AdditionExpContext(_ctx, getState());
        enterRule(_localctx, 22, RULE_additionExp);
        try
        {
            enterOuterAlt(_localctx, 1);
            {
                setState(111);
                singleValue();
                setState(112);
                match(ADD_MATH_OP);
                setState(113);
                singleValue();
            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public static class SubExpContext extends ParserRuleContext
    {
        public List<SingleValueContext> singleValue()
        {
            return getRuleContexts(SingleValueContext.class);
        }

        public SingleValueContext singleValue(int i)
        {
            return getRuleContext(SingleValueContext.class, i);
        }

        public TerminalNode SUB_MATH_OP()
        {
            return getToken(highLanguageParser.SUB_MATH_OP, 0);
        }

        public SubExpContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex()
        {
            return RULE_subExp;
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof highLanguageListener) ((highLanguageListener) listener).enterSubExp(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof highLanguageListener) ((highLanguageListener) listener).exitSubExp(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof highLanguageVisitor)
                return ((highLanguageVisitor<? extends T>) visitor).visitSubExp(this);
            else return visitor.visitChildren(this);
        }
    }

    public final SubExpContext subExp() throws RecognitionException
    {
        SubExpContext _localctx = new SubExpContext(_ctx, getState());
        enterRule(_localctx, 24, RULE_subExp);
        try
        {
            enterOuterAlt(_localctx, 1);
            {
                setState(115);
                singleValue();
                setState(116);
                match(SUB_MATH_OP);
                setState(117);
                singleValue();
            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public static class MultExpContext extends ParserRuleContext
    {
        public List<SingleValueContext> singleValue()
        {
            return getRuleContexts(SingleValueContext.class);
        }

        public SingleValueContext singleValue(int i)
        {
            return getRuleContext(SingleValueContext.class, i);
        }

        public TerminalNode MULT_MATH_OP()
        {
            return getToken(highLanguageParser.MULT_MATH_OP, 0);
        }

        public MultExpContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex()
        {
            return RULE_multExp;
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof highLanguageListener) ((highLanguageListener) listener).enterMultExp(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof highLanguageListener) ((highLanguageListener) listener).exitMultExp(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof highLanguageVisitor)
                return ((highLanguageVisitor<? extends T>) visitor).visitMultExp(this);
            else return visitor.visitChildren(this);
        }
    }

    public final MultExpContext multExp() throws RecognitionException
    {
        MultExpContext _localctx = new MultExpContext(_ctx, getState());
        enterRule(_localctx, 26, RULE_multExp);
        try
        {
            enterOuterAlt(_localctx, 1);
            {
                setState(119);
                singleValue();
                setState(120);
                match(MULT_MATH_OP);
                setState(121);
                singleValue();
            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public static class DivExpContext extends ParserRuleContext
    {
        public List<SingleValueContext> singleValue()
        {
            return getRuleContexts(SingleValueContext.class);
        }

        public SingleValueContext singleValue(int i)
        {
            return getRuleContext(SingleValueContext.class, i);
        }

        public TerminalNode DIV_MATH_OP()
        {
            return getToken(highLanguageParser.DIV_MATH_OP, 0);
        }

        public DivExpContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex()
        {
            return RULE_divExp;
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof highLanguageListener) ((highLanguageListener) listener).enterDivExp(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof highLanguageListener) ((highLanguageListener) listener).exitDivExp(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof highLanguageVisitor)
                return ((highLanguageVisitor<? extends T>) visitor).visitDivExp(this);
            else return visitor.visitChildren(this);
        }
    }

    public final DivExpContext divExp() throws RecognitionException
    {
        DivExpContext _localctx = new DivExpContext(_ctx, getState());
        enterRule(_localctx, 28, RULE_divExp);
        try
        {
            enterOuterAlt(_localctx, 1);
            {
                setState(123);
                singleValue();
                setState(124);
                match(DIV_MATH_OP);
                setState(125);
                singleValue();
            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public static class SingleValueContext extends ParserRuleContext
    {
        public TerminalNode INT_LITERAL()
        {
            return getToken(highLanguageParser.INT_LITERAL, 0);
        }

        public TerminalNode IDENTIFIER()
        {
            return getToken(highLanguageParser.IDENTIFIER, 0);
        }

        public SingleValueContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex()
        {
            return RULE_singleValue;
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof highLanguageListener) ((highLanguageListener) listener).enterSingleValue(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof highLanguageListener) ((highLanguageListener) listener).exitSingleValue(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof highLanguageVisitor)
                return ((highLanguageVisitor<? extends T>) visitor).visitSingleValue(this);
            else return visitor.visitChildren(this);
        }
    }

    public final SingleValueContext singleValue() throws RecognitionException
    {
        SingleValueContext _localctx = new SingleValueContext(_ctx, getState());
        enterRule(_localctx, 30, RULE_singleValue);
        int _la;
        try
        {
            enterOuterAlt(_localctx, 1);
            {
                setState(127);
                _la = _input.LA(1);
                if (!(_la == INT_LITERAL || _la == IDENTIFIER))
                {
                    _errHandler.recoverInline(this);
                }
                else
                {
                    if (_input.LA(1) == Token.EOF) matchedEOF = true;
                    _errHandler.reportMatch(this);
                    consume();
                }
            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public static final String _serializedATN =
            "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\33\u0084\4\2\t\2" +
                    "\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13" +
                    "\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\3\2\3\2" +
                    "\7\2%\n\2\f\2\16\2(\13\2\3\3\3\3\6\3,\n\3\r\3\16\3-\3\3\3\3\3\4\6\4\63" +
                    "\n\4\r\4\16\4\64\3\5\3\5\3\5\3\5\5\5;\n\5\3\6\3\6\3\6\5\6@\n\6\3\7\3\7" +
                    "\3\7\3\7\3\7\3\7\5\7H\n\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\5\bR\n\b\3\b" +
                    "\3\b\3\b\3\b\5\bX\n\b\3\b\5\b[\n\b\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n" +
                    "\3\n\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\5\fp\n\f\3\r\3\r\3\r\3\r" +
                    "\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\21\3\21" +
                    "\3\21\2\2\22\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \2\5\3\2\r\16\3\2" +
                    "\17\22\4\2\f\f\27\27\2\u0083\2&\3\2\2\2\4)\3\2\2\2\6\62\3\2\2\2\b:\3\2" +
                    "\2\2\n<\3\2\2\2\fA\3\2\2\2\16K\3\2\2\2\20\\\3\2\2\2\22a\3\2\2\2\24f\3" +
                    "\2\2\2\26o\3\2\2\2\30q\3\2\2\2\32u\3\2\2\2\34y\3\2\2\2\36}\3\2\2\2 \u0081" +
                    "\3\2\2\2\"%\5\4\3\2#%\5\6\4\2$\"\3\2\2\2$#\3\2\2\2%(\3\2\2\2&$\3\2\2\2" +
                    "&\'\3\2\2\2\'\3\3\2\2\2(&\3\2\2\2)+\7\21\2\2*,\5\b\5\2+*\3\2\2\2,-\3\2" +
                    "\2\2-+\3\2\2\2-.\3\2\2\2./\3\2\2\2/\60\7\22\2\2\60\5\3\2\2\2\61\63\5\b" +
                    "\5\2\62\61\3\2\2\2\63\64\3\2\2\2\64\62\3\2\2\2\64\65\3\2\2\2\65\7\3\2" +
                    "\2\2\66;\5\f\7\2\67;\5\16\b\28;\5\20\t\29;\5\22\n\2:\66\3\2\2\2:\67\3" +
                    "\2\2\2:8\3\2\2\2:9\3\2\2\2;\t\3\2\2\2<?\5\24\13\2=>\t\2\2\2>@\5\24\13" +
                    "\2?=\3\2\2\2?@\3\2\2\2@\13\3\2\2\2AB\7\13\2\2BC\7\6\2\2CD\5\n\6\2DE\7" +
                    "\7\2\2EG\7\4\2\2FH\5\b\5\2GF\3\2\2\2GH\3\2\2\2HI\3\2\2\2IJ\7\5\2\2J\r" +
                    "\3\2\2\2KL\7\t\2\2LM\7\6\2\2MN\5\n\6\2NO\7\7\2\2OQ\7\4\2\2PR\5\b\5\2Q" +
                    "P\3\2\2\2QR\3\2\2\2RS\3\2\2\2SZ\7\5\2\2TU\7\n\2\2UW\7\4\2\2VX\5\b\5\2" +
                    "WV\3\2\2\2WX\3\2\2\2XY\3\2\2\2Y[\7\5\2\2ZT\3\2\2\2Z[\3\2\2\2[\17\3\2\2" +
                    "\2\\]\7\27\2\2]^\7\3\2\2^_\5\26\f\2_`\7\b\2\2`\21\3\2\2\2ab\7\30\2\2b" +
                    "c\5\n\6\2cd\7\22\2\2de\7\b\2\2e\23\3\2\2\2fg\5 \21\2gh\t\3\2\2hi\5 \21" +
                    "\2i\25\3\2\2\2jp\5 \21\2kp\5\30\r\2lp\5\32\16\2mp\5\34\17\2np\5\36\20" +
                    "\2oj\3\2\2\2ok\3\2\2\2ol\3\2\2\2om\3\2\2\2on\3\2\2\2p\27\3\2\2\2qr\5 " +
                    "\21\2rs\7\25\2\2st\5 \21\2t\31\3\2\2\2uv\5 \21\2vw\7\26\2\2wx\5 \21\2" +
                    "x\33\3\2\2\2yz\5 \21\2z{\7\23\2\2{|\5 \21\2|\35\3\2\2\2}~\5 \21\2~\177" +
                    "\7\24\2\2\177\u0080\5 \21\2\u0080\37\3\2\2\2\u0081\u0082\t\4\2\2\u0082" +
                    "!\3\2\2\2\r$&-\64:?GQWZo";
    public static final ATN _ATN =
            new ATNDeserializer().deserialize(_serializedATN.toCharArray());

    static
    {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++)
        {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }
}
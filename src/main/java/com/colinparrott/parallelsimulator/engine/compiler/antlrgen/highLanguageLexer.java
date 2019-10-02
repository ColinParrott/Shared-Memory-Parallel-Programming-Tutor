package com.colinparrott.parallelsimulator.engine.compiler.antlrgen;// Generated from C:/Users/Colin/IdeaProjects/tutor-test\highLanguage.g4 by ANTLR 4.7.2

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class highLanguageLexer extends Lexer
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
    public static String[] channelNames = {
            "DEFAULT_TOKEN_CHANNEL", "HIDDEN"
    };

    public static String[] modeNames = {
            "DEFAULT_MODE"
    };

    private static String[] makeRuleNames()
    {
        return new String[]{
                "ASSIGN", "LBRA", "RBRA", "LPAR", "RPAR", "SC", "IF", "ELSE", "WHILE",
                "INT_LITERAL", "AND_OP", "OR_OP", "EQ_OP", "NE_OP", "LT_OP", "GT_OP",
                "MULT_MATH_OP", "DIV_MATH_OP", "ADD_MATH_OP", "SUB_MATH_OP", "IDENTIFIER",
                "AWAIT", "WS", "COMMENT", "LINE_COMMENT"
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


    public highLanguageLexer(CharStream input)
    {
        super(input);
        _interp = new LexerATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
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
    public String[] getChannelNames()
    {
        return channelNames;
    }

    @Override
    public String[] getModeNames()
    {
        return modeNames;
    }

    @Override
    public ATN getATN()
    {
        return _ATN;
    }

    public static final String _serializedATN =
            "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\33\u0098\b\1\4\2" +
                    "\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4" +
                    "\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22" +
                    "\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31" +
                    "\t\31\4\32\t\32\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3" +
                    "\b\3\b\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\13\6\13Q\n\13\r\13" +
                    "\16\13R\3\f\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3\16\3\17\3\17\3\17\3\20\3\20" +
                    "\3\21\3\21\3\22\3\22\3\23\3\23\3\24\3\24\3\25\3\25\3\26\6\26n\n\26\r\26" +
                    "\16\26o\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\30\6\30z\n\30\r\30\16\30" +
                    "{\3\30\3\30\3\31\3\31\3\31\3\31\7\31\u0084\n\31\f\31\16\31\u0087\13\31" +
                    "\3\31\3\31\3\31\3\31\3\31\3\32\3\32\3\32\3\32\7\32\u0092\n\32\f\32\16" +
                    "\32\u0095\13\32\3\32\3\32\3\u0085\2\33\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21" +
                    "\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30" +
                    "/\31\61\32\63\33\3\2\6\3\2\62;\3\2c|\5\2\13\f\17\17\"\"\4\2\f\f\17\17" +
                    "\2\u009c\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2" +
                    "\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27" +
                    "\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2" +
                    "\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2" +
                    "\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\3\65\3\2\2\2\5\67\3\2\2\2\79\3\2" +
                    "\2\2\t;\3\2\2\2\13=\3\2\2\2\r?\3\2\2\2\17A\3\2\2\2\21D\3\2\2\2\23I\3\2" +
                    "\2\2\25P\3\2\2\2\27T\3\2\2\2\31W\3\2\2\2\33Z\3\2\2\2\35]\3\2\2\2\37`\3" +
                    "\2\2\2!b\3\2\2\2#d\3\2\2\2%f\3\2\2\2\'h\3\2\2\2)j\3\2\2\2+m\3\2\2\2-q" +
                    "\3\2\2\2/y\3\2\2\2\61\177\3\2\2\2\63\u008d\3\2\2\2\65\66\7?\2\2\66\4\3" +
                    "\2\2\2\678\7}\2\28\6\3\2\2\29:\7\177\2\2:\b\3\2\2\2;<\7*\2\2<\n\3\2\2" +
                    "\2=>\7+\2\2>\f\3\2\2\2?@\7=\2\2@\16\3\2\2\2AB\7k\2\2BC\7h\2\2C\20\3\2" +
                    "\2\2DE\7g\2\2EF\7n\2\2FG\7u\2\2GH\7g\2\2H\22\3\2\2\2IJ\7y\2\2JK\7j\2\2" +
                    "KL\7k\2\2LM\7n\2\2MN\7g\2\2N\24\3\2\2\2OQ\t\2\2\2PO\3\2\2\2QR\3\2\2\2" +
                    "RP\3\2\2\2RS\3\2\2\2S\26\3\2\2\2TU\7(\2\2UV\7(\2\2V\30\3\2\2\2WX\7~\2" +
                    "\2XY\7~\2\2Y\32\3\2\2\2Z[\7?\2\2[\\\7?\2\2\\\34\3\2\2\2]^\7#\2\2^_\7?" +
                    "\2\2_\36\3\2\2\2`a\7>\2\2a \3\2\2\2bc\7@\2\2c\"\3\2\2\2de\7,\2\2e$\3\2" +
                    "\2\2fg\7\61\2\2g&\3\2\2\2hi\7-\2\2i(\3\2\2\2jk\7/\2\2k*\3\2\2\2ln\t\3" +
                    "\2\2ml\3\2\2\2no\3\2\2\2om\3\2\2\2op\3\2\2\2p,\3\2\2\2qr\7>\2\2rs\7C\2" +
                    "\2st\7Y\2\2tu\7C\2\2uv\7K\2\2vw\7V\2\2w.\3\2\2\2xz\t\4\2\2yx\3\2\2\2z" +
                    "{\3\2\2\2{y\3\2\2\2{|\3\2\2\2|}\3\2\2\2}~\b\30\2\2~\60\3\2\2\2\177\u0080" +
                    "\7\61\2\2\u0080\u0081\7,\2\2\u0081\u0085\3\2\2\2\u0082\u0084\13\2\2\2" +
                    "\u0083\u0082\3\2\2\2\u0084\u0087\3\2\2\2\u0085\u0086\3\2\2\2\u0085\u0083" +
                    "\3\2\2\2\u0086\u0088\3\2\2\2\u0087\u0085\3\2\2\2\u0088\u0089\7,\2\2\u0089" +
                    "\u008a\7\61\2\2\u008a\u008b\3\2\2\2\u008b\u008c\b\31\3\2\u008c\62\3\2" +
                    "\2\2\u008d\u008e\7\61\2\2\u008e\u008f\7\61\2\2\u008f\u0093\3\2\2\2\u0090" +
                    "\u0092\n\5\2\2\u0091\u0090\3\2\2\2\u0092\u0095\3\2\2\2\u0093\u0091\3\2" +
                    "\2\2\u0093\u0094\3\2\2\2\u0094\u0096\3\2\2\2\u0095\u0093\3\2\2\2\u0096" +
                    "\u0097\b\32\3\2\u0097\64\3\2\2\2\b\2Ro{\u0085\u0093\4\2\3\2\b\2\2";
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
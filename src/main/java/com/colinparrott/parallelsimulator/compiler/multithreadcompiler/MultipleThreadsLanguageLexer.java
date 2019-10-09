package com.colinparrott.parallelsimulator.compiler.multithreadcompiler;// Generated from C:/Users/Colin/IdeaProjects/tutor-test\MultipleThreadsLanguage.g4 by ANTLR 4.7.2

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MultipleThreadsLanguageLexer extends Lexer
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
            GTE_OP = 17, LTE_OP = 18, MULT_MATH_OP = 19, DIV_MATH_OP = 20, CO_SEPARATOR = 21,
            ADD_MATH_OP = 22, SUB_MATH_OP = 23, IDENTIFIER = 24, AWAIT = 25, WS = 26, LINE_COMMENT = 27;
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
                "GTE_OP", "LTE_OP", "MULT_MATH_OP", "DIV_MATH_OP", "CO_SEPARATOR", "ADD_MATH_OP",
                "SUB_MATH_OP", "IDENTIFIER", "AWAIT", "WS", "LINE_COMMENT"
        };
    }

    public static final String[] ruleNames = makeRuleNames();

    private static String[] makeLiteralNames()
    {
        return new String[]{
                null, "'='", "'{'", "'}'", "'('", "')'", "';'", "'if'", "'else'", "'while'",
                null, "'&&'", "'||'", "'=='", "'!='", "'<'", "'>'", "'>='", "'<='", "'*'",
                "'/'", "'//'", "'+'", "'-'", null, "'AWAIT'"
        };
    }

    private static final String[] _LITERAL_NAMES = makeLiteralNames();

    private static String[] makeSymbolicNames()
    {
        return new String[]{
                null, "ASSIGN", "LBRA", "RBRA", "LPAR", "RPAR", "SC", "IF", "ELSE", "WHILE",
                "INT_LITERAL", "AND_OP", "OR_OP", "EQ_OP", "NE_OP", "LT_OP", "GT_OP",
                "GTE_OP", "LTE_OP", "MULT_MATH_OP", "DIV_MATH_OP", "CO_SEPARATOR", "ADD_MATH_OP",
                "SUB_MATH_OP", "IDENTIFIER", "AWAIT", "WS", "LINE_COMMENT"
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


    public MultipleThreadsLanguageLexer(CharStream input)
    {
        super(input);
        _interp = new LexerATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    @Override
    public String getGrammarFileName()
    {
        return "MultipleThreadsLanguage.g4";
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
            "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\35\u0094\b\1\4\2" +
                    "\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4" +
                    "\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22" +
                    "\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31" +
                    "\t\31\4\32\t\32\4\33\t\33\4\34\t\34\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3" +
                    "\6\3\6\3\7\3\7\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n" +
                    "\3\13\6\13U\n\13\r\13\16\13V\3\f\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3\16\3" +
                    "\17\3\17\3\17\3\20\3\20\3\21\3\21\3\22\3\22\3\22\3\23\3\23\3\23\3\24\3" +
                    "\24\3\25\3\25\3\26\3\26\3\26\3\27\3\27\3\30\3\30\3\31\6\31{\n\31\r\31" +
                    "\16\31|\3\32\3\32\3\32\3\32\3\32\3\32\3\33\6\33\u0086\n\33\r\33\16\33" +
                    "\u0087\3\33\3\33\3\34\3\34\7\34\u008e\n\34\f\34\16\34\u0091\13\34\3\34" +
                    "\3\34\2\2\35\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16" +
                    "\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34" +
                    "\67\35\3\2\6\3\2\62;\3\2c|\5\2\13\f\17\17\"\"\4\2\f\f\17\17\2\u0097\2" +
                    "\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2" +
                    "\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2" +
                    "\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2" +
                    "\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2" +
                    "\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\39\3\2\2\2\5;\3\2" +
                    "\2\2\7=\3\2\2\2\t?\3\2\2\2\13A\3\2\2\2\rC\3\2\2\2\17E\3\2\2\2\21H\3\2" +
                    "\2\2\23M\3\2\2\2\25T\3\2\2\2\27X\3\2\2\2\31[\3\2\2\2\33^\3\2\2\2\35a\3" +
                    "\2\2\2\37d\3\2\2\2!f\3\2\2\2#h\3\2\2\2%k\3\2\2\2\'n\3\2\2\2)p\3\2\2\2" +
                    "+r\3\2\2\2-u\3\2\2\2/w\3\2\2\2\61z\3\2\2\2\63~\3\2\2\2\65\u0085\3\2\2" +
                    "\2\67\u008b\3\2\2\29:\7?\2\2:\4\3\2\2\2;<\7}\2\2<\6\3\2\2\2=>\7\177\2" +
                    "\2>\b\3\2\2\2?@\7*\2\2@\n\3\2\2\2AB\7+\2\2B\f\3\2\2\2CD\7=\2\2D\16\3\2" +
                    "\2\2EF\7k\2\2FG\7h\2\2G\20\3\2\2\2HI\7g\2\2IJ\7n\2\2JK\7u\2\2KL\7g\2\2" +
                    "L\22\3\2\2\2MN\7y\2\2NO\7j\2\2OP\7k\2\2PQ\7n\2\2QR\7g\2\2R\24\3\2\2\2" +
                    "SU\t\2\2\2TS\3\2\2\2UV\3\2\2\2VT\3\2\2\2VW\3\2\2\2W\26\3\2\2\2XY\7(\2" +
                    "\2YZ\7(\2\2Z\30\3\2\2\2[\\\7~\2\2\\]\7~\2\2]\32\3\2\2\2^_\7?\2\2_`\7?" +
                    "\2\2`\34\3\2\2\2ab\7#\2\2bc\7?\2\2c\36\3\2\2\2de\7>\2\2e \3\2\2\2fg\7" +
                    "@\2\2g\"\3\2\2\2hi\7@\2\2ij\7?\2\2j$\3\2\2\2kl\7>\2\2lm\7?\2\2m&\3\2\2" +
                    "\2no\7,\2\2o(\3\2\2\2pq\7\61\2\2q*\3\2\2\2rs\7\61\2\2st\7\61\2\2t,\3\2" +
                    "\2\2uv\7-\2\2v.\3\2\2\2wx\7/\2\2x\60\3\2\2\2y{\t\3\2\2zy\3\2\2\2{|\3\2" +
                    "\2\2|z\3\2\2\2|}\3\2\2\2}\62\3\2\2\2~\177\7C\2\2\177\u0080\7Y\2\2\u0080" +
                    "\u0081\7C\2\2\u0081\u0082\7K\2\2\u0082\u0083\7V\2\2\u0083\64\3\2\2\2\u0084" +
                    "\u0086\t\4\2\2\u0085\u0084\3\2\2\2\u0086\u0087\3\2\2\2\u0087\u0085\3\2" +
                    "\2\2\u0087\u0088\3\2\2\2\u0088\u0089\3\2\2\2\u0089\u008a\b\33\2\2\u008a" +
                    "\66\3\2\2\2\u008b\u008f\7%\2\2\u008c\u008e\n\5\2\2\u008d\u008c\3\2\2\2" +
                    "\u008e\u0091\3\2\2\2\u008f\u008d\3\2\2\2\u008f\u0090\3\2\2\2\u0090\u0092" +
                    "\3\2\2\2\u0091\u008f\3\2\2\2\u0092\u0093\b\34\3\2\u00938\3\2\2\2\7\2V" +
                    "|\u0087\u008f\4\2\3\2\b\2\2";
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
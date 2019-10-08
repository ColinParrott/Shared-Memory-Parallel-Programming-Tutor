package com.colinparrott.parallelsimulator.compiler.singleprogramcompiler;// Generated from C:/Users/Colin/IdeaProjects/tutor-test\SingleProgramLanguage.g4 by ANTLR 4.7.2

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
public class SingleProgramLanguageParser extends Parser
{
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		ASSIGN=1, LBRA=2, RBRA=3, LPAR=4, RPAR=5, SC=6, IF=7, ELSE=8, WHILE=9,
			INT_LITERAL = 10, AND_OP = 11, OR_OP = 12, EQ_OP = 13, NE_OP = 14, LT_OP = 15, GT_OP = 16,
			GTE_OP = 17, LTE_OP = 18, MULT_MATH_OP = 19, DIV_MATH_OP = 20, CO_SEPARATOR = 21,
			ADD_MATH_OP = 22, SUB_MATH_OP = 23, IDENTIFIER = 24, AWAIT = 25, WS = 26, COMMENT = 27,
			LINE_COMMENT = 28;
	public static final int
		RULE_program = 0, RULE_atomicBlock = 1, RULE_block = 2, RULE_stmt = 3,
			RULE_condExp = 4, RULE_condDualExp = 5, RULE_whileStmt = 6, RULE_ifStmt = 7,
			RULE_elseStmt = 8, RULE_assignStmt = 9, RULE_awaitStmt = 10, RULE_compExp = 11,
			RULE_valueExp = 12, RULE_additionExp = 13, RULE_subExp = 14, RULE_multExp = 15,
			RULE_divExp = 16, RULE_singleValue = 17;
	private static String[] makeRuleNames() {
		return new String[] {
				"program", "atomicBlock", "block", "stmt", "condExp", "condDualExp",
				"whileStmt", "ifStmt", "elseStmt", "assignStmt", "awaitStmt", "compExp",
				"valueExp", "additionExp", "subExp", "multExp", "divExp", "singleValue"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
				null, "'='", "'{'", "'}'", "'('", "')'", "';'", "'if'", "'else'", "'while'",
				null, "'&&'", "'||'", "'=='", "'!='", "'<'", "'>'", "'>='", "'<='", "'*'",
				"'/'", "'//'", "'+'", "'-'", null, "'AWAIT'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "ASSIGN", "LBRA", "RBRA", "LPAR", "RPAR", "SC", "IF", "ELSE", "WHILE",
				"INT_LITERAL", "AND_OP", "OR_OP", "EQ_OP", "NE_OP", "LT_OP", "GT_OP",
				"GTE_OP", "LTE_OP", "MULT_MATH_OP", "DIV_MATH_OP", "CO_SEPARATOR", "ADD_MATH_OP",
				"SUB_MATH_OP", "IDENTIFIER", "AWAIT", "WS", "COMMENT", "LINE_COMMENT"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName()
	{
		return "SingleProgramLanguage.g4";
	}

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public SingleProgramLanguageParser(TokenStream input)
	{
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ProgramContext extends ParserRuleContext {
		public List<AtomicBlockContext> atomicBlock() {
			return getRuleContexts(AtomicBlockContext.class);
		}
		public AtomicBlockContext atomicBlock(int i) {
			return getRuleContext(AtomicBlockContext.class,i);
		}
		public List<BlockContext> block() {
			return getRuleContexts(BlockContext.class);
		}
		public BlockContext block(int i) {
			return getRuleContext(BlockContext.class,i);
		}

		public List<AwaitStmtContext> awaitStmt()
		{
			return getRuleContexts(AwaitStmtContext.class);
		}

		public AwaitStmtContext awaitStmt(int i)
		{
			return getRuleContext(AwaitStmtContext.class, i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof SingleProgramLanguageListener)
				((SingleProgramLanguageListener) listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof SingleProgramLanguageListener)
				((SingleProgramLanguageListener) listener).exitProgram(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof SingleProgramLanguageVisitor)
				return ((SingleProgramLanguageVisitor<? extends T>) visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(41);
			_errHandler.sync(this);
			_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << IF) | (1L << WHILE) | (1L << LT_OP) | (1L << IDENTIFIER))) != 0))
				{
				{
					setState(39);
				_errHandler.sync(this);
					switch (getInterpreter().adaptivePredict(_input, 0, _ctx))
					{
						case 1:
					{
						setState(36);
					atomicBlock();
					}
					break;
						case 2:
					{
						setState(37);
					block();
					}
					break;
						case 3:
						{
							setState(38);
							awaitStmt();
						}
						break;
				}
				}
					setState(43);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AtomicBlockContext extends ParserRuleContext {
		public TerminalNode LT_OP()
		{
			return getToken(SingleProgramLanguageParser.LT_OP, 0);
		}

		public TerminalNode GT_OP()
		{
			return getToken(SingleProgramLanguageParser.GT_OP, 0);
		}
		public List<StmtContext> stmt() {
			return getRuleContexts(StmtContext.class);
		}
		public StmtContext stmt(int i) {
			return getRuleContext(StmtContext.class,i);
		}
		public AtomicBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atomicBlock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof SingleProgramLanguageListener)
				((SingleProgramLanguageListener) listener).enterAtomicBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof SingleProgramLanguageListener)
				((SingleProgramLanguageListener) listener).exitAtomicBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof SingleProgramLanguageVisitor)
				return ((SingleProgramLanguageVisitor<? extends T>) visitor).visitAtomicBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AtomicBlockContext atomicBlock() throws RecognitionException {
		AtomicBlockContext _localctx = new AtomicBlockContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_atomicBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(44);
			match(LT_OP);
				setState(46);
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
					setState(45);
					stmt();
				}
				}
				setState(48); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << IF) | (1L << WHILE) | (1L << IDENTIFIER))) != 0));
				setState(50);
			match(GT_OP);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlockContext extends ParserRuleContext {
		public List<StmtContext> stmt() {
			return getRuleContexts(StmtContext.class);
		}
		public StmtContext stmt(int i) {
			return getRuleContext(StmtContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof SingleProgramLanguageListener)
				((SingleProgramLanguageListener) listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof SingleProgramLanguageListener)
				((SingleProgramLanguageListener) listener).exitBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof SingleProgramLanguageVisitor)
				return ((SingleProgramLanguageVisitor<? extends T>) visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_block);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
				setState(53);
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
						setState(52);
					stmt();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(55); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			} while ( _alt!=2 && _alt!= ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StmtContext extends ParserRuleContext {
		public WhileStmtContext whileStmt() {
			return getRuleContext(WhileStmtContext.class,0);
		}
		public IfStmtContext ifStmt() {
			return getRuleContext(IfStmtContext.class,0);
		}
		public AssignStmtContext assignStmt() {
			return getRuleContext(AssignStmtContext.class,0);
		}
		public StmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof SingleProgramLanguageListener)
				((SingleProgramLanguageListener) listener).enterStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof SingleProgramLanguageListener)
				((SingleProgramLanguageListener) listener).exitStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof SingleProgramLanguageVisitor)
				return ((SingleProgramLanguageVisitor<? extends T>) visitor).visitStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StmtContext stmt() throws RecognitionException {
		StmtContext _localctx = new StmtContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_stmt);
		try {
			setState(60);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case WHILE:
				enterOuterAlt(_localctx, 1);
				{
					setState(57);
				whileStmt();
				}
				break;
			case IF:
				enterOuterAlt(_localctx, 2);
				{
					setState(58);
				ifStmt();
				}
				break;
			case IDENTIFIER:
				enterOuterAlt(_localctx, 3);
				{
					setState(59);
				assignStmt();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CondExpContext extends ParserRuleContext {
		public CompExpContext compExp() {
			return getRuleContext(CompExpContext.class,0);
		}
		public CondDualExpContext condDualExp() {
			return getRuleContext(CondDualExpContext.class,0);
		}
		public CondExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_condExp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof SingleProgramLanguageListener)
				((SingleProgramLanguageListener) listener).enterCondExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof SingleProgramLanguageListener)
				((SingleProgramLanguageListener) listener).exitCondExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof SingleProgramLanguageVisitor)
				return ((SingleProgramLanguageVisitor<? extends T>) visitor).visitCondExp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CondExpContext condExp() throws RecognitionException {
		CondExpContext _localctx = new CondExpContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_condExp);
		try {
			setState(64);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
					setState(62);
				compExp();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
					setState(63);
				condDualExp();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CondDualExpContext extends ParserRuleContext {
		public List<CompExpContext> compExp() {
			return getRuleContexts(CompExpContext.class);
		}
		public CompExpContext compExp(int i) {
			return getRuleContext(CompExpContext.class,i);
		}

		public TerminalNode AND_OP()
		{
			return getToken(SingleProgramLanguageParser.AND_OP, 0);
		}

		public TerminalNode OR_OP()
		{
			return getToken(SingleProgramLanguageParser.OR_OP, 0);
		}
		public CondDualExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_condDualExp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof SingleProgramLanguageListener)
				((SingleProgramLanguageListener) listener).enterCondDualExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof SingleProgramLanguageListener)
				((SingleProgramLanguageListener) listener).exitCondDualExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof SingleProgramLanguageVisitor)
				return ((SingleProgramLanguageVisitor<? extends T>) visitor).visitCondDualExp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CondDualExpContext condDualExp() throws RecognitionException {
		CondDualExpContext _localctx = new CondDualExpContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_condDualExp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(66);
			compExp();
				setState(67);
			_la = _input.LA(1);
			if ( !(_la==AND_OP || _la==OR_OP) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
				setState(68);
			compExp();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WhileStmtContext extends ParserRuleContext {
		public TerminalNode WHILE()
		{
			return getToken(SingleProgramLanguageParser.WHILE, 0);
		}

		public TerminalNode LPAR()
		{
			return getToken(SingleProgramLanguageParser.LPAR, 0);
		}
		public CondExpContext condExp() {
			return getRuleContext(CondExpContext.class,0);
		}

		public TerminalNode RPAR()
		{
			return getToken(SingleProgramLanguageParser.RPAR, 0);
		}

		public TerminalNode LBRA()
		{
			return getToken(SingleProgramLanguageParser.LBRA, 0);
		}

		public TerminalNode RBRA()
		{
			return getToken(SingleProgramLanguageParser.RBRA, 0);
		}
		public List<StmtContext> stmt() {
			return getRuleContexts(StmtContext.class);
		}
		public StmtContext stmt(int i) {
			return getRuleContext(StmtContext.class,i);
		}
		public WhileStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whileStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof SingleProgramLanguageListener)
				((SingleProgramLanguageListener) listener).enterWhileStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof SingleProgramLanguageListener)
				((SingleProgramLanguageListener) listener).exitWhileStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof SingleProgramLanguageVisitor)
				return ((SingleProgramLanguageVisitor<? extends T>) visitor).visitWhileStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhileStmtContext whileStmt() throws RecognitionException {
		WhileStmtContext _localctx = new WhileStmtContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_whileStmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(70);
			match(WHILE);
				setState(71);
			match(LPAR);
				setState(72);
			condExp();
				setState(73);
			match(RPAR);
				setState(74);
			match(LBRA);
				setState(78);
			_errHandler.sync(this);
			_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << IF) | (1L << WHILE) | (1L << IDENTIFIER))) != 0))
				{
				{
				{
					setState(75);
				stmt();
				}
				}
					setState(80);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
				setState(81);
			match(RBRA);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IfStmtContext extends ParserRuleContext {
		public TerminalNode IF()
		{
			return getToken(SingleProgramLanguageParser.IF, 0);
		}

		public TerminalNode LPAR()
		{
			return getToken(SingleProgramLanguageParser.LPAR, 0);
		}
		public CondExpContext condExp() {
			return getRuleContext(CondExpContext.class,0);
		}

		public TerminalNode RPAR()
		{
			return getToken(SingleProgramLanguageParser.RPAR, 0);
		}

		public TerminalNode LBRA()
		{
			return getToken(SingleProgramLanguageParser.LBRA, 0);
		}

		public TerminalNode RBRA()
		{
			return getToken(SingleProgramLanguageParser.RBRA, 0);
		}
		public List<StmtContext> stmt() {
			return getRuleContexts(StmtContext.class);
		}
		public StmtContext stmt(int i) {
			return getRuleContext(StmtContext.class,i);
		}

		public ElseStmtContext elseStmt()
		{
			return getRuleContext(ElseStmtContext.class, 0);
		}
		public IfStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof SingleProgramLanguageListener)
				((SingleProgramLanguageListener) listener).enterIfStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof SingleProgramLanguageListener)
				((SingleProgramLanguageListener) listener).exitIfStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof SingleProgramLanguageVisitor)
				return ((SingleProgramLanguageVisitor<? extends T>) visitor).visitIfStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfStmtContext ifStmt() throws RecognitionException {
		IfStmtContext _localctx = new IfStmtContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_ifStmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(83);
			match(IF);
				setState(84);
			match(LPAR);
				setState(85);
			condExp();
				setState(86);
			match(RPAR);
				setState(87);
			match(LBRA);
				setState(91);
			_errHandler.sync(this);
			_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << IF) | (1L << WHILE) | (1L << IDENTIFIER))) != 0))
				{
				{
				{
					setState(88);
				stmt();
				}
				}
					setState(93);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
				setState(94);
			match(RBRA);
				setState(96);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ELSE) {
				{
					setState(95);
					elseStmt();
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

	public static class ElseStmtContext extends ParserRuleContext
	{
		public TerminalNode ELSE()
		{
			return getToken(SingleProgramLanguageParser.ELSE, 0);
		}

		public TerminalNode LBRA()
		{
			return getToken(SingleProgramLanguageParser.LBRA, 0);
		}

		public TerminalNode RBRA()
		{
			return getToken(SingleProgramLanguageParser.RBRA, 0);
		}

		public List<StmtContext> stmt()
		{
			return getRuleContexts(StmtContext.class);
		}

		public StmtContext stmt(int i)
		{
			return getRuleContext(StmtContext.class, i);
		}

		public ElseStmtContext(ParserRuleContext parent, int invokingState)
		{
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex()
		{
			return RULE_elseStmt;
		}
		@Override
		public void enterRule(ParseTreeListener listener)
		{
			if (listener instanceof SingleProgramLanguageListener)
				((SingleProgramLanguageListener) listener).enterElseStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener)
		{
			if (listener instanceof SingleProgramLanguageListener)
				((SingleProgramLanguageListener) listener).exitElseStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof SingleProgramLanguageVisitor)
				return ((SingleProgramLanguageVisitor<? extends T>) visitor).visitElseStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElseStmtContext elseStmt() throws RecognitionException
	{
		ElseStmtContext _localctx = new ElseStmtContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_elseStmt);
		int _la;
		try
		{
			enterOuterAlt(_localctx, 1);
			{
				setState(98);
				match(ELSE);
				setState(99);
				match(LBRA);
				setState(103);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << IF) | (1L << WHILE) | (1L << IDENTIFIER))) != 0))
				{
					{
						{
							setState(100);
							stmt();
						}
					}
					setState(105);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(106);
				match(RBRA);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignStmtContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER()
		{
			return getToken(SingleProgramLanguageParser.IDENTIFIER, 0);
		}

		public TerminalNode ASSIGN()
		{
			return getToken(SingleProgramLanguageParser.ASSIGN, 0);
		}
		public ValueExpContext valueExp() {
			return getRuleContext(ValueExpContext.class,0);
		}

		public TerminalNode SC()
		{
			return getToken(SingleProgramLanguageParser.SC, 0);
		}
		public AssignStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof SingleProgramLanguageListener)
				((SingleProgramLanguageListener) listener).enterAssignStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof SingleProgramLanguageListener)
				((SingleProgramLanguageListener) listener).exitAssignStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof SingleProgramLanguageVisitor)
				return ((SingleProgramLanguageVisitor<? extends T>) visitor).visitAssignStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignStmtContext assignStmt() throws RecognitionException {
		AssignStmtContext _localctx = new AssignStmtContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_assignStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(108);
			match(IDENTIFIER);
				setState(109);
			match(ASSIGN);
				setState(110);
			valueExp();
				setState(111);
			match(SC);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AwaitStmtContext extends ParserRuleContext {
		public List<TerminalNode> LT_OP()
		{
			return getTokens(SingleProgramLanguageParser.LT_OP);
		}

		public TerminalNode LT_OP(int i)
		{
			return getToken(SingleProgramLanguageParser.LT_OP, i);
		}

		public TerminalNode AWAIT()
		{
			return getToken(SingleProgramLanguageParser.AWAIT, 0);
		}

		public List<TerminalNode> IDENTIFIER()
		{
			return getTokens(SingleProgramLanguageParser.IDENTIFIER);
		}

		public TerminalNode IDENTIFIER(int i)
		{
			return getToken(SingleProgramLanguageParser.IDENTIFIER, i);
		}

		public TerminalNode SC()
		{
			return getToken(SingleProgramLanguageParser.SC, 0);
		}

		public List<TerminalNode> GT_OP()
		{
			return getTokens(SingleProgramLanguageParser.GT_OP);
		}

		public TerminalNode GT_OP(int i)
		{
			return getToken(SingleProgramLanguageParser.GT_OP, i);
		}

		public TerminalNode EQ_OP()
		{
			return getToken(SingleProgramLanguageParser.EQ_OP, 0);
		}

		public TerminalNode NE_OP()
		{
			return getToken(SingleProgramLanguageParser.NE_OP, 0);
		}

		public TerminalNode LTE_OP()
		{
			return getToken(SingleProgramLanguageParser.LTE_OP, 0);
		}

		public TerminalNode GTE_OP()
		{
			return getToken(SingleProgramLanguageParser.GTE_OP, 0);
		}
		public AwaitStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_awaitStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof SingleProgramLanguageListener)
				((SingleProgramLanguageListener) listener).enterAwaitStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof SingleProgramLanguageListener)
				((SingleProgramLanguageListener) listener).exitAwaitStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof SingleProgramLanguageVisitor)
				return ((SingleProgramLanguageVisitor<? extends T>) visitor).visitAwaitStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AwaitStmtContext awaitStmt() throws RecognitionException {
		AwaitStmtContext _localctx = new AwaitStmtContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_awaitStmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(113);
				match(LT_OP);
				setState(114);
			match(AWAIT);
				setState(115);
				match(IDENTIFIER);
				setState(116);
				_la = _input.LA(1);
				if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << EQ_OP) | (1L << NE_OP) | (1L << LT_OP) | (1L << GT_OP) | (1L << GTE_OP) | (1L << LTE_OP))) != 0)))
				{
					_errHandler.recoverInline(this);
				}
				else
				{
					if (_input.LA(1) == Token.EOF) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(117);
				match(IDENTIFIER);
				setState(118);
			match(SC);
				setState(119);
				match(GT_OP);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CompExpContext extends ParserRuleContext {
		public List<SingleValueContext> singleValue() {
			return getRuleContexts(SingleValueContext.class);
		}
		public SingleValueContext singleValue(int i) {
			return getRuleContext(SingleValueContext.class,i);
		}

		public TerminalNode EQ_OP()
		{
			return getToken(SingleProgramLanguageParser.EQ_OP, 0);
		}

		public TerminalNode NE_OP()
		{
			return getToken(SingleProgramLanguageParser.NE_OP, 0);
		}

		public TerminalNode LT_OP()
		{
			return getToken(SingleProgramLanguageParser.LT_OP, 0);
		}

		public TerminalNode GT_OP()
		{
			return getToken(SingleProgramLanguageParser.GT_OP, 0);
		}

		public TerminalNode GTE_OP()
		{
			return getToken(SingleProgramLanguageParser.GTE_OP, 0);
		}

		public TerminalNode LTE_OP()
		{
			return getToken(SingleProgramLanguageParser.LTE_OP, 0);
		}
		public CompExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compExp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof SingleProgramLanguageListener)
				((SingleProgramLanguageListener) listener).enterCompExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof SingleProgramLanguageListener)
				((SingleProgramLanguageListener) listener).exitCompExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof SingleProgramLanguageVisitor)
				return ((SingleProgramLanguageVisitor<? extends T>) visitor).visitCompExp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CompExpContext compExp() throws RecognitionException {
		CompExpContext _localctx = new CompExpContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_compExp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(121);
			singleValue();
				setState(122);
			_la = _input.LA(1);
				if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << EQ_OP) | (1L << NE_OP) | (1L << LT_OP) | (1L << GT_OP) | (1L << GTE_OP) | (1L << LTE_OP))) != 0)))
				{
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
				setState(123);
			singleValue();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ValueExpContext extends ParserRuleContext {
		public SingleValueContext singleValue() {
			return getRuleContext(SingleValueContext.class,0);
		}
		public AdditionExpContext additionExp() {
			return getRuleContext(AdditionExpContext.class,0);
		}
		public SubExpContext subExp() {
			return getRuleContext(SubExpContext.class,0);
		}
		public MultExpContext multExp() {
			return getRuleContext(MultExpContext.class,0);
		}
		public DivExpContext divExp() {
			return getRuleContext(DivExpContext.class,0);
		}
		public ValueExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_valueExp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof SingleProgramLanguageListener)
				((SingleProgramLanguageListener) listener).enterValueExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof SingleProgramLanguageListener)
				((SingleProgramLanguageListener) listener).exitValueExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof SingleProgramLanguageVisitor)
				return ((SingleProgramLanguageVisitor<? extends T>) visitor).visitValueExp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueExpContext valueExp() throws RecognitionException {
		ValueExpContext _localctx = new ValueExpContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_valueExp);
		try {
			setState(130);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
					setState(125);
				singleValue();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
					setState(126);
				additionExp();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
					setState(127);
				subExp();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
					setState(128);
				multExp();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
					setState(129);
				divExp();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AdditionExpContext extends ParserRuleContext {
		public List<SingleValueContext> singleValue() {
			return getRuleContexts(SingleValueContext.class);
		}
		public SingleValueContext singleValue(int i) {
			return getRuleContext(SingleValueContext.class,i);
		}

		public TerminalNode ADD_MATH_OP()
		{
			return getToken(SingleProgramLanguageParser.ADD_MATH_OP, 0);
		}
		public AdditionExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_additionExp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof SingleProgramLanguageListener)
				((SingleProgramLanguageListener) listener).enterAdditionExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof SingleProgramLanguageListener)
				((SingleProgramLanguageListener) listener).exitAdditionExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof SingleProgramLanguageVisitor)
				return ((SingleProgramLanguageVisitor<? extends T>) visitor).visitAdditionExp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AdditionExpContext additionExp() throws RecognitionException {
		AdditionExpContext _localctx = new AdditionExpContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_additionExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(132);
			singleValue();
				setState(133);
			match(ADD_MATH_OP);
				setState(134);
			singleValue();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SubExpContext extends ParserRuleContext {
		public List<SingleValueContext> singleValue() {
			return getRuleContexts(SingleValueContext.class);
		}
		public SingleValueContext singleValue(int i) {
			return getRuleContext(SingleValueContext.class,i);
		}

		public TerminalNode SUB_MATH_OP()
		{
			return getToken(SingleProgramLanguageParser.SUB_MATH_OP, 0);
		}
		public SubExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subExp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof SingleProgramLanguageListener)
				((SingleProgramLanguageListener) listener).enterSubExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof SingleProgramLanguageListener)
				((SingleProgramLanguageListener) listener).exitSubExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof SingleProgramLanguageVisitor)
				return ((SingleProgramLanguageVisitor<? extends T>) visitor).visitSubExp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SubExpContext subExp() throws RecognitionException {
		SubExpContext _localctx = new SubExpContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_subExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(136);
			singleValue();
				setState(137);
			match(SUB_MATH_OP);
				setState(138);
			singleValue();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MultExpContext extends ParserRuleContext {
		public List<SingleValueContext> singleValue() {
			return getRuleContexts(SingleValueContext.class);
		}
		public SingleValueContext singleValue(int i) {
			return getRuleContext(SingleValueContext.class,i);
		}

		public TerminalNode MULT_MATH_OP()
		{
			return getToken(SingleProgramLanguageParser.MULT_MATH_OP, 0);
		}
		public MultExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multExp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof SingleProgramLanguageListener)
				((SingleProgramLanguageListener) listener).enterMultExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof SingleProgramLanguageListener)
				((SingleProgramLanguageListener) listener).exitMultExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof SingleProgramLanguageVisitor)
				return ((SingleProgramLanguageVisitor<? extends T>) visitor).visitMultExp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MultExpContext multExp() throws RecognitionException {
		MultExpContext _localctx = new MultExpContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_multExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(140);
			singleValue();
				setState(141);
			match(MULT_MATH_OP);
				setState(142);
			singleValue();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DivExpContext extends ParserRuleContext {
		public List<SingleValueContext> singleValue() {
			return getRuleContexts(SingleValueContext.class);
		}
		public SingleValueContext singleValue(int i) {
			return getRuleContext(SingleValueContext.class,i);
		}

		public TerminalNode DIV_MATH_OP()
		{
			return getToken(SingleProgramLanguageParser.DIV_MATH_OP, 0);
		}
		public DivExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_divExp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof SingleProgramLanguageListener)
				((SingleProgramLanguageListener) listener).enterDivExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof SingleProgramLanguageListener)
				((SingleProgramLanguageListener) listener).exitDivExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof SingleProgramLanguageVisitor)
				return ((SingleProgramLanguageVisitor<? extends T>) visitor).visitDivExp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DivExpContext divExp() throws RecognitionException {
		DivExpContext _localctx = new DivExpContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_divExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(144);
			singleValue();
				setState(145);
			match(DIV_MATH_OP);
				setState(146);
			singleValue();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SingleValueContext extends ParserRuleContext {
		public TerminalNode INT_LITERAL()
		{
			return getToken(SingleProgramLanguageParser.INT_LITERAL, 0);
		}

		public TerminalNode IDENTIFIER()
		{
			return getToken(SingleProgramLanguageParser.IDENTIFIER, 0);
		}
		public SingleValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_singleValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof SingleProgramLanguageListener)
				((SingleProgramLanguageListener) listener).enterSingleValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof SingleProgramLanguageListener)
				((SingleProgramLanguageListener) listener).exitSingleValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof SingleProgramLanguageVisitor)
				return ((SingleProgramLanguageVisitor<? extends T>) visitor).visitSingleValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SingleValueContext singleValue() throws RecognitionException {
		SingleValueContext _localctx = new SingleValueContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_singleValue);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(148);
			_la = _input.LA(1);
			if ( !(_la==INT_LITERAL || _la==IDENTIFIER) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
			"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\36\u0099\4\2\t\2" +
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
					"\4\23\t\23\3\2\3\2\3\2\7\2*\n\2\f\2\16\2-\13\2\3\3\3\3\6\3\61\n\3\r\3" +
					"\16\3\62\3\3\3\3\3\4\6\48\n\4\r\4\16\49\3\5\3\5\3\5\5\5?\n\5\3\6\3\6\5" +
					"\6C\n\6\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\7\bO\n\b\f\b\16\bR\13" +
					"\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\7\t\\\n\t\f\t\16\t_\13\t\3\t\3\t\5" +
					"\tc\n\t\3\n\3\n\3\n\7\nh\n\n\f\n\16\nk\13\n\3\n\3\n\3\13\3\13\3\13\3\13" +
					"\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3" +
					"\16\3\16\5\16\u0085\n\16\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\21" +
					"\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\23\3\23\3\23\2\2\24\2\4\6\b\n\f" +
					"\16\20\22\24\26\30\32\34\36 \"$\2\5\3\2\r\16\3\2\17\24\4\2\f\f\32\32\2" +
					"\u0096\2+\3\2\2\2\4.\3\2\2\2\6\67\3\2\2\2\b>\3\2\2\2\nB\3\2\2\2\fD\3\2" +
					"\2\2\16H\3\2\2\2\20U\3\2\2\2\22d\3\2\2\2\24n\3\2\2\2\26s\3\2\2\2\30{\3" +
					"\2\2\2\32\u0084\3\2\2\2\34\u0086\3\2\2\2\36\u008a\3\2\2\2 \u008e\3\2\2" +
					"\2\"\u0092\3\2\2\2$\u0096\3\2\2\2&*\5\4\3\2\'*\5\6\4\2(*\5\26\f\2)&\3" +
					"\2\2\2)\'\3\2\2\2)(\3\2\2\2*-\3\2\2\2+)\3\2\2\2+,\3\2\2\2,\3\3\2\2\2-" +
					"+\3\2\2\2.\60\7\21\2\2/\61\5\b\5\2\60/\3\2\2\2\61\62\3\2\2\2\62\60\3\2" +
					"\2\2\62\63\3\2\2\2\63\64\3\2\2\2\64\65\7\22\2\2\65\5\3\2\2\2\668\5\b\5" +
					"\2\67\66\3\2\2\289\3\2\2\29\67\3\2\2\29:\3\2\2\2:\7\3\2\2\2;?\5\16\b\2" +
					"<?\5\20\t\2=?\5\24\13\2>;\3\2\2\2><\3\2\2\2>=\3\2\2\2?\t\3\2\2\2@C\5\30" +
					"\r\2AC\5\f\7\2B@\3\2\2\2BA\3\2\2\2C\13\3\2\2\2DE\5\30\r\2EF\t\2\2\2FG" +
					"\5\30\r\2G\r\3\2\2\2HI\7\13\2\2IJ\7\6\2\2JK\5\n\6\2KL\7\7\2\2LP\7\4\2" +
					"\2MO\5\b\5\2NM\3\2\2\2OR\3\2\2\2PN\3\2\2\2PQ\3\2\2\2QS\3\2\2\2RP\3\2\2" +
					"\2ST\7\5\2\2T\17\3\2\2\2UV\7\t\2\2VW\7\6\2\2WX\5\n\6\2XY\7\7\2\2Y]\7\4" +
					"\2\2Z\\\5\b\5\2[Z\3\2\2\2\\_\3\2\2\2][\3\2\2\2]^\3\2\2\2^`\3\2\2\2_]\3" +
					"\2\2\2`b\7\5\2\2ac\5\22\n\2ba\3\2\2\2bc\3\2\2\2c\21\3\2\2\2de\7\n\2\2" +
					"ei\7\4\2\2fh\5\b\5\2gf\3\2\2\2hk\3\2\2\2ig\3\2\2\2ij\3\2\2\2jl\3\2\2\2" +
					"ki\3\2\2\2lm\7\5\2\2m\23\3\2\2\2no\7\32\2\2op\7\3\2\2pq\5\32\16\2qr\7" +
					"\b\2\2r\25\3\2\2\2st\7\21\2\2tu\7\33\2\2uv\7\32\2\2vw\t\3\2\2wx\7\32\2" +
					"\2xy\7\b\2\2yz\7\22\2\2z\27\3\2\2\2{|\5$\23\2|}\t\3\2\2}~\5$\23\2~\31" +
					"\3\2\2\2\177\u0085\5$\23\2\u0080\u0085\5\34\17\2\u0081\u0085\5\36\20\2" +
					"\u0082\u0085\5 \21\2\u0083\u0085\5\"\22\2\u0084\177\3\2\2\2\u0084\u0080" +
					"\3\2\2\2\u0084\u0081\3\2\2\2\u0084\u0082\3\2\2\2\u0084\u0083\3\2\2\2\u0085" +
					"\33\3\2\2\2\u0086\u0087\5$\23\2\u0087\u0088\7\30\2\2\u0088\u0089\5$\23" +
					"\2\u0089\35\3\2\2\2\u008a\u008b\5$\23\2\u008b\u008c\7\31\2\2\u008c\u008d" +
					"\5$\23\2\u008d\37\3\2\2\2\u008e\u008f\5$\23\2\u008f\u0090\7\25\2\2\u0090" +
					"\u0091\5$\23\2\u0091!\3\2\2\2\u0092\u0093\5$\23\2\u0093\u0094\7\26\2\2" +
					"\u0094\u0095\5$\23\2\u0095#\3\2\2\2\u0096\u0097\t\4\2\2\u0097%\3\2\2\2" +
					"\r)+\629>BP]bi\u0084";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
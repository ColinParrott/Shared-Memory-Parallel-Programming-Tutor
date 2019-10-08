package com.colinparrott.parallelsimulator.engine.compiler.antlrgen;// Generated from C:/Users/Colin/IdeaProjects/tutor-test\highLanguage.g4 by ANTLR 4.7.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link highLanguageParser}.
 */
public interface highLanguageListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link highLanguageParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(highLanguageParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link highLanguageParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(highLanguageParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link highLanguageParser#atomicBlock}.
	 * @param ctx the parse tree
	 */
	void enterAtomicBlock(highLanguageParser.AtomicBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link highLanguageParser#atomicBlock}.
	 * @param ctx the parse tree
	 */
	void exitAtomicBlock(highLanguageParser.AtomicBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link highLanguageParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(highLanguageParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link highLanguageParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(highLanguageParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link highLanguageParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterStmt(highLanguageParser.StmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link highLanguageParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitStmt(highLanguageParser.StmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link highLanguageParser#condExp}.
	 * @param ctx the parse tree
	 */
	void enterCondExp(highLanguageParser.CondExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link highLanguageParser#condExp}.
	 * @param ctx the parse tree
	 */
	void exitCondExp(highLanguageParser.CondExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link highLanguageParser#condDualExp}.
	 * @param ctx the parse tree
	 */
	void enterCondDualExp(highLanguageParser.CondDualExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link highLanguageParser#condDualExp}.
	 * @param ctx the parse tree
	 */
	void exitCondDualExp(highLanguageParser.CondDualExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link highLanguageParser#whileStmt}.
	 * @param ctx the parse tree
	 */
	void enterWhileStmt(highLanguageParser.WhileStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link highLanguageParser#whileStmt}.
	 * @param ctx the parse tree
	 */
	void exitWhileStmt(highLanguageParser.WhileStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link highLanguageParser#ifStmt}.
	 * @param ctx the parse tree
	 */
	void enterIfStmt(highLanguageParser.IfStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link highLanguageParser#ifStmt}.
	 * @param ctx the parse tree
	 */
	void exitIfStmt(highLanguageParser.IfStmtContext ctx);
	/**
     * Enter a parse tree produced by {@link highLanguageParser#elseStmt}.
     * @param ctx the parse tree
     */
    void enterElseStmt(highLanguageParser.ElseStmtContext ctx);

    /**
     * Exit a parse tree produced by {@link highLanguageParser#elseStmt}.
     *
     * @param ctx the parse tree
     */
    void exitElseStmt(highLanguageParser.ElseStmtContext ctx);

    /**
	 * Enter a parse tree produced by {@link highLanguageParser#assignStmt}.
	 * @param ctx the parse tree
	 */
	void enterAssignStmt(highLanguageParser.AssignStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link highLanguageParser#assignStmt}.
	 * @param ctx the parse tree
	 */
	void exitAssignStmt(highLanguageParser.AssignStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link highLanguageParser#awaitStmt}.
	 * @param ctx the parse tree
	 */
	void enterAwaitStmt(highLanguageParser.AwaitStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link highLanguageParser#awaitStmt}.
	 * @param ctx the parse tree
	 */
	void exitAwaitStmt(highLanguageParser.AwaitStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link highLanguageParser#compExp}.
	 * @param ctx the parse tree
	 */
	void enterCompExp(highLanguageParser.CompExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link highLanguageParser#compExp}.
	 * @param ctx the parse tree
	 */
	void exitCompExp(highLanguageParser.CompExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link highLanguageParser#valueExp}.
	 * @param ctx the parse tree
	 */
	void enterValueExp(highLanguageParser.ValueExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link highLanguageParser#valueExp}.
	 * @param ctx the parse tree
	 */
	void exitValueExp(highLanguageParser.ValueExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link highLanguageParser#additionExp}.
	 * @param ctx the parse tree
	 */
	void enterAdditionExp(highLanguageParser.AdditionExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link highLanguageParser#additionExp}.
	 * @param ctx the parse tree
	 */
	void exitAdditionExp(highLanguageParser.AdditionExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link highLanguageParser#subExp}.
	 * @param ctx the parse tree
	 */
	void enterSubExp(highLanguageParser.SubExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link highLanguageParser#subExp}.
	 * @param ctx the parse tree
	 */
	void exitSubExp(highLanguageParser.SubExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link highLanguageParser#multExp}.
	 * @param ctx the parse tree
	 */
	void enterMultExp(highLanguageParser.MultExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link highLanguageParser#multExp}.
	 * @param ctx the parse tree
	 */
	void exitMultExp(highLanguageParser.MultExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link highLanguageParser#divExp}.
	 * @param ctx the parse tree
	 */
	void enterDivExp(highLanguageParser.DivExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link highLanguageParser#divExp}.
	 * @param ctx the parse tree
	 */
	void exitDivExp(highLanguageParser.DivExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link highLanguageParser#singleValue}.
	 * @param ctx the parse tree
	 */
	void enterSingleValue(highLanguageParser.SingleValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link highLanguageParser#singleValue}.
	 * @param ctx the parse tree
	 */
	void exitSingleValue(highLanguageParser.SingleValueContext ctx);
}
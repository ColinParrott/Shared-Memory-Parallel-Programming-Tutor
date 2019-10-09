package com.colinparrott.parallelsimulator.compiler.multithreadcompiler;// Generated from C:/Users/Colin/IdeaProjects/tutor-test\MultipleThreadsLanguage.g4 by ANTLR 4.7.2

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MultipleThreadsLanguageParser}.
 */
public interface MultipleThreadsLanguageListener extends ParseTreeListener
{
    /**
     * Enter a parse tree produced by {@link MultipleThreadsLanguageParser#topProgram}.
     *
     * @param ctx the parse tree
     */
    void enterTopProgram(MultipleThreadsLanguageParser.TopProgramContext ctx);

    /**
     * Exit a parse tree produced by {@link MultipleThreadsLanguageParser#topProgram}.
     *
     * @param ctx the parse tree
     */
    void exitTopProgram(MultipleThreadsLanguageParser.TopProgramContext ctx);

    /**
     * Enter a parse tree produced by {@link MultipleThreadsLanguageParser#program}.
     *
     * @param ctx the parse tree
     */
    void enterProgram(MultipleThreadsLanguageParser.ProgramContext ctx);

    /**
     * Exit a parse tree produced by {@link MultipleThreadsLanguageParser#program}.
     *
     * @param ctx the parse tree
     */
    void exitProgram(MultipleThreadsLanguageParser.ProgramContext ctx);

    /**
     * Enter a parse tree produced by {@link MultipleThreadsLanguageParser#atomicBlock}.
     *
     * @param ctx the parse tree
     */
    void enterAtomicBlock(MultipleThreadsLanguageParser.AtomicBlockContext ctx);

    /**
     * Exit a parse tree produced by {@link MultipleThreadsLanguageParser#atomicBlock}.
     *
     * @param ctx the parse tree
     */
    void exitAtomicBlock(MultipleThreadsLanguageParser.AtomicBlockContext ctx);

    /**
     * Enter a parse tree produced by {@link MultipleThreadsLanguageParser#block}.
     *
     * @param ctx the parse tree
     */
    void enterBlock(MultipleThreadsLanguageParser.BlockContext ctx);

    /**
     * Exit a parse tree produced by {@link MultipleThreadsLanguageParser#block}.
     *
     * @param ctx the parse tree
     */
    void exitBlock(MultipleThreadsLanguageParser.BlockContext ctx);

    /**
     * Enter a parse tree produced by {@link MultipleThreadsLanguageParser#stmt}.
     *
     * @param ctx the parse tree
     */
    void enterStmt(MultipleThreadsLanguageParser.StmtContext ctx);

    /**
     * Exit a parse tree produced by {@link MultipleThreadsLanguageParser#stmt}.
     *
     * @param ctx the parse tree
     */
    void exitStmt(MultipleThreadsLanguageParser.StmtContext ctx);

    /**
     * Enter a parse tree produced by {@link MultipleThreadsLanguageParser#condExp}.
     *
     * @param ctx the parse tree
     */
    void enterCondExp(MultipleThreadsLanguageParser.CondExpContext ctx);

    /**
     * Exit a parse tree produced by {@link MultipleThreadsLanguageParser#condExp}.
     *
     * @param ctx the parse tree
     */
    void exitCondExp(MultipleThreadsLanguageParser.CondExpContext ctx);

    /**
     * Enter a parse tree produced by {@link MultipleThreadsLanguageParser#condDualExp}.
     *
     * @param ctx the parse tree
     */
    void enterCondDualExp(MultipleThreadsLanguageParser.CondDualExpContext ctx);

    /**
     * Exit a parse tree produced by {@link MultipleThreadsLanguageParser#condDualExp}.
     *
     * @param ctx the parse tree
     */
    void exitCondDualExp(MultipleThreadsLanguageParser.CondDualExpContext ctx);

    /**
     * Enter a parse tree produced by {@link MultipleThreadsLanguageParser#whileStmt}.
     *
     * @param ctx the parse tree
     */
    void enterWhileStmt(MultipleThreadsLanguageParser.WhileStmtContext ctx);

    /**
     * Exit a parse tree produced by {@link MultipleThreadsLanguageParser#whileStmt}.
     *
     * @param ctx the parse tree
     */
    void exitWhileStmt(MultipleThreadsLanguageParser.WhileStmtContext ctx);

    /**
     * Enter a parse tree produced by {@link MultipleThreadsLanguageParser#ifStmt}.
     *
     * @param ctx the parse tree
     */
    void enterIfStmt(MultipleThreadsLanguageParser.IfStmtContext ctx);

    /**
     * Exit a parse tree produced by {@link MultipleThreadsLanguageParser#ifStmt}.
     *
     * @param ctx the parse tree
     */
    void exitIfStmt(MultipleThreadsLanguageParser.IfStmtContext ctx);

    /**
     * Enter a parse tree produced by {@link MultipleThreadsLanguageParser#elseStmt}.
     *
     * @param ctx the parse tree
     */
    void enterElseStmt(MultipleThreadsLanguageParser.ElseStmtContext ctx);

    /**
     * Exit a parse tree produced by {@link MultipleThreadsLanguageParser#elseStmt}.
     *
     * @param ctx the parse tree
     */
    void exitElseStmt(MultipleThreadsLanguageParser.ElseStmtContext ctx);

    /**
     * Enter a parse tree produced by {@link MultipleThreadsLanguageParser#assignStmt}.
     *
     * @param ctx the parse tree
     */
    void enterAssignStmt(MultipleThreadsLanguageParser.AssignStmtContext ctx);

    /**
     * Exit a parse tree produced by {@link MultipleThreadsLanguageParser#assignStmt}.
     *
     * @param ctx the parse tree
     */
    void exitAssignStmt(MultipleThreadsLanguageParser.AssignStmtContext ctx);

    /**
     * Enter a parse tree produced by {@link MultipleThreadsLanguageParser#awaitStmt}.
     *
     * @param ctx the parse tree
     */
    void enterAwaitStmt(MultipleThreadsLanguageParser.AwaitStmtContext ctx);

    /**
     * Exit a parse tree produced by {@link MultipleThreadsLanguageParser#awaitStmt}.
     *
     * @param ctx the parse tree
     */
    void exitAwaitStmt(MultipleThreadsLanguageParser.AwaitStmtContext ctx);

    /**
     * Enter a parse tree produced by {@link MultipleThreadsLanguageParser#compExp}.
     *
     * @param ctx the parse tree
     */
    void enterCompExp(MultipleThreadsLanguageParser.CompExpContext ctx);

    /**
     * Exit a parse tree produced by {@link MultipleThreadsLanguageParser#compExp}.
     *
     * @param ctx the parse tree
     */
    void exitCompExp(MultipleThreadsLanguageParser.CompExpContext ctx);

    /**
     * Enter a parse tree produced by {@link MultipleThreadsLanguageParser#valueExp}.
     *
     * @param ctx the parse tree
     */
    void enterValueExp(MultipleThreadsLanguageParser.ValueExpContext ctx);

    /**
     * Exit a parse tree produced by {@link MultipleThreadsLanguageParser#valueExp}.
     *
     * @param ctx the parse tree
     */
    void exitValueExp(MultipleThreadsLanguageParser.ValueExpContext ctx);

    /**
     * Enter a parse tree produced by {@link MultipleThreadsLanguageParser#additionExp}.
     *
     * @param ctx the parse tree
     */
    void enterAdditionExp(MultipleThreadsLanguageParser.AdditionExpContext ctx);

    /**
     * Exit a parse tree produced by {@link MultipleThreadsLanguageParser#additionExp}.
     *
     * @param ctx the parse tree
     */
    void exitAdditionExp(MultipleThreadsLanguageParser.AdditionExpContext ctx);

    /**
     * Enter a parse tree produced by {@link MultipleThreadsLanguageParser#subExp}.
     *
     * @param ctx the parse tree
     */
    void enterSubExp(MultipleThreadsLanguageParser.SubExpContext ctx);

    /**
     * Exit a parse tree produced by {@link MultipleThreadsLanguageParser#subExp}.
     *
     * @param ctx the parse tree
     */
    void exitSubExp(MultipleThreadsLanguageParser.SubExpContext ctx);

    /**
     * Enter a parse tree produced by {@link MultipleThreadsLanguageParser#multExp}.
     *
     * @param ctx the parse tree
     */
    void enterMultExp(MultipleThreadsLanguageParser.MultExpContext ctx);

    /**
     * Exit a parse tree produced by {@link MultipleThreadsLanguageParser#multExp}.
     *
     * @param ctx the parse tree
     */
    void exitMultExp(MultipleThreadsLanguageParser.MultExpContext ctx);

    /**
     * Enter a parse tree produced by {@link MultipleThreadsLanguageParser#divExp}.
     *
     * @param ctx the parse tree
     */
    void enterDivExp(MultipleThreadsLanguageParser.DivExpContext ctx);

    /**
     * Exit a parse tree produced by {@link MultipleThreadsLanguageParser#divExp}.
     *
     * @param ctx the parse tree
     */
    void exitDivExp(MultipleThreadsLanguageParser.DivExpContext ctx);

    /**
     * Enter a parse tree produced by {@link MultipleThreadsLanguageParser#singleValue}.
     *
     * @param ctx the parse tree
     */
    void enterSingleValue(MultipleThreadsLanguageParser.SingleValueContext ctx);

    /**
     * Exit a parse tree produced by {@link MultipleThreadsLanguageParser#singleValue}.
     *
     * @param ctx the parse tree
     */
    void exitSingleValue(MultipleThreadsLanguageParser.SingleValueContext ctx);
}
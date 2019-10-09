package com.colinparrott.parallelsimulator.compiler.multithreadcompiler;// Generated from C:/Users/Colin/IdeaProjects/tutor-test\MultipleThreadsLanguage.g4 by ANTLR 4.7.2

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MultipleThreadsLanguageParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 *            operations with no return type.
 */
public interface MultipleThreadsLanguageVisitor<T> extends ParseTreeVisitor<T>
{
    /**
     * Visit a parse tree produced by {@link MultipleThreadsLanguageParser#topProgram}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitTopProgram(MultipleThreadsLanguageParser.TopProgramContext ctx);

    /**
     * Visit a parse tree produced by {@link MultipleThreadsLanguageParser#program}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitProgram(MultipleThreadsLanguageParser.ProgramContext ctx);

    /**
     * Visit a parse tree produced by {@link MultipleThreadsLanguageParser#atomicBlock}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAtomicBlock(MultipleThreadsLanguageParser.AtomicBlockContext ctx);

    /**
     * Visit a parse tree produced by {@link MultipleThreadsLanguageParser#block}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitBlock(MultipleThreadsLanguageParser.BlockContext ctx);

    /**
     * Visit a parse tree produced by {@link MultipleThreadsLanguageParser#stmt}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitStmt(MultipleThreadsLanguageParser.StmtContext ctx);

    /**
     * Visit a parse tree produced by {@link MultipleThreadsLanguageParser#condExp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitCondExp(MultipleThreadsLanguageParser.CondExpContext ctx);

    /**
     * Visit a parse tree produced by {@link MultipleThreadsLanguageParser#condDualExp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitCondDualExp(MultipleThreadsLanguageParser.CondDualExpContext ctx);

    /**
     * Visit a parse tree produced by {@link MultipleThreadsLanguageParser#whileStmt}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitWhileStmt(MultipleThreadsLanguageParser.WhileStmtContext ctx);

    /**
     * Visit a parse tree produced by {@link MultipleThreadsLanguageParser#ifStmt}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitIfStmt(MultipleThreadsLanguageParser.IfStmtContext ctx);

    /**
     * Visit a parse tree produced by {@link MultipleThreadsLanguageParser#elseStmt}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitElseStmt(MultipleThreadsLanguageParser.ElseStmtContext ctx);

    /**
     * Visit a parse tree produced by {@link MultipleThreadsLanguageParser#assignStmt}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAssignStmt(MultipleThreadsLanguageParser.AssignStmtContext ctx);

    /**
     * Visit a parse tree produced by {@link MultipleThreadsLanguageParser#awaitStmt}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAwaitStmt(MultipleThreadsLanguageParser.AwaitStmtContext ctx);

    /**
     * Visit a parse tree produced by {@link MultipleThreadsLanguageParser#compExp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitCompExp(MultipleThreadsLanguageParser.CompExpContext ctx);

    /**
     * Visit a parse tree produced by {@link MultipleThreadsLanguageParser#valueExp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitValueExp(MultipleThreadsLanguageParser.ValueExpContext ctx);

    /**
     * Visit a parse tree produced by {@link MultipleThreadsLanguageParser#additionExp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAdditionExp(MultipleThreadsLanguageParser.AdditionExpContext ctx);

    /**
     * Visit a parse tree produced by {@link MultipleThreadsLanguageParser#subExp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSubExp(MultipleThreadsLanguageParser.SubExpContext ctx);

    /**
     * Visit a parse tree produced by {@link MultipleThreadsLanguageParser#multExp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitMultExp(MultipleThreadsLanguageParser.MultExpContext ctx);

    /**
     * Visit a parse tree produced by {@link MultipleThreadsLanguageParser#divExp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitDivExp(MultipleThreadsLanguageParser.DivExpContext ctx);

    /**
     * Visit a parse tree produced by {@link MultipleThreadsLanguageParser#singleValue}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSingleValue(MultipleThreadsLanguageParser.SingleValueContext ctx);
}
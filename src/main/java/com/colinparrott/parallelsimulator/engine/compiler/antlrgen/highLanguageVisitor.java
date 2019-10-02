package com.colinparrott.parallelsimulator.engine.compiler.antlrgen;// Generated from C:/Users/Colin/IdeaProjects/tutor-test\highLanguage.g4 by ANTLR 4.7.2

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link highLanguageParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 *            operations with no return type.
 */
public interface highLanguageVisitor<T> extends ParseTreeVisitor<T>
{
    /**
     * Visit a parse tree produced by {@link highLanguageParser#program}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitProgram(highLanguageParser.ProgramContext ctx);

    /**
     * Visit a parse tree produced by {@link highLanguageParser#atomicBlock}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAtomicBlock(highLanguageParser.AtomicBlockContext ctx);

    /**
     * Visit a parse tree produced by {@link highLanguageParser#block}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitBlock(highLanguageParser.BlockContext ctx);

    /**
     * Visit a parse tree produced by {@link highLanguageParser#stmt}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitStmt(highLanguageParser.StmtContext ctx);

    /**
     * Visit a parse tree produced by {@link highLanguageParser#condExp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitCondExp(highLanguageParser.CondExpContext ctx);

    /**
     * Visit a parse tree produced by {@link highLanguageParser#whileStmt}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitWhileStmt(highLanguageParser.WhileStmtContext ctx);

    /**
     * Visit a parse tree produced by {@link highLanguageParser#ifStmt}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitIfStmt(highLanguageParser.IfStmtContext ctx);

    /**
     * Visit a parse tree produced by {@link highLanguageParser#assignStmt}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAssignStmt(highLanguageParser.AssignStmtContext ctx);

    /**
     * Visit a parse tree produced by {@link highLanguageParser#awaitStmt}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAwaitStmt(highLanguageParser.AwaitStmtContext ctx);

    /**
     * Visit a parse tree produced by {@link highLanguageParser#compExp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitCompExp(highLanguageParser.CompExpContext ctx);

    /**
     * Visit a parse tree produced by {@link highLanguageParser#valueExp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitValueExp(highLanguageParser.ValueExpContext ctx);

    /**
     * Visit a parse tree produced by {@link highLanguageParser#additionExp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAdditionExp(highLanguageParser.AdditionExpContext ctx);

    /**
     * Visit a parse tree produced by {@link highLanguageParser#subExp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSubExp(highLanguageParser.SubExpContext ctx);

    /**
     * Visit a parse tree produced by {@link highLanguageParser#multExp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitMultExp(highLanguageParser.MultExpContext ctx);

    /**
     * Visit a parse tree produced by {@link highLanguageParser#divExp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitDivExp(highLanguageParser.DivExpContext ctx);

    /**
     * Visit a parse tree produced by {@link highLanguageParser#singleValue}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSingleValue(highLanguageParser.SingleValueContext ctx);
}
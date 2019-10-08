package com.colinparrott.parallelsimulator.compiler.singleprogramcompiler;// Generated from C:/Users/Colin/IdeaProjects/tutor-test\SingleProgramLanguage.g4 by ANTLR 4.7.2

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link SingleProgramLanguageParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 *            operations with no return type.
 */
public interface SingleProgramLanguageVisitor<T> extends ParseTreeVisitor<T>
{
    /**
     * Visit a parse tree produced by {@link SingleProgramLanguageParser#program}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitProgram(SingleProgramLanguageParser.ProgramContext ctx);

    /**
     * Visit a parse tree produced by {@link SingleProgramLanguageParser#atomicBlock}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAtomicBlock(SingleProgramLanguageParser.AtomicBlockContext ctx);

    /**
     * Visit a parse tree produced by {@link SingleProgramLanguageParser#block}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitBlock(SingleProgramLanguageParser.BlockContext ctx);

    /**
     * Visit a parse tree produced by {@link SingleProgramLanguageParser#stmt}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitStmt(SingleProgramLanguageParser.StmtContext ctx);

    /**
     * Visit a parse tree produced by {@link SingleProgramLanguageParser#condExp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitCondExp(SingleProgramLanguageParser.CondExpContext ctx);

    /**
     * Visit a parse tree produced by {@link SingleProgramLanguageParser#condDualExp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitCondDualExp(SingleProgramLanguageParser.CondDualExpContext ctx);

    /**
     * Visit a parse tree produced by {@link SingleProgramLanguageParser#whileStmt}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitWhileStmt(SingleProgramLanguageParser.WhileStmtContext ctx);

    /**
     * Visit a parse tree produced by {@link SingleProgramLanguageParser#ifStmt}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitIfStmt(SingleProgramLanguageParser.IfStmtContext ctx);

    /**
     * Visit a parse tree produced by {@link SingleProgramLanguageParser#elseStmt}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitElseStmt(SingleProgramLanguageParser.ElseStmtContext ctx);

    /**
     * Visit a parse tree produced by {@link SingleProgramLanguageParser#assignStmt}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAssignStmt(SingleProgramLanguageParser.AssignStmtContext ctx);

    /**
     * Visit a parse tree produced by {@link SingleProgramLanguageParser#awaitStmt}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAwaitStmt(SingleProgramLanguageParser.AwaitStmtContext ctx);

    /**
     * Visit a parse tree produced by {@link SingleProgramLanguageParser#compExp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitCompExp(SingleProgramLanguageParser.CompExpContext ctx);

    /**
     * Visit a parse tree produced by {@link SingleProgramLanguageParser#valueExp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitValueExp(SingleProgramLanguageParser.ValueExpContext ctx);

    /**
     * Visit a parse tree produced by {@link SingleProgramLanguageParser#additionExp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAdditionExp(SingleProgramLanguageParser.AdditionExpContext ctx);

    /**
     * Visit a parse tree produced by {@link SingleProgramLanguageParser#subExp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSubExp(SingleProgramLanguageParser.SubExpContext ctx);

    /**
     * Visit a parse tree produced by {@link SingleProgramLanguageParser#multExp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitMultExp(SingleProgramLanguageParser.MultExpContext ctx);

    /**
     * Visit a parse tree produced by {@link SingleProgramLanguageParser#divExp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitDivExp(SingleProgramLanguageParser.DivExpContext ctx);

    /**
     * Visit a parse tree produced by {@link SingleProgramLanguageParser#singleValue}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSingleValue(SingleProgramLanguageParser.SingleValueContext ctx);
}
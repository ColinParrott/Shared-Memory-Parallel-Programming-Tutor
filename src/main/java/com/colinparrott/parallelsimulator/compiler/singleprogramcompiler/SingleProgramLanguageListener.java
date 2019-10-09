package com.colinparrott.parallelsimulator.compiler.singleprogramcompiler;// Generated from C:/Users/Colin/IdeaProjects/tutor-test\SingleProgramLanguage.g4 by ANTLR 4.7.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SingleProgramLanguageParser}.
 */
public interface SingleProgramLanguageListener extends ParseTreeListener
{
    /**
     * Enter a parse tree produced by {@link SingleProgramLanguageParser#program}.
     *
     * @param ctx the parse tree
     */
    void enterProgram(SingleProgramLanguageParser.ProgramContext ctx);

    /**
     * Exit a parse tree produced by {@link SingleProgramLanguageParser#program}.
     *
     * @param ctx the parse tree
     */
    void exitProgram(SingleProgramLanguageParser.ProgramContext ctx);

    /**
     * Enter a parse tree produced by {@link SingleProgramLanguageParser#atomicBlock}.
     *
     * @param ctx the parse tree
     */
    void enterAtomicBlock(SingleProgramLanguageParser.AtomicBlockContext ctx);

    /**
     * Exit a parse tree produced by {@link SingleProgramLanguageParser#atomicBlock}.
     *
     * @param ctx the parse tree
     */
    void exitAtomicBlock(SingleProgramLanguageParser.AtomicBlockContext ctx);

    /**
     * Enter a parse tree produced by {@link SingleProgramLanguageParser#block}.
     *
     * @param ctx the parse tree
     */
    void enterBlock(SingleProgramLanguageParser.BlockContext ctx);

    /**
     * Exit a parse tree produced by {@link SingleProgramLanguageParser#block}.
     *
     * @param ctx the parse tree
     */
    void exitBlock(SingleProgramLanguageParser.BlockContext ctx);

    /**
     * Enter a parse tree produced by {@link SingleProgramLanguageParser#stmt}.
     *
     * @param ctx the parse tree
     */
    void enterStmt(SingleProgramLanguageParser.StmtContext ctx);

    /**
     * Exit a parse tree produced by {@link SingleProgramLanguageParser#stmt}.
     *
     * @param ctx the parse tree
     */
    void exitStmt(SingleProgramLanguageParser.StmtContext ctx);

    /**
     * Enter a parse tree produced by {@link SingleProgramLanguageParser#condExp}.
     *
     * @param ctx the parse tree
     */
    void enterCondExp(SingleProgramLanguageParser.CondExpContext ctx);

    /**
     * Exit a parse tree produced by {@link SingleProgramLanguageParser#condExp}.
     *
     * @param ctx the parse tree
     */
    void exitCondExp(SingleProgramLanguageParser.CondExpContext ctx);

    /**
     * Enter a parse tree produced by {@link SingleProgramLanguageParser#condDualExp}.
     *
     * @param ctx the parse tree
     */
    void enterCondDualExp(SingleProgramLanguageParser.CondDualExpContext ctx);

    /**
     * Exit a parse tree produced by {@link SingleProgramLanguageParser#condDualExp}.
     *
     * @param ctx the parse tree
     */
    void exitCondDualExp(SingleProgramLanguageParser.CondDualExpContext ctx);

    /**
     * Enter a parse tree produced by {@link SingleProgramLanguageParser#whileStmt}.
     *
     * @param ctx the parse tree
     */
    void enterWhileStmt(SingleProgramLanguageParser.WhileStmtContext ctx);

    /**
     * Exit a parse tree produced by {@link SingleProgramLanguageParser#whileStmt}.
     *
     * @param ctx the parse tree
     */
    void exitWhileStmt(SingleProgramLanguageParser.WhileStmtContext ctx);

    /**
     * Enter a parse tree produced by {@link SingleProgramLanguageParser#ifStmt}.
     *
     * @param ctx the parse tree
     */
    void enterIfStmt(SingleProgramLanguageParser.IfStmtContext ctx);

    /**
     * Exit a parse tree produced by {@link SingleProgramLanguageParser#ifStmt}.
     *
     * @param ctx the parse tree
     */
    void exitIfStmt(SingleProgramLanguageParser.IfStmtContext ctx);

    /**
     * Enter a parse tree produced by {@link SingleProgramLanguageParser#elseStmt}.
     *
     * @param ctx the parse tree
     */
    void enterElseStmt(SingleProgramLanguageParser.ElseStmtContext ctx);

    /**
     * Exit a parse tree produced by {@link SingleProgramLanguageParser#elseStmt}.
     *
     * @param ctx the parse tree
     */
    void exitElseStmt(SingleProgramLanguageParser.ElseStmtContext ctx);

    /**
     * Enter a parse tree produced by {@link SingleProgramLanguageParser#assignStmt}.
     *
     * @param ctx the parse tree
     */
    void enterAssignStmt(SingleProgramLanguageParser.AssignStmtContext ctx);

    /**
     * Exit a parse tree produced by {@link SingleProgramLanguageParser#assignStmt}.
     *
     * @param ctx the parse tree
     */
    void exitAssignStmt(SingleProgramLanguageParser.AssignStmtContext ctx);

    /**
     * Enter a parse tree produced by {@link SingleProgramLanguageParser#awaitStmt}.
     *
     * @param ctx the parse tree
     */
    void enterAwaitStmt(SingleProgramLanguageParser.AwaitStmtContext ctx);

    /**
     * Exit a parse tree produced by {@link SingleProgramLanguageParser#awaitStmt}.
     *
     * @param ctx the parse tree
     */
    void exitAwaitStmt(SingleProgramLanguageParser.AwaitStmtContext ctx);

    /**
     * Enter a parse tree produced by {@link SingleProgramLanguageParser#compExp}.
     *
     * @param ctx the parse tree
     */
    void enterCompExp(SingleProgramLanguageParser.CompExpContext ctx);

    /**
     * Exit a parse tree produced by {@link SingleProgramLanguageParser#compExp}.
     *
     * @param ctx the parse tree
     */
    void exitCompExp(SingleProgramLanguageParser.CompExpContext ctx);

    /**
     * Enter a parse tree produced by {@link SingleProgramLanguageParser#valueExp}.
     *
     * @param ctx the parse tree
     */
    void enterValueExp(SingleProgramLanguageParser.ValueExpContext ctx);

    /**
     * Exit a parse tree produced by {@link SingleProgramLanguageParser#valueExp}.
     *
     * @param ctx the parse tree
     */
    void exitValueExp(SingleProgramLanguageParser.ValueExpContext ctx);

    /**
     * Enter a parse tree produced by {@link SingleProgramLanguageParser#additionExp}.
     *
     * @param ctx the parse tree
     */
    void enterAdditionExp(SingleProgramLanguageParser.AdditionExpContext ctx);

    /**
     * Exit a parse tree produced by {@link SingleProgramLanguageParser#additionExp}.
     *
     * @param ctx the parse tree
     */
    void exitAdditionExp(SingleProgramLanguageParser.AdditionExpContext ctx);

    /**
     * Enter a parse tree produced by {@link SingleProgramLanguageParser#subExp}.
     *
     * @param ctx the parse tree
     */
    void enterSubExp(SingleProgramLanguageParser.SubExpContext ctx);

    /**
     * Exit a parse tree produced by {@link SingleProgramLanguageParser#subExp}.
     *
     * @param ctx the parse tree
     */
    void exitSubExp(SingleProgramLanguageParser.SubExpContext ctx);

    /**
     * Enter a parse tree produced by {@link SingleProgramLanguageParser#multExp}.
     *
     * @param ctx the parse tree
     */
    void enterMultExp(SingleProgramLanguageParser.MultExpContext ctx);

    /**
     * Exit a parse tree produced by {@link SingleProgramLanguageParser#multExp}.
     *
     * @param ctx the parse tree
     */
    void exitMultExp(SingleProgramLanguageParser.MultExpContext ctx);

    /**
     * Enter a parse tree produced by {@link SingleProgramLanguageParser#divExp}.
     *
     * @param ctx the parse tree
     */
    void enterDivExp(SingleProgramLanguageParser.DivExpContext ctx);

    /**
     * Exit a parse tree produced by {@link SingleProgramLanguageParser#divExp}.
     *
     * @param ctx the parse tree
     */
    void exitDivExp(SingleProgramLanguageParser.DivExpContext ctx);

    /**
     * Enter a parse tree produced by {@link SingleProgramLanguageParser#singleValue}.
     *
     * @param ctx the parse tree
     */
    void enterSingleValue(SingleProgramLanguageParser.SingleValueContext ctx);

    /**
     * Exit a parse tree produced by {@link SingleProgramLanguageParser#singleValue}.
     *
     * @param ctx the parse tree
     */
    void exitSingleValue(SingleProgramLanguageParser.SingleValueContext ctx);
}
package com.colinparrott.parallelsimulator.engine.compiler.ast;

public class BinOp extends Expr
{
    public final Expr leftExpr;
    public final Op op;
    public final Expr rightExpr;

    public BinOp(Expr leftExpr, Op op, Expr rightExpr)
    {
        this.leftExpr = leftExpr;
        this.op = op;
        this.rightExpr = rightExpr;
    }

    @Override
    public <T> T accept(ASTVisitor<T> v)
    {
        return v.visitBinOp(this);
    }
}

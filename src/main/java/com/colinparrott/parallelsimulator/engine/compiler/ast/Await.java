package com.colinparrott.parallelsimulator.engine.compiler.ast;

public class Await extends Expr
{
    public final Expr condExpr;

    public Await(Expr condExpr)
    {
        this.condExpr = condExpr;
    }

    @Override
    public <T> T accept(ASTVisitor<T> v)
    {
        return v.visitAwait(this);
    }
}

package com.colinparrott.parallelsimulator.engine.compiler.ast;

public class ExprStmt extends Stmt
{
    public final Expr expr;

    public ExprStmt(Expr expr)
    {
        this.expr = expr;
    }

    @Override
    public <T> T accept(ASTVisitor<T> v)
    {
        return v.visitExprStmt(this);
    }
}

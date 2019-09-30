package com.colinparrott.parallelsimulator.engine.compiler.ast;

public class Assign extends Stmt
{

    public final String varName;
    public final Expr right;

    public Assign(String varName, Expr right)
    {
        this.varName = varName;
        this.right = right;
    }

    @Override
    public <T> T accept(ASTVisitor<T> v)
    {
        return v.visitAssign(this);
    }
}

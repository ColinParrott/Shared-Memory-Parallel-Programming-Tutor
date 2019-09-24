package com.colinparrott.parallelsimulator.engine.compiler.ast;

import java.util.List;

public class Block extends Stmt
{

    public final List<Stmt> stmts;

    public Block(List<Stmt> stmts)
    {
        this.stmts = stmts;
    }

    public <T> T accept(ASTVisitor<T> v)
    {
        return v.visitBlock(this);
    }
}

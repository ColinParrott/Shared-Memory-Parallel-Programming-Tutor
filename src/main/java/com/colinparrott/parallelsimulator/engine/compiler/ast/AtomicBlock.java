package com.colinparrott.parallelsimulator.engine.compiler.ast;

import java.util.List;

public class AtomicBlock extends Block
{

    public final List<Stmt> stmts;

    public AtomicBlock(List<Stmt> stmts)
    {
        super(stmts);
        this.stmts = stmts;
    }

    public <T> T accept(ASTVisitor<T> v)
    {
        return v.visitBlock(this);
    }
}

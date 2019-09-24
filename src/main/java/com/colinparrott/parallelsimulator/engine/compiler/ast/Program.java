package com.colinparrott.parallelsimulator.engine.compiler.ast;

import java.util.List;

public class Program implements ASTNode
{

    public final List<VarDecl> varDecls;
    public final Block block;

    public Program(List<VarDecl> varDecls, Block block)
    {
        this.varDecls = varDecls;
        this.block = block;
    }

    public <T> T accept(ASTVisitor<T> v)
    {
        return v.visitProgram(this);
    }
}

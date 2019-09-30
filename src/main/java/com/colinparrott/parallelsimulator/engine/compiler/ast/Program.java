package com.colinparrott.parallelsimulator.engine.compiler.ast;

import java.util.List;

public class Program implements ASTNode
{

    public final List<VarDecl> varDecls;
    public final List<Block> blocks;

    public Program(List<VarDecl> varDecls, List<Block> blocks)
    {
        this.varDecls = varDecls;
        this.blocks = blocks;
    }

    public <T> T accept(ASTVisitor<T> v)
    {
        return v.visitProgram(this);
    }
}

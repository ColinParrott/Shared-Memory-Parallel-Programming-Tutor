package com.colinparrott.parallelsimulator.engine.compiler.ast;

public interface ASTNode
{
    public <T> T accept(ASTVisitor<T> v);
}

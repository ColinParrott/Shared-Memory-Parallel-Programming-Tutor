package com.colinparrott.parallelsimulator.engine.compiler.ast;

public interface Type extends ASTNode
{

    public <T> T accept(ASTVisitor<T> v);

}

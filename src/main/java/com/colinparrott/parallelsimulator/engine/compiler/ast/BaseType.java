package com.colinparrott.parallelsimulator.engine.compiler.ast;

public enum BaseType implements Type
{
    INT, CHAR, VOID;

    public <T> T accept(ASTVisitor<T> v)
    {
        return v.visitBaseType(this);
    }
}

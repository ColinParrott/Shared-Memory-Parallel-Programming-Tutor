package com.colinparrott.parallelsimulator.engine.compiler.ast;

public enum ErrorType implements Type
{
    ERROR_TYPE;

    @Override
    public <T> T accept(ASTVisitor<T> v)
    {
        return v.visitErrorType(this);
    }
}

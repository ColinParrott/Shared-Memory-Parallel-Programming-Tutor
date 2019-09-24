package com.colinparrott.parallelsimulator.engine.compiler.ast;


public class VarDecl implements ASTNode
{
    public String varName;
    public int value = 0;
    public Type type = BaseType.INT;

    public VarDecl(String varName, int value)
    {
        this.varName = varName;
        this.value = value;
    }

    public <T> T accept(ASTVisitor<T> v)
    {
        return v.visitVarDecl(this);
    }

    public void setVarName(String varName)
    {
        this.varName = varName;
    }
}

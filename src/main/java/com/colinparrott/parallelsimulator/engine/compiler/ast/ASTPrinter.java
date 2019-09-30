package com.colinparrott.parallelsimulator.engine.compiler.ast;

import java.io.PrintWriter;

public class ASTPrinter implements ASTVisitor<Void>
{

    private PrintWriter writer;

    public ASTPrinter(PrintWriter writer)
    {
        this.writer = writer;
    }

    @Override
    public Void visitBlock(Block b)
    {
        writer.print("Block(");
        String delimiter = "";
        for (Stmt stmt : b.stmts)
        {
            writer.print(delimiter);
            delimiter = ",";
            stmt.accept(this);
        }
        writer.print(")");
        return null;
    }

    @Override
    public Void visitAtomicBlock(AtomicBlock b)
    {
        writer.print("AtomicBlock(");
        String delimiter = "";
        for (Stmt stmt : b.stmts)
        {
            writer.print(delimiter);
            delimiter = ",";
            stmt.accept(this);
        }
        writer.print(")");
        return null;
    }


    @Override
    public Void visitProgram(Program p)
    {
        writer.print("Program(");
        String delimiter = "";
        for (VarDecl vd : p.varDecls)
        {
            writer.print(delimiter);
            delimiter = ",";
            vd.accept(this);
        }
        writer.print(")");
        writer.flush();
        return null;
    }

    @Override
    public Void visitVarDecl(VarDecl vd)
    {
        writer.print("VarDecl(");
        vd.type.accept(this);
        writer.print("," + vd.varName);
        writer.print("," + vd.value);
        writer.print(")");
        return null;
    }

    @Override
    public Void visitVarExpr(VarExpr v)
    {
        writer.print("VarExpr(");
        writer.print(v.name);
        writer.print(")");
        return null;
    }

    @Override
    public Void visitWhile(While aWhile)
    {
        writer.print("While(");
        aWhile.expr.accept(this);
        writer.print(",");
        aWhile.stmt.accept(this);
        writer.print(")");
        return null;
    }

    @Override
    public Void visitIntLiteral(IntLiteral intLiteral)
    {
        writer.print("IntLiteral(" + intLiteral.value + ")");
        return null;
    }

    @Override
    public Void visitOp(Op op)
    {
        writer.print(op);
        return null;
    }

    @Override
    public Void visitBinOp(BinOp binOp)
    {
        writer.print("BinOp(");
        binOp.leftExpr.accept(this);
        writer.print(",");
        binOp.op.accept(this);
        writer.print(",");
        binOp.rightExpr.accept(this);
        writer.print(")");
        return null;
    }

    @Override
    public Void visitExprStmt(ExprStmt exprStmt)
    {
        writer.print("ExprStmt(");
        exprStmt.expr.accept(this);
        writer.print(")");
        return null;
    }

    @Override
    public Void visitIf(If anIf)
    {
        writer.print("If(");
        anIf.expr.accept(this);
        writer.print(",");
        anIf.ifStmt.accept(this);

        // Else optional
        if (anIf.elseStmt != null)
        {
            writer.print(",");
            anIf.elseStmt.accept(this);
        }

        writer.print(")");
        return null;
    }

    @Override
    public Void visitAssign(Assign assign)
    {
        writer.print("Assign(");
        writer.print(assign.varName);
        writer.print(",");
        assign.right.accept(this);
        writer.print(")");
        return null;
    }

    @Override
    public Void visitErrorType(ErrorType errorType)
    {
        return null;
    }

    @Override
    public Void visitAwait(Await await)
    {
        return null;
    }

    @Override
    public Void visitBaseType(BaseType bt)
    {
        writer.print(bt);
        return null;
    }


    // to complete ...

}

package com.colinparrott.parallelsimulator.engine.compiler.ast;

import java.io.PrintWriter;
import java.util.ArrayList;

public class DotPrinter implements ASTVisitor<String>
{
    PrintWriter writer;
    int nodeCount = 0;

    public DotPrinter(PrintWriter printWriter)
    {
        this.writer = printWriter;
    }

    @Override
    public String visitBaseType(BaseType bt)
    {
        String baseTypeID = "Node" + nodeCount++;
        writer.println(baseTypeID + "[label=\"" + bt + "\"];");
        return baseTypeID;
    }

    @Override
    public String visitBlock(Block b)
    {
        String blockNodeId = "Node" + nodeCount++;
        writer.println(blockNodeId + "[label=\"Block\"];");

        ArrayList<String> nodeIds = new ArrayList<>();

        for (Stmt s : b.stmts)
        {
            String node = s.accept(this);
            nodeIds.add(node);
        }

        for (String s : nodeIds) writer.println(blockNodeId + "->" + s + ";");

        return blockNodeId;
    }

    @Override
    public String visitAtomicBlock(AtomicBlock b)
    {
        String blockNodeId = "Node" + nodeCount++;
        writer.println(blockNodeId + "[label=\"Block\"];");

        ArrayList<String> nodeIds = new ArrayList<>();

        for (Stmt s : b.stmts)
        {
            String node = s.accept(this);
            nodeIds.add(node);
        }

        for (String s : nodeIds) writer.println(blockNodeId + "->" + s + ";");

        return blockNodeId;
    }

    @Override
    public String visitProgram(Program p)
    {
        String programNodeId = "Node" + nodeCount++;

        writer.println(programNodeId + "[label=\"Program\"];");

        ArrayList<String> nodeIds = new ArrayList<>();
        for (VarDecl vd : p.varDecls)
        {
            String node = vd.accept(this);
            nodeIds.add(node);
        }


        for (String s : nodeIds) writer.println(programNodeId + "->" + s + ";");

        return programNodeId;
    }

    @Override
    public String visitVarDecl(VarDecl vd)
    {
        String varDeclNodeId = "Node" + nodeCount++;
        writer.println(varDeclNodeId + "[label=\"VarDecl\"]");

        String typeNodeId = vd.type.accept(this);
        String stringNodeId = "Node" + nodeCount++;

        writer.println(stringNodeId + "[label=\"" + vd.varName + "\"];");

        writer.println(varDeclNodeId + "->" + typeNodeId);
        writer.println(varDeclNodeId + "->" + stringNodeId);

        return varDeclNodeId;
    }

    @Override
    public String visitVarExpr(VarExpr v)
    {
        return null;
    }


    @Override
    public String visitWhile(While aWhile)
    {
        return null;
    }

    @Override
    public String visitIntLiteral(IntLiteral intLiteral)
    {
        nodeCount++;
        writer.println("Node" + nodeCount + "[label=\"Cst(" + intLiteral.value + ")\"];");
        return "Node" + nodeCount;
    }

    @Override
    public String visitOp(Op op)
    {
        String opNodeId = "Node" + nodeCount++;
        writer.println(opNodeId + "[label\"" + op.name() + "\"];");
        return opNodeId;
    }

    @Override
    public String visitBinOp(BinOp binOp)
    {
        String binOpNodeId = "Node" + nodeCount++;
        writer.println(binOpNodeId + "[label=\"BinOp\"];");

        String lhsNodeId = binOp.leftExpr.accept(this);
        String opNodeId = binOp.op.accept(this);
        String rhsNodeId = binOp.rightExpr.accept(this);

        writer.println(binOpNodeId + "->" + lhsNodeId + ";");
        writer.println(binOpNodeId + "->" + opNodeId + ";");
        writer.println(binOpNodeId + "->" + rhsNodeId + ";");

        return binOpNodeId;
    }

    @Override
    public String visitExprStmt(ExprStmt exprStmt)
    {
        return null;
    }

    @Override
    public String visitIf(If anIf)
    {
        return null;
    }

    @Override
    public String visitAssign(Assign assign)
    {
        return null;
    }

    @Override
    public String visitErrorType(ErrorType errorType)
    {
        return null;
    }

    @Override
    public String visitAwait(Await await)
    {
        return null;
    }
}

package com.colinparrott.parallelsimulator.engine.compiler.ast;

public interface ASTVisitor<T>
{
    public T visitBaseType(BaseType bt);

    public T visitBlock(Block b);

    public T visitProgram(Program p);

    public T visitVarDecl(VarDecl vd);

    public T visitVarExpr(VarExpr v);

    public T visitWhile(While aWhile);

    public T visitIntLiteral(IntLiteral intLiteral);

    public T visitOp(Op op);

    public T visitBinOp(BinOp binOp);

    public T visitExprStmt(ExprStmt exprStmt);

    public T visitIf(If anIf);

    public T visitAssign(Assign assign);

    public T visitErrorType(ErrorType errorType);

    public T visitAwait(Await await);

    public T visitAtomicBlock(AtomicBlock atomicBlock);

    // to complete ... (should have one visit method for each concrete AST node class)
}

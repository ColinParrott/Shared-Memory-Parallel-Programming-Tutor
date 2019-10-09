package com.colinparrott.parallelsimulator.compiler;

import com.colinparrott.parallelsimulator.compiler.errorhandlers.CompilationResult;
import com.colinparrott.parallelsimulator.compiler.errorhandlers.CompilerErrorHandler;
import com.colinparrott.parallelsimulator.compiler.singleprogramcompiler.SingleProgramLanguageBaseVisitor;
import com.colinparrott.parallelsimulator.compiler.singleprogramcompiler.SingleProgramLanguageLexer;
import com.colinparrott.parallelsimulator.compiler.singleprogramcompiler.SingleProgramLanguageParser;
import com.colinparrott.parallelsimulator.engine.hardware.SimulatorThread;
import com.colinparrott.parallelsimulator.engine.instructions.AwaitComparator;
import com.colinparrott.parallelsimulator.engine.instructions.Instruction;
import com.colinparrott.parallelsimulator.engine.instructions.InstructionKeyword;
import com.colinparrott.parallelsimulator.programs.parser.AssemblyParser;
import javafx.util.Pair;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static org.antlr.v4.runtime.CharStreams.fromString;

public class SingleProgramCompiler
{
    private final Logger logger = LoggerFactory.getLogger(SingleProgramCompiler.class);
    private ArrayList<String> programText;
    private final String LOOP_LABEL_PREFIX = "loop";
    private int loopCounter = 0;
    private int ifCounter = 0;
    private HashSet<Integer> freeRegisters;
    private final int COMPARISON_REG_ONE = 8; // $R8
    private final int COMPARISON_REG_TWO = 9; // $R9


    /**
     * Compiles a single program for a thread from high-level code to assembly
     *
     * @return A pair containing an array of the compiled assembly lines and a list of error strings.
     * If no error, the error list is empty. If there is an error, the error list contains the error message(s)
     * from the lexer or parser.
     */
    public CompilationResult compileProgram(String code)
    {
        freeRegisters = new HashSet<>();

        // Populate register stack in reverse order (so lower ones get used first), $R8 & $R9 are used for comparison results
        // hence why we stop two registers early
        for (int i = SimulatorThread.REGISTERS_PER_THREAD - 3; i >= 0; i--)
        {
            freeRegisters.add(i);
        }

        programText = new ArrayList<>();
        CharStream cs = fromString(code);
        SingleProgramLanguageLexer lexer = new SingleProgramLanguageLexer(cs);

        CompilerErrorHandler lexerErrorHandler = new CompilerErrorHandler();
        lexer.removeErrorListeners();
        lexer.addErrorListener(lexerErrorHandler);

        SingleProgramLanguageParser parser = new SingleProgramLanguageParser((new CommonTokenStream(lexer)));
        CompilerErrorHandler parserErrorHandler = new CompilerErrorHandler();
        parser.removeErrorListeners();
        parser.addErrorListener(parserErrorHandler);

        ParseTree tree = parser.program(); // parse the content and get the tree

        if (lexerErrorHandler.hasErrors())
        {
            return new CompilationResult(null, lexerErrorHandler.getErrorMessages());
        }

        if (parserErrorHandler.hasErrors())
        {
            return new CompilationResult(null, parserErrorHandler.getErrorMessages());
        }

        MyVisitor visitor = new MyVisitor();
        visitor.visit(tree);

        printAssemblyString();
        return new CompilationResult(programText, new ArrayList<>());
    }

    private void freeRegisters(int... regNum)
    {
        for (int i : regNum)
        {
            logger.debug("Freeing register: $R" + i);
            freeRegisters.add(i);
        }
    }

    private int getRegister()
    {
        logger.info("Free regs: " + freeRegisters.size());
        if (freeRegisters.size() > 0)
        {
            int reg = freeRegisters.iterator().next();
            freeRegisters.remove(reg);
            logger.debug("Allocating register: $R" + reg);
            return reg;
        }


        System.out.println("ERROR: NO FREE REGISTERS!! EXITING");
        System.exit(-1);
        return -1;
    }

    void printAssemblyString()
    {
        for (String s : programText)
        {
            System.out.println(s);
        }

    }

    void parseAndPrintAssembly()
    {
        AssemblyParser assemblyParser = new AssemblyParser();
        Pair<ArrayList<Instruction>, Optional<String>> parsed = assemblyParser.parseAssemblyCode(programText.toArray(new String[0]));

        if (parsed.getValue().isPresent())
        {
            logger.error(parsed.getValue().get());
        }

        ArrayList<Instruction> assemblyInstructions = parsed.getKey();

        for (Instruction i : assemblyInstructions)
        {
            System.out.println(i);
        }

//        ProgramJsonProducer.produceJsonFile("egg_" + new Random().nextInt(30000), programText.toArray(new String[0]));
    }

    private class MyVisitor<T> extends SingleProgramLanguageBaseVisitor
    {

        @Override
        public T visitProgram(SingleProgramLanguageParser.ProgramContext context)
        {

            for (int i = 0; i < context.children.size(); i++)
            {
                System.out.println("vistProgramChild");
                context.getChild(i).accept(this);
            }
            return null;
        }


        @Override
        public Integer visitValueExp(SingleProgramLanguageParser.ValueExpContext ctx)
        {
            return (int) ctx.children.get(0).accept(this);
        }

        @Override
        public Integer visitAdditionExp(SingleProgramLanguageParser.AdditionExpContext ctx)
        {
            int lhs = (int) ctx.singleValue(0).accept(this);
            int rhs = (int) ctx.singleValue(1).accept(this);
            int sumReg = getRegister();
            programText.add(String.format("ADD $R%d $R%d $R%d", sumReg, lhs, rhs));
            freeRegisters(lhs, rhs);
            return sumReg;
        }

        @Override
        public Integer visitSubExp(SingleProgramLanguageParser.SubExpContext ctx)
        {
            int lhs = (int) ctx.singleValue(0).accept(this);
            int rhs = (int) ctx.singleValue(1).accept(this);
            int sumReg = getRegister();
            programText.add(String.format("SUB $R%d $R%d $R%d", sumReg, lhs, rhs));
            freeRegisters(lhs, rhs);
            return sumReg;
        }

        @Override
        public Integer visitMultExp(SingleProgramLanguageParser.MultExpContext ctx)
        {
            int lhs = (int) ctx.singleValue(0).accept(this);
            int rhs = (int) ctx.singleValue(1).accept(this);
            int sumReg = getRegister();
            programText.add(String.format("MUL $R%d $R%d $R%d", sumReg, lhs, rhs));
            freeRegisters(lhs, rhs);
            return sumReg;
        }

        @Override
        public Integer visitDivExp(SingleProgramLanguageParser.DivExpContext ctx)
        {
            int lhs = (int) ctx.singleValue(0).accept(this);
            int rhs = (int) ctx.singleValue(1).accept(this);
            int sumReg = getRegister();
            programText.add(String.format("DIV $R%d $R%d $R%d", sumReg, lhs, rhs));
            freeRegisters(lhs, rhs);
            return sumReg;
        }

        @Override
        public Integer visitSingleValue(SingleProgramLanguageParser.SingleValueContext ctx)
        {
            int reg;
            if (ctx.INT_LITERAL() != null)
            {
                reg = getRegister();
                programText.add(String.format("LDI $R%d %s", reg, ctx.INT_LITERAL().getText()));
            }
            else
            {
                reg = getRegister();
                programText.add(String.format("LD $R%d %s", reg, ctx.IDENTIFIER()));
            }

            return reg;
        }

        @Override
        public T visitAtomicBlock(SingleProgramLanguageParser.AtomicBlockContext ctx)
        {
            programText.add("ATOMIC");

            for (ParseTree t : ctx.children)
            {
                t.accept(this);
            }

            programText.add("ENDATOMIC");
            return null;
        }

        @Override
        public T visitBlock(SingleProgramLanguageParser.BlockContext ctx)
        {
            for (SingleProgramLanguageParser.StmtContext stmt : ctx.stmt())
            {
                stmt.accept(this);
            }
            return null;
        }

        @Override
        public T visitStmt(SingleProgramLanguageParser.StmtContext ctx)
        {
            for (ParseTree stmt : ctx.children)
            {
                stmt.accept(this);
            }
            return null;
        }

        @Override
        public T visitWhileStmt(SingleProgramLanguageParser.WhileStmtContext ctx)
        {
            String loopLabel = String.format("%s_%d:", LOOP_LABEL_PREFIX, loopCounter);
            String loopExitLabel = String.format("%s_%d_exit:", LOOP_LABEL_PREFIX, loopCounter);
            programText.add(loopLabel);
            loopCounter++;

            // substring to remove colon in label
            visitCondExpEnhanced(ctx.condExp(), loopExitLabel.substring(0, loopExitLabel.length() - 1), true);

            for (ParseTree stmt : ctx.stmt())
            {
                stmt.accept(this);
            }

            programText.add(String.format("JUMP %s", loopLabel.substring(0, loopLabel.length() - 1)));
            programText.add(loopExitLabel);

            return null;
        }

        @Override
        public T visitIfStmt(SingleProgramLanguageParser.IfStmtContext ctx)
        {
            int ifId = ifCounter;
            ifCounter++;

            String skipLabel = String.format("if_skip_%d", ifId);

            if (ctx.elseStmt() == null)
            {
                visitCondExpEnhanced(ctx.condExp(), skipLabel, true);

                for (SingleProgramLanguageParser.StmtContext s : ctx.stmt())
                    s.accept(this);

                programText.add(skipLabel + ":");
            }
            else
            {
                String elseLabel = String.format("else_%d", ifId);
                visitCondExpEnhanced(ctx.condExp(), elseLabel, true);

                for (SingleProgramLanguageParser.StmtContext s : ctx.stmt())
                    s.accept(this);
                programText.add("JUMP " + skipLabel);

                programText.add(elseLabel + ":");

                ctx.elseStmt().accept(this);
                programText.add(skipLabel + ":");

            }

            return null;
        }

        @Override
        public T visitElseStmt(SingleProgramLanguageParser.ElseStmtContext ctx)
        {
            for (SingleProgramLanguageParser.StmtContext stmt : ctx.stmt())
            {
                stmt.accept(this);
            }

            return null;
        }

        @Override
        public T visitAssignStmt(SingleProgramLanguageParser.AssignStmtContext ctx)
        {
            int registerNum = (int) ctx.valueExp().accept(this);
            programText.add(String.format("ST $R%d %s", registerNum, ctx.IDENTIFIER()));
            freeRegisters(registerNum);
            return null;
        }

        @Override
        public T visitAwaitStmt(SingleProgramLanguageParser.AwaitStmtContext ctx)
        {
            String varOne = ctx.IDENTIFIER(0).getText();
            String varTwo = ctx.IDENTIFIER(1).getText();
            String op = "";

            if (ctx.EQ_OP() != null)
            {
                op = AwaitComparator.EQ.name();
            }
            else if (ctx.NE_OP() != null)
            {
                op = AwaitComparator.NE.name();
            }
            else if (ctx.LT_OP() != null)
            {
                op = AwaitComparator.LT.name();
            }
            else if (ctx.LTE_OP() != null)
            {
                op = AwaitComparator.LE.name();
            }
            else if (ctx.GT_OP() != null)
            {
                op = AwaitComparator.GT.name();
            }
            else if (ctx.GTE_OP() != null)
            {
                op = AwaitComparator.GE.name();
            }

            programText.add("ATOMIC");
            programText.add(String.format("AWAIT %s %s %s", varOne, op, varTwo));
            programText.add("ENDATOMIC");

            return null;
        }

        void visitCondExpEnhanced(SingleProgramLanguageParser.CondExpContext ctx, String label, boolean inverse)
        {

            // Single comparison (x==5, a>b ...)
            if (ctx.condDualExp() == null)
            {
                int lhs = (int) ctx.compExp().singleValue(0).accept(this);
                int rhs = (int) ctx.compExp().singleValue(1).accept(this);

                if (!inverse)
                    programText.add(String.format("%s $R%d $R%d %s", branchOpGetter(ctx.compExp()), lhs, rhs, label));
                else
                    programText.add(String.format("%s $R%d $R%d %s", branchOpInverseGetter(ctx.compExp()), lhs, rhs, label));
                freeRegisters(lhs, rhs);
            }
            // Two conditions (x==5 || x==2, y>5 && y<10 ...)
            else
            {
                visitCondDualExpEnhanced(ctx.condDualExp(), label, inverse);
            }
        }

        void visitCondDualExpEnhanced(SingleProgramLanguageParser.CondDualExpContext ctx, String label, boolean inverse)
        {
            int lhs = (int) ctx.compExp().get(0).singleValue(0).accept(this);
            int rhs = (int) ctx.compExp().get(0).singleValue(1).accept(this);
            String setOperator = setBranchOpGetter(ctx.compExp().get(0));
            programText.add(String.format("%s $R%d $R%d $R%d", setOperator, COMPARISON_REG_ONE, lhs, rhs));
            freeRegisters(lhs, rhs);

            int lhsTwo = (int) ctx.compExp().get(1).singleValue(0).accept(this);
            int rhsTwo = (int) ctx.compExp().get(1).singleValue(1).accept(this);
            String setOperatorTwo = setBranchOpGetter(ctx.compExp().get(1));
            programText.add(String.format("%s $R%d $R%d $R%d", setOperatorTwo, COMPARISON_REG_TWO, lhsTwo, rhsTwo));
            freeRegisters(lhsTwo, rhsTwo);

            int sumReg = getRegister();
            int targetReg = getRegister();

            // Add both comparison results (1 if true, 0 otherwise) into sumReg
            programText.add(String.format("ADD $R%d $R%d $R%d", sumReg, COMPARISON_REG_ONE, COMPARISON_REG_TWO));

            if (ctx.AND_OP() != null)
            {
                // If AND, sum must equal 2
                programText.add(String.format("LDI $R%d 2", targetReg));
                if (!inverse)
                    programText.add(String.format("BEQ $R%d $R%d %s", sumReg, targetReg, label));
                else
                    programText.add(String.format("BNE $R%d $R%d %s", sumReg, targetReg, label));
            }
            else
            {
                // If OR, at least one must be true (i.e sum > 0)
                programText.add(String.format("LDI $R%d 0", targetReg));
                if (!inverse)
                    programText.add(String.format("BGT $R%d $R%d %s", sumReg, targetReg, label));
                else
                    programText.add(String.format("BLE $R%d $R%d %s", sumReg, targetReg, label));
            }

            // No longer needed
            freeRegisters(sumReg, targetReg);

        }

        private String branchOpInverseGetter(SingleProgramLanguageParser.CompExpContext compExp)
        {
            if (compExp.EQ_OP() != null)
            {
                return "BNE";
            }
            else if (compExp.NE_OP() != null)
            {
                return "BEQ";
            }
            else if (compExp.LT_OP() != null)
            {
                return "BGE";
            }
            else if (compExp.GT_OP() != null)
            {
                return "BLE";
            }
            else if (compExp.GTE_OP() != null)
            {
                return "BLT";
            }
            else if (compExp.LTE_OP() != null)
            {
                return "BGT";
            }

            logger.error("Not implemented for op: " + compExp);
            return null;
        }

        String setBranchOpGetter(SingleProgramLanguageParser.CompExpContext compExp)
        {

            if (compExp.EQ_OP() != null)
                return InstructionKeyword.SEQ.name();
            if (compExp.NE_OP() != null)
                return InstructionKeyword.SNE.name();
            if (compExp.GT_OP() != null)
                return InstructionKeyword.SGT.name();
            if (compExp.LT_OP() != null)
                return InstructionKeyword.SLT.name();
            if (compExp.GTE_OP() != null)
                return InstructionKeyword.SGE.name();
            if (compExp.LTE_OP() != null)
                return InstructionKeyword.SLE.name();

            logger.error("Not implemented for set op: " + compExp);
            return null;
        }

        String branchOpGetter(SingleProgramLanguageParser.CompExpContext compExp)
        {
            if (compExp.NE_OP() != null)
            {
                return "BNE";
            }
            else if (compExp.EQ_OP() != null)
            {
                return "BEQ";
            }
            else if (compExp.LT_OP() != null)
            {
                return "BLT";
            }
            else if (compExp.GT_OP() != null)
            {
                return "BGT";
            }
            else if (compExp.LTE_OP() != null)
            {
                return "BLE";
            }
            else if (compExp.GTE_OP() != null)
            {
                return "GTE";
            }

            return null;
        }

        String branchOpGetter(String str)
        {
            switch (str)
            {
                case ">":
                    return "BGT";
                case "<":
                    return "LGT";
                case "==":
                    return "BEQ";
                case "!=":
                    return "BNE";
                case ">=":
                    return "BGE";
                case "<=":
                    return "BLE";
                default:
                    logger.error("Unrecognised comparison op: " + str);
                    return null;
            }
        }

        @Override
        public T visitCondExp(SingleProgramLanguageParser.CondExpContext ctx)
        {
            return null;
        }


        @Override
        public T visitCondDualExp(SingleProgramLanguageParser.CondDualExpContext ctx)
        {
            return null;
        }

        @Override
        public T visitCompExp(SingleProgramLanguageParser.CompExpContext ctx)
        {
            return null;
        }
    }
}

package com.colinparrott.parallelsimulator;

import com.colinparrott.parallelsimulator.engine.compiler.antlrgen.highLanguageBaseVisitor;
import com.colinparrott.parallelsimulator.engine.compiler.antlrgen.highLanguageLexer;
import com.colinparrott.parallelsimulator.engine.compiler.antlrgen.highLanguageParser;
import com.colinparrott.parallelsimulator.engine.hardware.SimulatorThread;
import com.colinparrott.parallelsimulator.engine.instructions.Instruction;
import com.colinparrott.parallelsimulator.programs.parser.AssemblyParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import static org.antlr.v4.runtime.CharStreams.fromFileName;

public class AntlrTest {
    private static final Logger logger = LoggerFactory.getLogger(AntlrTest.class);
    static ArrayList<String> programText;
    private static final String LOOP_LABEL_PREFIX = "loop";
    private static final String BRANCH_LABEL_PREFIX = "branch";
    private static int loopCounter = 0;
    private static HashSet<Integer> freeRegisters;

    public static void main(String[] args) throws IOException {
        // Populate register stack in reverse order (so lower ones get used first)
        freeRegisters = new HashSet<>();
        for (int i = SimulatorThread.REGISTERS_PER_THREAD - 1; i >= 0; i--) {
            freeRegisters.add(i);
        }

        programText = new ArrayList<>();
        CharStream cs = fromFileName("program1.hl");
        highLanguageLexer lexer = new highLanguageLexer(cs);
        highLanguageParser parser = new highLanguageParser((new CommonTokenStream(lexer)));
        ParseTree tree = parser.program(); // parse the content and get the tree

        if (parser.getNumberOfSyntaxErrors() > 0)
        {
            System.out.println("parser errors");
            System.exit(0);
        }


        MyVisitor visitor = new MyVisitor();
        visitor.visit(tree);
    }

    private static void freeRegisters(int... regNum) {
        for (int i : regNum) {
            logger.debug("Freeing register: $R" + i);
            freeRegisters.remove(i);
        }


//        logger.info("Registers free: ");
//        for(int r : freeRegisters){
//            logger.info("$R" + r + "\t");
//        }
    }

    private static int getRegister() {
        if (freeRegisters.size() > 0) {
            int reg = freeRegisters.iterator().next();
            freeRegisters.remove(reg);
            logger.debug("Allocating register: $R" + reg);
            return reg;
        }


        System.out.println("ERROR: NO FREE REGISTERS!! EXITING");
        System.exit(-1);
        return -1;
    }

    private static class MyVisitor<T> extends highLanguageBaseVisitor {
        @Override
        public T visitProgram(highLanguageParser.ProgramContext context) {

            for (int i = 0; i < context.children.size(); i++) {
                context.getChild(i).accept(this);
            }

//            printAssemblyString();
            parseAndPrintAssembly();
            return null;
        }

        static void printAssemblyString() {
            for (String s : programText) {
                System.out.println(s);
            }
        }

        public static void parseAndPrintAssembly() {
            AssemblyParser assemblyParser = new AssemblyParser();
            ArrayList<Instruction> assemblyInstructions = assemblyParser.parseAssemblyCode(programText.toArray(new String[0])).getKey();

            for (Instruction i : assemblyInstructions)
            {
                System.out.println(i);
            }

            ProgramJsonProducer.produceJsonFile("egg_" + new Random().nextInt(30000), programText.toArray(new String[0]));
        }

        @Override
        public Integer visitValueExp(highLanguageParser.ValueExpContext ctx) {
            return (int) ctx.children.get(0).accept(this);
        }

        @Override
        public Integer visitAdditionExp(highLanguageParser.AdditionExpContext ctx) {
            int lhs = (int) ctx.singleValue(0).accept(this);
            int rhs = (int) ctx.singleValue(1).accept(this);
            int sumReg = getRegister();
            programText.add(String.format("ADD $R%d $R%d $R%d", sumReg, lhs, rhs));
            freeRegisters(lhs, rhs);
            return sumReg;
        }

        @Override
        public Integer visitSubExp(highLanguageParser.SubExpContext ctx) {
            int lhs = (int) ctx.singleValue(0).accept(this);
            int rhs = (int) ctx.singleValue(1).accept(this);
            int sumReg = getRegister();
            programText.add(String.format("SUB $R%d $R%d $R%d", sumReg, lhs, rhs));
            freeRegisters(lhs, rhs);
            return sumReg;
        }

        @Override
        public Integer visitMultExp(highLanguageParser.MultExpContext ctx) {
            int lhs = (int) ctx.singleValue(0).accept(this);
            int rhs = (int) ctx.singleValue(1).accept(this);
            int sumReg = getRegister();
            programText.add(String.format("MUL $R%d $R%d $R%d", sumReg, lhs, rhs));
            freeRegisters(lhs, rhs);
            return sumReg;
        }

        @Override
        public Integer visitDivExp(highLanguageParser.DivExpContext ctx) {
            int lhs = (int) ctx.singleValue(0).accept(this);
            int rhs = (int) ctx.singleValue(1).accept(this);
            int sumReg = getRegister();
            programText.add(String.format("DIV $R%d $R%d $R%d", sumReg, lhs, rhs));
            freeRegisters(lhs, rhs);
            return sumReg;
        }

        @Override
        public Integer visitSingleValue(highLanguageParser.SingleValueContext ctx) {
            int reg;
            if (ctx.INT_LITERAL() != null) {
                reg = getRegister();
                programText.add(String.format("LDI $R%d %s", reg, ctx.INT_LITERAL().getText()));
            } else {
                reg = getRegister();
                programText.add(String.format("LD $R%d %s", reg, ctx.IDENTIFIER()));
            }

            return reg;
        }

        @Override
        public T visitAtomicBlock(highLanguageParser.AtomicBlockContext ctx) {
//            System.out.println("visitAtomicBlock");
            programText.add("ATOMIC");

            for (highLanguageParser.StmtContext stmt : ctx.stmt()) {
                stmt.accept(this);
            }
            programText.add("ENDATOMIC");
            return null;
        }

        @Override
        public T visitBlock(highLanguageParser.BlockContext ctx) {
//            System.out.println("visitBlock");
            for (highLanguageParser.StmtContext stmt : ctx.stmt()) {
                stmt.accept(this);
            }
            return null;
        }

        @Override
        public T visitStmt(highLanguageParser.StmtContext ctx) {
            for (ParseTree stmt : ctx.children) {
                stmt.accept(this);
            }
            return null;
        }

        @Override
        public T visitWhileStmt(highLanguageParser.WhileStmtContext ctx) {
            String loopLabel = String.format("%s_%d:", LOOP_LABEL_PREFIX, loopCounter);
            programText.add(loopLabel);
            loopCounter++;

            for(ParseTree stmt : ctx.stmt()){
                stmt.accept(this);
            }

            // substring to remove colon in label
            visitCondExpEnhanced(ctx.condExp(), loopLabel.substring(0, loopLabel.length()-1));

            return null;
        }

        @Override
        public T visitIfStmt(highLanguageParser.IfStmtContext ctx) {
            return null;
        }

        @Override
        public T visitAssignStmt(highLanguageParser.AssignStmtContext ctx) {
            int registerNum = (int) ctx.valueExp().accept(this);
            programText.add(String.format("ST $R%d %s", registerNum, ctx.IDENTIFIER()));
            freeRegisters(registerNum);
            return null;
        }


        @Override
        public T visitAwaitStmt(highLanguageParser.AwaitStmtContext ctx) {
            return null;
        }

        public T visitCondExpEnhanced(highLanguageParser.CondExpContext ctx, String label){

            // Single comparison (x==5, a>b...)
            if(ctx.condDualExp() == null){
                int lhs = (int) ctx.compExp().singleValue(0).accept(this);
                int rhs = (int) ctx.compExp().singleValue(1).accept(this);
                programText.add(String.format("%s $R%d $R%d %s", branchOpGetter(ctx.compExp()), lhs, rhs, label));
                // todo: is it ok to free the registers here??
                freeRegisters(lhs, rhs);
                return null;
//                return new int[]{lhs, rhs};
            }
            else{
                return null;
            }
        }

        String branchOpGetter(highLanguageParser.CompExpContext compExp){
            if(compExp.NE_OP() != null){
                return "BNE";
            }
            else if(compExp.EQ_OP() != null){
                return "BEQ";
            }
            else if(compExp.LT_OP() != null){
                return "BLT";
            }
            else{
                return "BGT";
            }
        }

        String branchOpGetter(String str){
            switch (str){
                case ">":
                    return "BGT";
                case "<":
                    return "LGT";
                case "==":
                    return "BEQ";
                case "!=":
                    return "BNE";
                default:
                    logger.error("Unrecognised comparison op: " + str);
                    return null;
            }
        }

        public T visitCompExpEnhanced(highLanguageParser.CompExpContext ctx, String label){
            return null;
        }

        public T visitCondDualExpEnhanced(highLanguageParser.CondDualExpContext ctx, String label){
            return null;
        }

        @Override
        public T visitCondExp(highLanguageParser.CondExpContext ctx) {
            return null;
        }


        @Override
        public T visitCondDualExp(highLanguageParser.CondDualExpContext ctx) {
            return null;
        }

        @Override
        public T visitCompExp(highLanguageParser.CompExpContext ctx) {
            return null;
        }

//        @Override
//        public Integer visitAdditionExp(highLanguageParser.AdditionExpContext ctx)
//        {
//            int lhs = (int) ctx.multiplyExp(0).accept(this);
//            // If assignment is simple assignment eg. x=5 or x=b, no need to go further
//            if (ctx.ADD_MATH_OP().size() == 0 && ctx.SUB_MATH_OP().size() == 0) return lhs;
//
//            // Form list of '+' & '-' in order they appear
//            ArrayList<Token> tokenList = new ArrayList<>();
//            for (ParseTree t : ctx.children)
//            {
//                if (t instanceof TerminalNodeImpl)
//                {
//                    TerminalNodeImpl node = (TerminalNodeImpl) t;
//                    tokenList.add(node.symbol);
//                }
//            }
//
//            int sumReg = getRegister();
//            Token t = tokenList.get(0);
//            int rhs = (int) ctx.multiplyExp(1).accept(this);
//            programText.add(String.format("ADD $R%d $R%d $R%d", sumReg, lhs, rhs));
//            freeRegister(lhs);
//            freeRegister(rhs);
//
//            if (tokenList.size() == 1)
//            {
//                return sumReg;
//            }
//            else
//            {
//                int rhs2 = -1;
//                for (int i = 1; i < tokenList.size(); i++)
//                {
//                    Token tok2 = tokenList.get(i);
//                    rhs2 = (int) ctx.multiplyExp(i + 1).accept(this);
//                    String op = tok2.getText().equals("+") ? "ADD" : "SUB";
//                    programText.add(String.format("%s $R%d $R%d $R%d", op, sumReg, sumReg, rhs2));
//                }
//                freeRegister(rhs2);
//                return sumReg;
//
//            }
//
//
////            if (ctx.ADD_MATH_OP(0) != null)
////            {
////                int rhs = (int) ctx.multiplyExp(1).accept(this);
////                int sumReg = getRegister();
////                programText.add(String.format("ADD $R%d $R%d $R%d", sumReg, lhs, rhs));
////                freeRegister(lhs);
////                freeRegister(rhs);
////                return sumReg;
////            }
////            else if (ctx.SUB_MATH_OP(0) != null)
////            {
////                int rhs = (int) ctx.multiplyExp(1).accept(this);
////                int differenceReg = getRegister();
////                programText.add(String.format("SUB $R%d $R%d $R%d", differenceReg, lhs, rhs));
////                freeRegister(lhs);
////                freeRegister(rhs);
////                return differenceReg;
////            }
//        }
//
//        @Override
//        public Integer visitMultiplyExp(highLanguageParser.MultiplyExpContext ctx)
//        {
//            int lhs = (int) ctx.atomExp(0).accept(this);
//
//            if (ctx.MULT_MATH_OP(0) != null)
//            {
//                int rhs = (int) ctx.atomExp(1).accept(this);
//                int multReg = getRegister();
//                programText.add(String.format("MUL $R%d $R%d $R%d", multReg, lhs, rhs));
//                freeRegister(lhs);
//                freeRegister(rhs);
//                return multReg;
//            }
//            else if (ctx.DIV_MATH_OP(0) != null)
//            {
//                int rhs = (int) ctx.atomExp(1).accept(this);
//                int divReg = getRegister();
//                programText.add(String.format("DIV $R%d $R%d $R%d", divReg, lhs, rhs));
//                freeRegister(lhs);
//                freeRegister(rhs);
//                return divReg;
//            }
//
//            return lhs;
//        }
//
//        @Override
//        public Integer visitAtomExp(highLanguageParser.AtomExpContext ctx)
//        {
//            if (ctx.INT_LITERAL() != null)
//            {
//                int i = getRegister();
//                programText.add(String.format("LDI $R%d %s", i, ctx.INT_LITERAL().getText()));
//                return i;
//            }
//            else if (ctx.IDENTIFIER() != null)
//            {
//                int i = getRegister();
//                programText.add(String.format("LD $R%d %s", i, ctx.IDENTIFIER().getText()));
//                return i;
//            }
//            else if (ctx.LPAR() != null && ctx.additionExp() != null)
//            {
//                return (Integer) ctx.additionExp().accept(this);
//            }
//            return null;
//        }
//    }
    }
}

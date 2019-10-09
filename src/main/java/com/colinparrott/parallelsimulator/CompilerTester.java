package com.colinparrott.parallelsimulator;

import com.colinparrott.parallelsimulator.compiler.MultithreadedParser;
import com.colinparrott.parallelsimulator.compiler.MultithreadedParserResult;
import com.colinparrott.parallelsimulator.compiler.SingleProgramCompiler;
import com.colinparrott.parallelsimulator.compiler.errorhandlers.CompilationError;
import com.colinparrott.parallelsimulator.compiler.errorhandlers.CompilationResult;

import java.util.ArrayList;

public class CompilerTester
{

    public static void main(String[] args)
    {
        singleProgramTest();
//        multipleProgramsTest();
    }

    private static void multipleProgramsTest()
    {
        MultithreadedParser parser = new MultithreadedParser();
        MultithreadedParserResult result = parser.parseProgram("x=5;//c=2;//x=2;//v=4;//x=2;");

        if (result.getErrors().size() == 0)
        {
            for (String s : result.getThreadCode())
                System.out.println(s);
        }
        else
        {
            for (CompilationError err : result.getErrors())
                System.out.println(err.getErrorMessage());
        }

    }

    private static void singleProgramTest()
    {
        SingleProgramCompiler compiler = new SingleProgramCompiler();
        CompilationResult compilationResult = compiler.compileProgram("x=5;");

        ArrayList<String> assemblyLines = compilationResult.getAssemblyLines();
        ArrayList<CompilationError> errorLines = compilationResult.getErrors();

//        if (errorLines.size() == 0)
//        {
//            for (String s : assemblyLines)
//                System.out.println(s);
//        }
//        else
//        {
//            for (CompilationError err : errorLines)
//                System.out.println(err.getErrorMessage());
//        }
    }
}

package com.colinparrott.parallelsimulator;

import com.colinparrott.parallelsimulator.compiler.SingleProgramCompiler;
import javafx.util.Pair;

import java.util.ArrayList;

public class CompilerTester
{

    public static void main(String[] args)
    {
        SingleProgramCompiler compiler = new SingleProgramCompiler("x=5;");
        Pair<ArrayList<String>, ArrayList<String>> compiledData = compiler.compileProgram();

        ArrayList<String> assemblyLines = compiledData.getKey();
        ArrayList<String> errorLines = compiledData.getValue();

        if (errorLines.size() == 0)
        {
            for (String s : assemblyLines)
                System.out.println(s);
        }
        else
        {
            for (String s : errorLines)
                System.out.println(s);
        }


    }
}

package com.colinparrott.parallelsimulator;

import com.colinparrott.parallelsimulator.engine.compiler.SingleProgramCompiler;

public class CompilerTester
{

    public static void main(String[] args)
    {
        SingleProgramCompiler compiler = new SingleProgramCompiler("M");
        compiler.compileProgram();
    }
}

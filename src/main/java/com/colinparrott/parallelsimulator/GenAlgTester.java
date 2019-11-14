package com.colinparrott.parallelsimulator;

import com.colinparrott.parallelsimulator.engine.simulator.programs.generators.GenerationAlgorithm;
import com.colinparrott.parallelsimulator.programs.ProgramFile;
import com.colinparrott.parallelsimulator.programs.ProgramFileReader;

import java.util.ArrayList;

public class GenAlgTester
{
    public static void main(String[] args)
    {
        ArrayList<ProgramFile> pfs = ProgramFileReader.readProgramFiles();
        GenerationAlgorithm generationAlgorithm = new GenerationAlgorithm();
        generationAlgorithm.generateSequence(pfs.get(3), 2);
    }
}

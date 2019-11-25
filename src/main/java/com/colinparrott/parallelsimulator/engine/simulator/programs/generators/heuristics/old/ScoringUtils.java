package com.colinparrott.parallelsimulator.engine.simulator.programs.generators.heuristics.old;

import com.colinparrott.parallelsimulator.engine.hardware.Memory;
import com.colinparrott.parallelsimulator.engine.hardware.MemoryLocation;
import com.colinparrott.parallelsimulator.engine.simulator.programs.generators.GenerationSim;
import com.colinparrott.parallelsimulator.programs.ProgramFile;

import java.util.ArrayList;

public class ScoringUtils
{

    public static double scoreSequences(ProgramFile pf, ArrayList<int[]> sequences)
    {
        ArrayList<Memory> outcomes = new ArrayList<>();
        ArrayList<Memory> expectedOutcomes = pf.getExpectedOutcomes();

        int numSequencesThatAvoidExpectedOutcomes = 0;

        for (int[] seq : sequences)
        {
            GenerationSim sim = new GenerationSim();
            sim.simSequence(pf.generateProgram(), seq);
            outcomes.add(sim.getMachine().getMemory());
        }

        for (Memory outcome : outcomes)
        {
            if (outcomeAvoidsExpectedOutcomes(expectedOutcomes, outcome))
            {
                numSequencesThatAvoidExpectedOutcomes++;
            }
        }

        // Return percentage of sequences that avoided all expected outcomes
//        return (double) numSequencesThatAvoidExpectedOutcomes / sequences.size();
        return numSequencesThatAvoidExpectedOutcomes;
    }


    public static boolean outcomeAvoidsExpectedOutcomes(ArrayList<Memory> expectedOutcomes, Memory generatedOutcome)
    {
        for (Memory expected : expectedOutcomes)
        {
            if (memoryStatesEqual(expected, generatedOutcome))
            {
                return false;
            }
        }
        return true;
    }

    public static boolean memoryStatesEqual(Memory a, Memory b)
    {
        for (MemoryLocation var : a.getVariables().keySet())
        {
            if (a.getValue(var) != b.getValue(var))
            {
                return false;
            }
        }
        return true;
    }

}

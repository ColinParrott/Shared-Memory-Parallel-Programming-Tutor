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
        ArrayList<Memory> expectedOutcomes = new ArrayList<>();

        // Populate expected outcomes into list of type Memory
        for (int i = 0; i < pf.getExpectedOutcomes().size(); i++)
        {
            expectedOutcomes.add(pf.getExpectedOutcomes().get(i));
        }

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
        return (double) numSequencesThatAvoidExpectedOutcomes / sequences.size();

    }


    private static boolean outcomeAvoidsExpectedOutcomes(ArrayList<Memory> expectedOutcomes, Memory generatedOutcome)
    {
        for (Memory expected : expectedOutcomes)
        {
            if (!memoryStatesEqual(expected, generatedOutcome))
            {
                return false;
            }
        }
        return true;
    }

    private static boolean memoryStatesEqual(Memory a, Memory b)
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

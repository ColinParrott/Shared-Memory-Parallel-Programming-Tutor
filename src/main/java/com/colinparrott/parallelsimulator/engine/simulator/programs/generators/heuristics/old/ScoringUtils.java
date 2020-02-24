package com.colinparrott.parallelsimulator.engine.simulator.programs.generators.heuristics.old;

import com.colinparrott.parallelsimulator.engine.hardware.Memory;
import com.colinparrott.parallelsimulator.engine.hardware.MemoryLocation;
import com.colinparrott.parallelsimulator.engine.simulator.programs.Program;
import com.colinparrott.parallelsimulator.engine.simulator.programs.generators.GenerationSim;
import com.colinparrott.parallelsimulator.programs.ProgramFile;
import org.apache.commons.lang3.tuple.ImmutablePair;

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

    public static ImmutablePair<Integer, ArrayList<Memory>> scoreSequencesReturnOutcomes(ProgramFile pf, ArrayList<int[]> sequences, boolean countOnlyCompletedPrograms)
    {
        ArrayList<Memory> outcomes = new ArrayList<>();
        ArrayList<Memory> expectedOutcomes = pf.getExpectedOutcomes();

        int numSequencesThatAvoidExpectedOutcomes = 0;

        for (int[] seq : sequences)
        {
            Program p = pf.generateProgram();
            GenerationSim sim = new GenerationSim();
            sim.simSequence(p, seq);

            // Only count outcome if all threads were completed (and boolean was set for this option)
            if(countOnlyCompletedPrograms)
            {
                boolean allThreadsCompleted = true;
                for(int id : p.getUsedThreadIDs()){
                    if (sim.getMachine().getThread(id).getNextInstruction() != null){
                        allThreadsCompleted = false;
                        break;
                    }
                }

                if (allThreadsCompleted)
                    outcomes.add(sim.getMachine().getMemory());
            }
            else
            {
                outcomes.add(sim.getMachine().getMemory());
            }

        }


        for (Memory outcome : outcomes)
        {
            if (outcomeAvoidsExpectedOutcomesNew(pf, outcome))
            {
                numSequencesThatAvoidExpectedOutcomes++;
            }
        }

        // Return percentage of sequences that avoided all expected outcomes
//        return (double) numSequencesThatAvoidExpectedOutcomes / sequences.size();
        return new ImmutablePair<>(numSequencesThatAvoidExpectedOutcomes, outcomes);
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

    public static boolean outcomeAvoidsExpectedOutcomesNew(ProgramFile pf, Memory generatedOutcome)
    {
//        for (Memory expected : pf.getExpectedOutcomes())
//        {
//            if (memoryStatesEqualSelectVariables(pf.getOutcomeVariables(), expected, generatedOutcome))
//                return false;
//        }
        for (int i = 0; i < pf.getExpectedOutcomes().size(); i++)
        {
            if (memoryStatesEqualSelectVariables(pf.getOutcomeVariables().get(i), pf.getExpectedOutcomes().get(i), generatedOutcome))
            {
                return false;
            }
        }

        return true;
    }

    public static boolean memoryStatesEqualSelectVariables(ArrayList<MemoryLocation> locations, Memory a, Memory b)
    {
        for (MemoryLocation var : locations)
        {
            if (a.getValue(var) != b.getValue(var))
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

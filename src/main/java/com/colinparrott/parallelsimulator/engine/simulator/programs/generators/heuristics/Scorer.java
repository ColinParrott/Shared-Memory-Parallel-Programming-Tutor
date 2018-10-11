package com.colinparrott.parallelsimulator.engine.simulator.programs.generators.heuristics;

import com.colinparrott.parallelsimulator.engine.hardware.Memory;
import com.colinparrott.parallelsimulator.engine.hardware.MemoryLocation;
import com.colinparrott.parallelsimulator.engine.simulator.programs.Program;
import com.colinparrott.parallelsimulator.engine.simulator.programs.generators.ExecutionSequenceStateAnalyser;

import java.util.ArrayList;

/**
 * Class used for scoring how good a sequence is for a particular program
 */
public class Scorer
{
    public static int calculateScore(int[] seq, Program p, ScoreMethod method)
    {
        switch (method)
        {
            case VARIABLE_CHANGE_COUNT:
                return variableChangeCount(seq, p);
            case VARIABLE_CHANGE_START_AND_END:
                return variableChangeCountCompareStartAndEnd(seq, p);
            default:
                System.out.println("Shouldn't reach here!");
                return -1;
        }
    }

    private static int variableChangeCount(int[] seq, Program p)
    {
        ArrayList<Memory> memories = ExecutionSequenceStateAnalyser.calculateMemoryStates(p, seq);

        int count = 0;
        for (int i = 0; i < memories.size() - 1; i++)
        {
            Memory one = memories.get(i);
            Memory two = memories.get(i + 1);

            for (MemoryLocation l : MemoryLocation.values())
            {
                if (two.getValue(l) != one.getValue(l))
                    count++;
            }
        }

        return count;
    }

    private static int variableChangeCountCompareStartAndEnd(int[] seq, Program p)
    {
        ArrayList<Memory> memories = ExecutionSequenceStateAnalyser.calculateMemoryStates(p, seq);

        Memory start = memories.get(0);
        Memory end = memories.get(memories.size() - 1);

        int count = 0;
        for (MemoryLocation l : MemoryLocation.values())
        {
            if (end.getValue(l) != start.getValue(l))
                count++;
        }

        return count;
    }
}

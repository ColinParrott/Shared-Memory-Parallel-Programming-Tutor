package com.colinparrott.parallelsimulator.engine.simulator.programs.generators.heuristics.old;

import com.colinparrott.parallelsimulator.engine.hardware.Memory;
import com.colinparrott.parallelsimulator.engine.hardware.MemoryLocation;
import com.colinparrott.parallelsimulator.engine.simulator.programs.Program;
import com.colinparrott.parallelsimulator.engine.simulator.programs.generators.ExecutionSequenceStateAnalyser;
import com.colinparrott.parallelsimulator.engine.simulator.programs.generators.GenerationSim;
import javafx.util.Pair;

import java.util.*;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

/**
 * Class used for scoring how good a sequence is for a particular program
 */
public class Scorer
{
    public int calculateScore(int[] seq, Program p, ScoreMethod method)
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

    /**
     * From a list of sequences, simulates them all and returns the sequence which produces the least common final
     * memory state(random if more than one)
     *
     * @param p               Program to simulate
     * @param listOfSequences List of sequences to sim
     * @return Execution sequence (list of thread ids)
     */
    public static Pair<int[], Memory> getMostUniqueSeq(Program p, int[][] listOfSequences)
    {
        HashMap<Memory, Integer> counts = new HashMap<>();
        HashMap<Memory, ArrayList<int[]>> memoryToSeq = new HashMap<>();

        for (int[] seq : listOfSequences)
        {
            GenerationSim sim = new GenerationSim();
            sim.simSequence(p, seq);
            Memory outcome = sim.getMachine().getMemory();

            // Update counts
            counts.put(outcome, counts.containsKey(outcome) ? counts.get(outcome) + 1 : 0);

            // Add seq to memory outcomes map
            if (memoryToSeq.containsKey(outcome))
            {
                memoryToSeq.get(outcome).add(seq);
            }
            else
            {
                memoryToSeq.put(outcome, new ArrayList<>());
                memoryToSeq.get(outcome).add(seq);
            }
        }

        LinkedHashMap<Memory, Integer> sorted =
                counts
                        .entrySet()
                        .stream()
                        .sorted(comparingByValue())
                        .collect(
                                toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                        LinkedHashMap::new));

        Memory lowestCountMemory = sorted.keySet().toArray(new Memory[0])[0];
        ArrayList<int[]> rarestSequences = memoryToSeq.get(lowestCountMemory);

        Random r = new Random();

        return new Pair<>(rarestSequences.get(r.nextInt(rarestSequences.size())), lowestCountMemory);
    }

    /**
     * From a list of sequences, simulates them all and returns the sequence which produces the least common final
     * memory state(random if more than one)
     *
     * @param p               Program to simulate
     * @param seq List of sequences to sim
     * @return Execution sequence (list of thread ids)
     */


    private int variableChangeCount(int[] seq, Program p)
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

    private int variableChangeCountCompareStartAndEnd(int[] seq, Program p)
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

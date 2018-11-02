package com.colinparrott.parallelsimulator.engine.simulator.programs.generators.heuristics;

import com.colinparrott.parallelsimulator.engine.hardware.Memory;
import com.colinparrott.parallelsimulator.engine.simulator.programs.Program;
import com.colinparrott.parallelsimulator.engine.simulator.programs.generators.GenerationSim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

/**
 * Generates a score for list of sequences using different scoring methods
 */

public class BatchScorer
{

    public int calculateScore(int[][] seqs, Program p, ScoreMethod method)
    {

        int score = 0;
        Scorer scorer = new Scorer();
        if (method != ScoreMethod.COUNT_UNIQUE_OUTCOMES)
        {
            for (int[] seq : seqs)
                score += scorer.calculateScore(seq, p, method);
            return score;
        }
        else
        {
            return countUniqueSeq(p, seqs);
        }


    }

    private int countUniqueSeq(Program p, int[][] listOfSequences)
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

        return rarestSequences.size();
    }


}

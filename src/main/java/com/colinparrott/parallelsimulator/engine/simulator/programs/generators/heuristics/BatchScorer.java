package com.colinparrott.parallelsimulator.engine.simulator.programs.generators.heuristics;

import com.colinparrott.parallelsimulator.engine.hardware.Memory;
import com.colinparrott.parallelsimulator.engine.simulator.programs.Program;
import com.colinparrott.parallelsimulator.engine.simulator.programs.generators.GenerationSim;
import javafx.util.Pair;

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
        if (method == ScoreMethod.COUNT_UNIQUE_OUTCOMES)
        {
            return countUniqueSeq(p, seqs);

        }
        else
        {
            for (int[] seq : seqs)
                score += scorer.calculateScore(seq, p, method);
            return score;
        }

    }

    public float calculateHumanScore(int[][] seqs, Program p)
    {
        return compareToHuman(seqs, p);
    }

    private int compareToHuman(int[][] seqs, Program p)
    {
        Pair<ArrayList<int[]>, Boolean> data = getHumanExpectedOutcomes(p);
        ArrayList<int[]> seqsToCompare = data.getKey();

        boolean isComplement = false;
        try
        {
            isComplement = data.getValue();
        }
        catch (NullPointerException e)
        {
            System.out.println(p.getName() + "ERROR: NULL!");
        }


        int score = 0;
        if (!isComplement)
        {

            for (int[] sequence : seqs)
            {
                for (int[] humanSequence : seqsToCompare)
                {
                    if (sequenceMatch(humanSequence, sequence))
                    {
                        score += 1;
                        break;
                    }

                }
            }

        }
        else
        {
            for (int[] sequence : seqs)
            {

                if (notEqualToAny(sequence, seqsToCompare))
                {
                    score += 1;
                }
            }


        }


        return score;
    }

    private boolean notEqualToAny(int[] generated, ArrayList<int[]> humanOnes)
    {

        for (int[] humanSeq : humanOnes)
        {
            if (sequenceMatch(humanSeq, generated))
                return false;
        }

        return true;
    }


    // Returns true if generated sequence matches the start of the human expected sequence
    private boolean sequenceMatch(int[] human, int[] generated)
    {
        if (generated.length < human.length) return false;

        for (int i = 0; i < human.length; i++)
        {
            if (human[i] != generated[i]) return false;
        }

        return true;
    }

    private Pair<ArrayList<int[]>, Boolean> getHumanExpectedOutcomes(Program p)
    {
        HashMap<String, ArrayList<int[]>> humanSequences = new HashMap<>();
        HashMap<String, Boolean> sequencesAreComplement = new HashMap<>();

        final String xPlusPlus = "x++";
        humanSequences.put(xPlusPlus, new ArrayList<>());
        humanSequences.get(xPlusPlus).add(new int[]{0, 0, 0, 1, 1, 1});
        humanSequences.get(xPlusPlus).add(new int[]{1, 1, 1, 0, 0, 0});
        sequencesAreComplement.put(xPlusPlus, true);

        final String xPlusPlusAtomic = "<x++>";
        humanSequences.put(xPlusPlusAtomic, new ArrayList<>());
        humanSequences.get(xPlusPlusAtomic).add(new int[]{0, 1});
        humanSequences.get(xPlusPlusAtomic).add(new int[]{1, 0});
        sequencesAreComplement.put(xPlusPlusAtomic, false);

        final String awaitFlag = "Await flag";
        humanSequences.put(awaitFlag, new ArrayList<>());
        humanSequences.get(awaitFlag).add(new int[]{0, 0, 0, 0, 1}); // x = 25
        sequencesAreComplement.put(awaitFlag, false);


        final String bEqualsAPlusA = "a=1 // a=2 // b=a+a";
        humanSequences.put(bEqualsAPlusA, new ArrayList<>());
        humanSequences.get(bEqualsAPlusA).add(new int[]{0, 0, 2, 1, 1, 2, 2, 2}); // b = 3, a = 2
        humanSequences.get(bEqualsAPlusA).add(new int[]{1, 1, 2, 0, 0, 2, 2, 2}); // b = 3, a = 1
        sequencesAreComplement.put(bEqualsAPlusA, false);

        final String bEqualsAPlusAAtomic = "a=1 // a=2 // <b=a+a>";
        humanSequences.put(bEqualsAPlusAAtomic, new ArrayList<>());
        humanSequences.get(bEqualsAPlusAAtomic).add(new int[]{2}); // b = 0, a = 1 or 2
        sequencesAreComplement.put(bEqualsAPlusAAtomic, false);

        return new Pair<>(humanSequences.get(p.getName()), sequencesAreComplement.get(p.getName()));

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


        if (sorted.values().toArray(new Integer[0])[0] == 1)
        {
            ArrayList<int[]> rarestSequences = memoryToSeq.get(lowestCountMemory);
            return rarestSequences.size();
        }
        else
        {
            return 0;
        }

    }


}

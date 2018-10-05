package com.colinparrott.parallelsimulator.engine.simulator.programs.generators.asksteps;

import com.colinparrott.parallelsimulator.engine.simulator.programs.Program;
import com.colinparrott.parallelsimulator.engine.simulator.programs.generators.GenUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Execution generation where each thread gets k-execution chances each in some random order
 */

public class EqualThreadSteps
{


    /**
     * Algorithm that generates a sequence of thread IDs
     *
     * @param p Program to generate sequence for
     * @return Array of thread IDs
     */
    public static int[] generateRandomThreadSequence(Program p, int stepsPerThread)
    {
        int numThreads = p.getUsedThreadIDs().length;
        int[] sequence = new int[numThreads * stepsPerThread];


        // Map of <thread_id, steps_left> so we know when we're done
        Map<Integer, Integer> stepsLeft = new HashMap<>();
        for (int i : p.getUsedThreadIDs()) stepsLeft.put(i, stepsPerThread); // populate hash map with thread ids

        for (int i = 0; i < sequence.length; i++)
        {
            // Choose thread id and add to sequence
            int chosenThread = GenUtils.selectRandomElementFromSet(stepsLeft.keySet());
            sequence[i] = chosenThread;

            // Decrement steps left counter for chosen thread
            stepsLeft.put(chosenThread, stepsLeft.get(chosenThread) - 1);

            // If thread has been placed in k times, steps left is 0, remove from map
            if (stepsLeft.get(chosenThread) == 0) stepsLeft.remove(chosenThread);
        }

        return sequence;
    }

    public static ArrayList<ArrayList<Integer>> generateAllPossibleSequences(Program p, int stepsPerThread)
    {
        ArrayList<ArrayList<Integer>> listOfSequences = new ArrayList<>();

        ArrayList<Integer> sequenceToPermute = new ArrayList<>();

        for(int i = 0; i < p.getUsedThreadIDs().length; i++)
        {
            for(int j = 0; j < stepsPerThread; j++)
            {
                sequenceToPermute.add(i);
            }
        }

        return GenUtils.permute(sequenceToPermute);
    }
}

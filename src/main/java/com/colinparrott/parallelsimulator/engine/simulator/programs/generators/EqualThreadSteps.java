package com.colinparrott.parallelsimulator.engine.simulator.programs.generators;

import com.colinparrott.parallelsimulator.engine.simulator.programs.Program;

import java.util.*;

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
            int chosenThread = selectRandomElementFromSet(stepsLeft.keySet());
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

        return permute(sequenceToPermute);
    }

    public static ArrayList<ArrayList<Integer>> permute(ArrayList<Integer> num) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();

        //start from an empty list
        result.add(new ArrayList<Integer>());

        for (int i = 0; i < num.size(); i++) {
            //list of list in current iteration of the array num
            ArrayList<ArrayList<Integer>> current = new ArrayList<ArrayList<Integer>>();

            for (ArrayList<Integer> l : result) {
                // # of locations to insert is largest index + 1
                for (int j = 0; j < l.size()+1; j++) {
                    // + add num[i] to different locations
                    l.add(j, num.get(i));

                    ArrayList<Integer> temp = new ArrayList<Integer>(l);
                    current.add(temp);

                    //System.out.println(temp);

                    // - remove num[i] add
                    l.remove(j);
                }
            }

            result = new ArrayList<ArrayList<Integer>>(current);
        }

        return result;
    }

    /**
     * Selects a random int from a set
     *
     * @param set Set of ints to choose from
     * @return int chosen
     */
    private static int selectRandomElementFromSet(Set<Integer> set)
    {
        Random r = new Random();
        ArrayList<Integer> elements = new ArrayList<>(set);
        return elements.get(r.nextInt(elements.size()));
    }
}

package com.colinparrott.parallelsimulator.engine.simulator.programs.generators;

import com.colinparrott.parallelsimulator.engine.hardware.Memory;
import com.colinparrott.parallelsimulator.engine.hardware.MemoryLocation;
import com.colinparrott.parallelsimulator.engine.instructions.Instruction;
import com.colinparrott.parallelsimulator.engine.instructions.InstructionKeyword;
import com.colinparrott.parallelsimulator.engine.simulator.programs.Program;

import java.util.*;

public class GenUtils
{

    public static ArrayList<ArrayList<Integer>> permute(ArrayList<Integer> num)
    {
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();

        //start from an empty list
        result.add(new ArrayList<Integer>());

        for (int i = 0; i < num.size(); i++)
        {
            //list of list in current iteration of the array num
            ArrayList<ArrayList<Integer>> current = new ArrayList<ArrayList<Integer>>();

            for (ArrayList<Integer> l : result)
            {
                // # of locations to insert is largest index + 1
                for (int j = 0; j < l.size() + 1; j++)
                {
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

    public static HashMap<Integer, Integer> instructionPerThreadCount(Program p, InstructionKeyword keyword)
    {
        HashMap<Integer, Integer> counts = new HashMap<>();

        for (int id : p.getUsedThreadIDs())
        {
            // Init hashmap
            counts.put(id, 0);
            ;

            for (Instruction instruction : p.getInstructionsForThread(id))
            {
                if (instruction.getKeyword() == keyword)
                {
                    int oldCount = counts.get(id);
                    counts.put(id, oldCount + 1);
                }
            }
        }

        return counts;
    }

    public static HashMap<Integer, Integer> instructionPerThreadCount(Program p, InstructionKeyword... keywords)
    {
        HashMap<Integer, Integer> counts = new HashMap<>();

        for (int id : p.getUsedThreadIDs())
        {
            // Init hashmap
            counts.put(id, 0);

            for (Instruction instruction : p.getInstructionsForThread(id))
            {
                if (Arrays.asList(keywords).contains(instruction.getKeyword()))
                {
                    int oldCount = counts.get(id);
                    counts.put(id, oldCount + 1);
                }
            }
        }

        return counts;

    }

    public static HashMap<Integer, HashMap<InstructionKeyword, Integer>> instructionsPerThreadCount(Program p, InstructionKeyword... keywords)
    {
        HashMap<Integer, HashMap<InstructionKeyword, Integer>> counts = new HashMap<>();

        for (int id : p.getUsedThreadIDs())
        {
            // Init inner hashmap
            counts.put(id, new HashMap<>());

            for (InstructionKeyword keyword : keywords)
            {
                counts.get(id).put(keyword, 0);
            }


            for (Instruction instruction : p.getInstructionsForThread(id))
            {
                for (InstructionKeyword k : keywords)
                {
                    if (instruction.getKeyword() == k)
                    {
                        int oldCount = counts.get(id).get(k);
                        counts.get(id).put(k, oldCount + 1);
                    }
                }
            }
        }

        return counts;
    }

    /**
     * Get the list of variables who changed value between two memory states
     * @param initial Initial memory state to compare to
     * @param end Newer memory state
     * @return List of variables
     */
    public static ArrayList<MemoryLocation> changedVariables(Memory initial, Memory end)
    {
        ArrayList<MemoryLocation> changed = new ArrayList<>();

        for(MemoryLocation l : MemoryLocation.values())
        {
            if(initial.getValue(l) != end.getValue(l))
                changed.add(l);
        }

        return changed;
    }

    /**
     * Shuffles array using Fisher-Yates algorithm: https://www.dotnetperls.com/shuffle-java
     *
     * @param array array to shuffle
     */
    public static int[] shuffle(int[] array)
    {
        int n = array.length;
        Random random = new Random();
        // Loop over array.
        for (int i = 0; i < array.length; i++)
        {
            // Get a random index of the array past the current index.
            // ... The argument is an exclusive bound.
            //     It will not go past the array's end.
            int randomValue = i + random.nextInt(n - i);
            // Swap the random element with the present element.
            int randomElement = array[randomValue];
            array[randomValue] = array[i];
            array[i] = randomElement;
        }

        return array;
    }

    /**
     * Selects a random int from a set
     *
     * @param set Set of ints to choose from
     * @return int chosen
     */
    public static int selectRandomElementFromSet(Set<Integer> set)
    {
        Random r = new Random();
        ArrayList<Integer> elements = new ArrayList<>(set);
        return elements.get(r.nextInt(elements.size()));
    }
}

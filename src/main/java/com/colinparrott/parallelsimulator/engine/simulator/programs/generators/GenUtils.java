package com.colinparrott.parallelsimulator.engine.simulator.programs.generators;

import com.colinparrott.parallelsimulator.engine.hardware.Memory;
import com.colinparrott.parallelsimulator.engine.hardware.MemoryLocation;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

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

package com.colinparrott.parallelsimulator.engine.simulator;

import com.colinparrott.parallelsimulator.engine.instructions.Instruction;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Holds the list of assembly instructions for each thread
 */

public class Program
{
    // List of pairs containing threads and their respective instructions
    private HashMap<Integer, ArrayList<Instruction>> instructionLists;

    public Program()
    {
        instructionLists = new HashMap<>();
    }

    /**
     * Sets the list of instructions for a thread
     * @param threadId ID of thread
     * @param instructions Instructions to set
     */
    public void setInstructionsForThread(int threadId, ArrayList<Instruction> instructions)
    {
        instructionLists.put(threadId, instructions);
    }

    /**
     * Get the list of instructions for a thread in this program
     * @param threadId ID of thread
     * @return Instructions bound to thread
     */
    public ArrayList<Instruction> getInstructionsForThread(int threadId)
    {
        assert instructionLists.containsKey(threadId) : "Tried to get instructions from program for thread that doesn't exist";
        return instructionLists.get(threadId);
    }

    /**
     * Get an array of thread IDs in use in this program
     * @return Array of thread IDs
     */
    public int[] getUsedThreadIDs()
    {
        int[] ids = new int[instructionLists.size()];

        int i = 0;
        for(int id : instructionLists.keySet())
        {
            ids[i] = id;
            i++;
        }

        return ids;
    }

}
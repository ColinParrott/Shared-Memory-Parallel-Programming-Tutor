package com.colinparrott.parallelsimulator.engine.hardware;

import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * Represents RAM in the simulator where variables are stored
 */

public class Memory
{
    private HashMap<MemoryLocation, Integer> variables;


    public Memory()
    {
        variables = new HashMap<>();
        for (MemoryLocation location : MemoryLocation.values())
        {
            variables.put(location, 0);
        }
    }

    /**
     * Set a variable in memory
     *
     * @param variable Name of the variable
     * @param value    Value to set variable to
     */
    public void setVariable(MemoryLocation variable, int value)
    {
        variables.put(variable, value);
    }

    /**
     * Get the value of a variable in memory
     *
     * @param variable Name of the variable
     * @return The value of the variable if present, throws exception if not
     * @throws NoSuchElementException Exception should not happen unless there's a serious problem with the code
     */
    public int getValue(MemoryLocation variable) throws NoSuchElementException
    {
        if (variables.containsKey(variable))
        {
            return variables.get(variable);
        } else
        {
            throw new NoSuchElementException();
        }
    }

    /**
     * Clears all variables from memory (useful for resetting execution state)
     */
    public void clearAll()
    {
        variables.clear();
    }

    @Override
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        for (MemoryLocation variable : MemoryLocation.values())
        {
            s.append(variable.toString()).append(":").append(variables.get(variable)).append(" ");
        }

        return s.toString();
    }

    public HashMap<MemoryLocation, Integer> getVariables() {
        return variables;
    }
}

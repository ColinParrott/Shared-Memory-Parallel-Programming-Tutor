package com.colinparrott.parallelsimulator.engine.hardware;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * Represents RAM in the simulator where variables are stored
 */

public class Memory
{
    private HashMap<MemoryLocation, Integer> variables;
    private MemoryLocation lastUpdatedLocation = null;


    public Memory()
    {
        variables = new HashMap<>();
        for (MemoryLocation location : MemoryLocation.values())
        {
            variables.put(location, 0);
        }
    }

    public Memory(HashMap<MemoryLocation, Integer> variables)
    {
        this.variables = variables;
    }

    /**
     * Set a variable in memory
     *
     * @param variable Name of the variable
     * @param value    Value to set variable to
     */
    public void setVariable(MemoryLocation variable, int value)
    {
        lastUpdatedLocation = variable;
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
     * Get the value of a variable in memory
     *
     * @param var Name of the variable
     * @return The value of the variable if present, throws exception if not
     * @throws NoSuchElementException Exception should not happen unless there's a serious problem with the code
     */
    public int getValue(String var) throws NoSuchElementException
    {
        MemoryLocation variable = stringToLocation(var);
        if (variables.containsKey(variable))
        {
            return variables.get(variable);
        }
        else
        {
            throw new NoSuchElementException();
        }
    }

    private MemoryLocation stringToLocation(String variable)
    {
        for (MemoryLocation l : MemoryLocation.values())
        {
            if (variable.toLowerCase().equals(l.name().toLowerCase()))
                return l;
        }

        return null;
    }

    /**
     * Clears all variables from memory (useful for resetting execution state)
     */
    public void clearAll()
    {
        variables.clear();
    }


    @Override
    public boolean equals(Object o)
    {
        if (o == this) return true;

        if (!(o instanceof Memory)) return false;

        Memory second = (Memory) o;

        for (MemoryLocation l : MemoryLocation.values())
        {
            if (this.getValue(l) != second.getValue(l))
                return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(variables.values().toArray());
        return builder.hashCode();
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

    public MemoryLocation getLastUpdatedLocation()
    {
        return lastUpdatedLocation;
    }
}

package com.colinparrott.parallelsimulator.simulator;

import com.colinparrott.parallelsimulator.hardware.Machine;

import java.util.ArrayDeque;
import java.util.Deque;

public class MachineStateHolder
{
    public Deque<Machine> machineStates = new ArrayDeque<>();

    public void addState(Machine machine)
    {
        machineStates.add(machine);
    }

    public Machine getCurrentState()
    {
        return machineStates.getLast();
    }

    public void popState()
    {
        machineStates.pop();
    }
}

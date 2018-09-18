package com.colinparrott.parallelsimulator.engine.simulator;

import com.colinparrott.parallelsimulator.engine.hardware.Machine;
import com.rits.cloning.Cloner;

import java.util.Stack;

public class MachineStateHolder
{
    public Stack<Machine> machineStates = new Stack<>();
    private static Cloner cloner = new Cloner();


    public void addState(Machine machine)
    {
        machineStates.push(cloner.deepClone(machine));
        System.out.println("States: " + machineStates.size());
    }

    public Machine getCurrentState()
    {
        return machineStates.peek();
    }

    public void popState()
    {
        machineStates.pop();
    }
}

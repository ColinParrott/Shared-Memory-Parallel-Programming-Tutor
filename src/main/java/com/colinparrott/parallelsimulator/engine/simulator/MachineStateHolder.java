package com.colinparrott.parallelsimulator.engine.simulator;

import com.colinparrott.parallelsimulator.engine.hardware.Machine;
import com.rits.cloning.Cloner;

import java.util.Stack;

public class MachineStateHolder
{
    Stack<Machine> machineStates = new Stack<>();
    private static Cloner cloner = new Cloner();


    void addState(Machine machine)
    {
        machineStates.push(cloner.deepClone(machine));
    }

    public Machine getCurrentState()
    {
        return machineStates.peek();
    }

    public Machine rollbackState()
    {
        machineStates.pop();
        return machineStates.peek();
    }

    private void printStates()
    {
        int counter = 1;
        for(Machine m : machineStates)
        {
            System.out.println("-----------------------");
            System.out.println("State " + counter);
            for(int i = 0; i < m.numberUsedThreads(); i++)
            {
                System.out.println("Thread 1: " + m.getThread(i).getNextInstruction());
            }
            System.out.println("-----------------------");
            counter++;
        }
    }
}

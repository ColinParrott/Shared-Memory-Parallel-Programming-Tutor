package com.colinparrott.parallelsimulator.engine.simulator;

import com.colinparrott.parallelsimulator.engine.hardware.Machine;
import com.rits.cloning.Cloner;

import java.util.Stack;

/**
 * Class for holding and altering the historical states of the current execution
 */

public class MachineStateHolder
{
    Stack<Machine> machineStates = new Stack<>();
    private static Cloner cloner = new Cloner();


    public void addState(Machine machine)
    {
        machineStates.push(cloner.deepClone(machine));
    }

    /**
     * Gets the current machine state
     * @return Current machine
     */
    public Machine getCurrentState()
    {
        return machineStates.peek();
    }

    /**
     * Remove current state off stack and return previous state
     * @return Machine as it was in the previous state
     */
    Machine rollbackState()
    {
        machineStates.pop();
        return machineStates.peek();
    }

    /**
     * For debugging
     */
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

package com.colinparrott.parallelsimulator.engine.simulator;

import com.colinparrott.parallelsimulator.engine.hardware.Machine;
import com.rits.cloning.Cloner;

/**
 * High-level class for manipulating the machine
 */

public abstract class Simulator
{
    static Machine machine;
    static MachineStateHolder stateHistory;

    protected Simulator()
    {
        machine = new Machine();
        stateHistory = new MachineStateHolder();
    }

    /**
     * Move the execution forward a step
     * @param threadId ID of thread to execute
     */
    void stepForward(int threadId)
    {
        machine.executeInstruction(threadId);
        stateHistory.addState(machine);
    }

    /**
     * Move back a step and restore previous state
     */
    void stepBackward()
    {
        // If we've got more than one state left, set the current machine to the previous state
        if (stateHistory.machineStates.size() > 1)
        {
            // Clone previous state and restore it
            Cloner cloner = new Cloner();
            machine = cloner.deepClone(stateHistory.rollbackState());
        }

    }
}

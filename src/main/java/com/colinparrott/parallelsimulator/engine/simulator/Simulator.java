package com.colinparrott.parallelsimulator.engine.simulator;

import com.colinparrott.parallelsimulator.engine.hardware.Machine;
import com.colinparrott.parallelsimulator.engine.hardware.Memory;
import com.colinparrott.parallelsimulator.engine.hardware.MemoryLocation;
import com.rits.cloning.Cloner;

/**
 * High-level class for manipulating the machine
 */

public abstract class Simulator
{
    Machine machine;
    MachineStateHolder stateHistory;

    protected Simulator()
    {
        machine = new Machine();
        stateHistory = new MachineStateHolder();
    }

    /**
     * Move the execution forward a step
     * @param threadId ID of thread to execute
     */
    public void stepForward(int threadId)
    {
        machine.executeInstruction(threadId);
        stateHistory.addState(machine);
    }

    /**
     * Move back a step and restore previous state
     */
    public void stepBackward()
    {
        // If we've got more than one state left, set the current machine to the previous state
        if (stateHistory.machineStates.size() > 1)
        {
            // Clone previous state and restore it
            Cloner cloner = new Cloner();
            machine = cloner.deepClone(stateHistory.rollbackState());
        }

    }

    /**
     * Set initial memory state
     * @param m Memory state to set to
     */
    public void setInitialMemory(Memory m)
    {
        for(MemoryLocation location : m.getVariables().keySet())
        {
            machine.getMemory().setVariable(location, m.getValue(location));
        }
    }

    /**
     * Visible for testing
     * @return Current machine state
     */
    public Machine getMachine() {
        return machine;
    }

    /**
     * Visible for testing
     * @return Machine state history
     */
    public MachineStateHolder getStateHistory() {
        return stateHistory;
    }
}

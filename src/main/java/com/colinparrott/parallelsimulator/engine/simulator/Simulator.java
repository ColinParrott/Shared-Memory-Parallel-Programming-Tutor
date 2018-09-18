package com.colinparrott.parallelsimulator.engine.simulator;

import com.colinparrott.parallelsimulator.engine.hardware.Machine;

public abstract class Simulator
{
    static Machine machine;
    static MachineStateHolder stateHistory;

    Simulator()
    {
        machine = new Machine();
        stateHistory = new MachineStateHolder();
    }

    public void stepForward(int threadId)
    {
        machine.executeInstruction(threadId);
        stateHistory.addState(machine);
    }

    public void stepBackward()
    {
        if (stateHistory.machineStates.size() > 1)
        {
            stateHistory.popState();
            machine = stateHistory.getCurrentState();
        }

    }
}

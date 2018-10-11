package com.colinparrott.parallelsimulator.engine.simulator.programs.generators;

import com.colinparrott.parallelsimulator.engine.hardware.SimulatorThread;
import com.colinparrott.parallelsimulator.engine.simulator.Simulator;
import com.colinparrott.parallelsimulator.engine.simulator.programs.Program;

/**
 * Simulator used for analysing generations
 */
public class GenerationSim extends Simulator
{

    public void simSequence(Program p, int[] seq)
    {
        SimulatorThread[] threads = new SimulatorThread[p.getUsedThreadIDs().length];

        for (int i = 0; i < p.getUsedThreadIDs().length; i++)
        {
            threads[i] = getMachine().createThread(i);
            threads[i].queueInstructions(p.getInstructionsForThread(i));
        }

        setInitialMemory(p.getInitialMemory());

        getStateHistory().addState(getMachine());

        for (int i : seq)
        {
            stepForward(i);
        }
    }

}

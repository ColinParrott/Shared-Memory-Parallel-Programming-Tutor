package com.colinparrott.parallelsimulator.engine.simulator.programs.game;

import com.colinparrott.parallelsimulator.engine.hardware.Memory;
import com.colinparrott.parallelsimulator.engine.hardware.MemoryLocation;
import com.colinparrott.parallelsimulator.engine.simulator.programs.Program;

public class SequenceChecker
{
    public static boolean correctOutcome(MemoryLocation location, int[] guessSequence, int desiredOutcome, Memory initialMemory, Program p)
    {
        System.out.println();
        InternalSimulator simulator = new InternalSimulator();

        simulator.loadProgram(p);
        simulator.setInitialMemory(initialMemory);

        for (int i : guessSequence)
        {
            simulator.stepForward(i);
        }

        return simulator.getMachine().getMemory().getValue(location) == desiredOutcome;
    }

}

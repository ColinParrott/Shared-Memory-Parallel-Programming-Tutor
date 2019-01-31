package com.colinparrott.parallelsimulator.engine.simulator.programs.game;

import com.colinparrott.parallelsimulator.engine.hardware.Memory;
import com.colinparrott.parallelsimulator.engine.hardware.MemoryLocation;
import com.colinparrott.parallelsimulator.engine.simulator.Simulator;
import com.colinparrott.parallelsimulator.engine.simulator.programs.Program;
import com.colinparrott.parallelsimulator.engine.simulator.programs.generators.askoutcome.GenMethod;
import com.colinparrott.parallelsimulator.engine.simulator.programs.generators.askoutcome.ThreadSequenceGen;

public class OutcomeCalculator
{
    public static int calculateVariableOutcome(MemoryLocation location, int[] sequence, Memory initialMemory, Program p)
    {
        System.out.println();
        InternalSimulator simulator = new InternalSimulator();

        simulator.loadProgram(p);
        simulator.setInitialMemory(initialMemory);

        for (int i : sequence)
        {
            simulator.stepForward(i);
        }

        return simulator.getMachine().getMemory().getValue(location);
    }

    public static int calculateVariableOutcomeInverseSequences(MemoryLocation location, int[][] bannedSequences, Memory initialMemory, Program p)
    {
        System.out.println();
        InternalSimulator simulator = new InternalSimulator();

        simulator.loadProgram(p);
        simulator.setInitialMemory(initialMemory);

        int[] sequence = generateSequenceNotInSet(bannedSequences, p);

        for (int i : sequence)
        {
            simulator.stepForward(i);
        }

        return simulator.getMachine().getMemory().getValue(location);

    }

    private static int[] generateSequenceNotInSet(int[][] sequences, Program p)
    {
        int length = sequences[0].length;

        int[] generatedSequence;
        do
        {
            generatedSequence = ThreadSequenceGen.generateThreadSequence(p, length, GenMethod.RANDOM_MAX_GLOBAL_STEPS);
        } while (notInSet(generatedSequence, sequences));

        return generatedSequence;
    }

    private static boolean notInSet(int[] testSequence, int[][] sequenceSet)
    {

        for (int[] seq : sequenceSet)
        {
            if (sequencesEqual(testSequence, seq))
                return false;
        }

        return true;
    }

    private static boolean sequencesEqual(int[] sequenceOne, int[] sequenceTwo)
    {
        assert sequenceOne.length == sequenceTwo.length;

        for (int i = 0; i < sequenceTwo.length; i++)
        {
            if (sequenceOne[i] != sequenceTwo[i])
                return false;
        }

        return true;
    }


}

class InternalSimulator extends Simulator
{

}

package com.colinparrott.parallelsimulator.engine.simulator.programs.generators.askoutcome;

import com.colinparrott.parallelsimulator.engine.hardware.Machine;
import com.colinparrott.parallelsimulator.engine.hardware.SimulatorThread;
import com.colinparrott.parallelsimulator.engine.simulator.programs.Program;
import com.colinparrott.parallelsimulator.engine.simulator.programs.generators.GenerationSim;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

/**
 * Class for generating a sequence of threads to execute
 */

public class ThreadSequenceGen
{

    public static int[] generateThreadSequence(Program p, int steps, GenMethod method)
    {
        switch (method)
        {
            case RANDOM_MAX_GLOBAL_STEPS:
                return generateThreadSequenceMaxSteps(p, steps);
            case RANDOM_MAX_GLOBAL_STEPS_IGNORE_COMPLETE_THREADS:
                return generateThreadSequenceIgnoreFinishedThreads(p, steps);
            default:
                throw new NotImplementedException();
        }
    }

    /**
     * Generates a thread sequence with max steps globally (not per thread)
     * @param p Program to generate sequence from
     * @param numSteps Max number of steps to take
     * @return Array of ordered thread IDs representing the sequence
     */
    private static int[] generateThreadSequenceMaxSteps(Program p, int numSteps)
    {
        int[] seq = new int[numSteps];
        int[] usedThreads = p.getUsedThreadIDs();

        Random r = new Random();
        for(int i = 0; i < numSteps; i++)
        {
            seq[i] = usedThreads[r.nextInt(usedThreads.length)];
        }

        return seq;
    }

    /**
     * Similar to generateThreadSequenceMaxSteps but ensures a thread isn't added to sequence when it has no instructions left
     * @param p Program to generate sequence from
     * @param numSteps Max number of steps to take
     * @return Array of ordered thread IDs representing the sequence
     */
    private static int[] generateThreadSequenceIgnoreFinishedThreads(Program p, int numSteps)
    {
        GenerationSim sim = new GenerationSim();
        Machine machine = sim.getMachine();
        SimulatorThread[] threads = new SimulatorThread[p.getUsedThreadIDs().length];

        for (int i = 0; i < p.getUsedThreadIDs().length; i++)
        {
            threads[i] = machine.createThread(i);
            threads[i].queueInstructions(p.getInstructionsForThread(i));
        }
        sim.setInitialMemory(p.getInitialMemory());

        ArrayList<Integer> seq = new ArrayList<>();
        Integer[] usedThreads = new Integer[p.getUsedThreadIDs().length];

        // Convert int[] to Integer[] essentially
        for(int i = 0; i < usedThreads.length; i++)
        {
            usedThreads[i] = p.getUsedThreadIDs()[i];
        }

        // HashSet of all thread ids with possible instructions left
        Set<Integer> aliveThreads = new HashSet<Integer>(Arrays.asList(usedThreads));

        Random r = new Random();

        // Loop through up to numSteps (max)
        for(int i = 0; i < numSteps; i++)
        {
            //
            if(aliveThreads.size() > 0)
            {
                // Choose random thread from set of alive threads, simulate step and add to seq
                int randThread = (int) aliveThreads.toArray()[r.nextInt(aliveThreads.size())];
                sim.stepForward(randThread);
                seq.add(randThread);

                // If no instructions left remove thread from alive set
                if(machine.getThread(randThread).getInstructionPointer() >= machine.getThread(randThread).getInstructionsList().size())
                {
                    aliveThreads.remove(randThread);
                }

            }
            // Break if no threads with instructions left
            else
            {
                break;
            }

        }

        // Convert ArrayList<Integer> to int[] and return
        return seq.stream().mapToInt(i -> i).toArray();
    }
}

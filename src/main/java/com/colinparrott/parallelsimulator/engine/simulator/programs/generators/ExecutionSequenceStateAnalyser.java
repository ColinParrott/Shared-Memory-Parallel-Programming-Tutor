package com.colinparrott.parallelsimulator.engine.simulator.programs.generators;

import com.colinparrott.parallelsimulator.engine.hardware.Machine;
import com.colinparrott.parallelsimulator.engine.hardware.Memory;
import com.colinparrott.parallelsimulator.engine.hardware.SimulatorThread;
import com.colinparrott.parallelsimulator.engine.instructions.Instruction;
import com.colinparrott.parallelsimulator.engine.simulator.programs.Program;
import com.rits.cloning.Cloner;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Provides methods for analysing an execution sequence (list of thread ids)
 */
public class ExecutionSequenceStateAnalyser
{
    @SuppressWarnings("Duplicates")
    public static ArrayList<Memory> calculateMemoryStates(Program p, int[] threadSequence)
    {
        Cloner cloner = new Cloner();
        // Stores values of variables only if they're non-zero
        ArrayList<Memory> memoryStates = new ArrayList<>();

        GenerationSim sim = new GenerationSim();
        Machine machine = sim.getMachine();
        SimulatorThread[] threads = new SimulatorThread[p.getUsedThreadIDs().length];

        for (int i = 0; i < p.getUsedThreadIDs().length; i++)
        {
            threads[i] = machine.createThread(i);
            threads[i].queueInstructions(p.getInstructionsForThread(i));
        }

        sim.setInitialMemory(p.getInitialMemory());


        memoryStates.add(cloner.deepClone(sim.getMachine().getMemory()));
        for (int thread : threadSequence)
        {
            sim.stepForward(thread);
//            System.out.println(sim.getMachine().getMemory());
            memoryStates.add(cloner.deepClone(sim.getMachine().getMemory()));
        }

        return memoryStates;
    }

    public static HashMap<Integer, ArrayList<Instruction>> getListOfInstructions(Program p, int[] threadSequence)
    {
        Cloner cloner = new Cloner();
        HashMap<Integer, ArrayList<Instruction>> instructions = new HashMap<>();

        GenerationSim sim = new GenerationSim();
        Machine machine = sim.getMachine();
        SimulatorThread[] threads = new SimulatorThread[p.getUsedThreadIDs().length];

        for (int i = 0; i < p.getUsedThreadIDs().length; i++)
        {
            threads[i] = machine.createThread(i);
            threads[i].queueInstructions(p.getInstructionsForThread(i));
        }

        sim.setInitialMemory(p.getInitialMemory());


        for (int thread : threadSequence)
        {
            instructions.computeIfAbsent(thread, k -> new ArrayList<>());

            instructions.get(thread).add(cloner.deepClone(sim.getMachine().getThread(thread).getNextInstruction()));
            sim.stepForward(thread);
        }
        return instructions;


    }

    @SuppressWarnings("Duplicates")
    public static ArrayList<Memory> calculateMemoryStates(Program p, ArrayList<Integer> threadSequence)
    {
        Cloner cloner = new Cloner();
        // Stores values of variables only if they're non-zero
        ArrayList<Memory> memoryStates = new ArrayList<>();

        GenerationSim sim = new GenerationSim();
        Machine machine = sim.getMachine();
        SimulatorThread[] threads = new SimulatorThread[p.getUsedThreadIDs().length];

        for (int i = 0; i < p.getUsedThreadIDs().length; i++)
        {
            threads[i] = machine.createThread(i);
            threads[i].queueInstructions(p.getInstructionsForThread(i));
        }

        sim.setInitialMemory(p.getInitialMemory());


        for (int thread : threadSequence)
        {
            sim.stepForward(thread);
//            System.out.println(sim.getMachine().getMemory());
            memoryStates.add(cloner.deepClone(sim.getMachine().getMemory()));
        }

        return memoryStates;
    }
}

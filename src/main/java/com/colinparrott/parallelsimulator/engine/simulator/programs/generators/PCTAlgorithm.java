package com.colinparrott.parallelsimulator.engine.simulator.programs.generators;

import com.colinparrott.parallelsimulator.engine.hardware.Machine;
import com.colinparrott.parallelsimulator.engine.hardware.MemoryLocation;
import com.colinparrott.parallelsimulator.engine.hardware.SimulatorThread;
import com.colinparrott.parallelsimulator.engine.instructions.*;
import com.colinparrott.parallelsimulator.engine.simulator.Simulator;
import com.colinparrott.parallelsimulator.engine.simulator.programs.Program;
import com.colinparrott.parallelsimulator.programs.ProgramFileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class PCTAlgorithm
{
    private static final Logger logger = LoggerFactory.getLogger(PCTAlgorithm.class);

    public static void main(String[] args)
    {
        ArrayList<Program> programs = ProgramFileReader.readPrograms();

        for (Program p : programs)
        {
//            if (p.getName().equals("x++"))
//            {
            System.out.println(p.getName());
            ArrayList<ArrayList<Instruction>> instructions = new ArrayList<>();

            boolean programHasLoop = false;
            int instructionCount = 0;
            for (int i = 0; i < p.getUsedThreadIDs().length; i++)
            {
                instructions.add(p.getInstructionsForThread(i));

                if (GenUtils.containsLoop(p.getInstructionsForThread(i)))
                    programHasLoop = true;

                // todo: only count instructions inside atomic blocks as one instruction
                for (Instruction instr : p.getInstructionsForThread(i))
                {
                    if (!(instr instanceof Label || instr instanceof Atomic || instr instanceof EndAtomic))
                        instructionCount++;
                }
            }
//            System.out.println("Instruction count: " + instructionCount);
            int maxSteps = programHasLoop ? 20 : instructionCount;
            System.out.println("Max steps: " + maxSteps);

            ArrayList<Integer> seq = generateSequence(instructions, maxSteps, 6);
            System.out.print("[");
            for (int i : seq)
            {
                System.out.print(i + " ");
            }
            System.out.println("]");

            Simulator simulator = new GenerationSim();
            simulator.loadProgram(p);

            for (int i : seq)
            {
                simulator.getMachine().executeInstruction(i);
            }

            for (MemoryLocation memoryLocation : getRelevantVariables(p))
            {
                System.out.print(memoryLocation + ":" + simulator.getMachine().getMemory().getValue(memoryLocation) + " ");
            }

            System.out.println();
//            }


        }
    }

    public static ArrayList<Integer> generateSequence(ArrayList<ArrayList<Instruction>> threads, int steps, int depth)
    {
        ArrayList<Integer> schedule = new ArrayList<>();
        ArrayList<Integer> priorities = new ArrayList<>();
        ArrayList<Integer> changePoints;
        int currentThread;

        GenerationSim sim = new GenerationSim();
        Machine machine = sim.getMachine();
        SimulatorThread[] simThreads = new SimulatorThread[threads.size()];

        for (int i = 0; i < threads.size(); i++)
        {
            simThreads[i] = machine.createThread(i);
            simThreads[i].queueInstructions(threads.get(i));
        }

        Set<Integer> aliveThreads = new HashSet<>();

        // Set initial thread priorities randomly
        for (int i = 0; i < threads.size(); i++)
        {
            aliveThreads.add(i);
            priorities.add(depth + i);
            Collections.shuffle(priorities);
        }

        // Generate array containing change points
        changePoints = generateChangePointsArray(steps, depth);

        while (schedule.size() < steps && aliveThreads.size() != 0)
        {
            currentThread = maxIndex(priorities);
            schedule.add(currentThread);
            sim.stepForward(currentThread);

            for (int i = 0; i < depth - 1; i++)
            {
                if (schedule.size() == changePoints.get(i))
                {
                    priorities.set(currentThread, depth - i);
                }
            }

            if (machine.getThread(currentThread).getInstructionPointer() >= machine.getThread(currentThread).getInstructionsList().size())
            {
                aliveThreads.remove(currentThread);
                priorities.set(currentThread, -1);
            }
        }

        return schedule;

    }

    private static ArrayList<Integer> generateChangePointsArray(int steps, int depth)
    {
        ArrayList<Integer> arr = new ArrayList<>(depth - 1);

        for (int i = 0; i < depth - 1; i++)
        {
            int r;
            do
            {
                r = new Random().nextInt(steps) + 1;
            } while (arr.contains(r));

            arr.add(r);
        }

//        System.out.println("Change points: " + arr);

        return arr;
    }

    private static int maxIndex(ArrayList<Integer> priorities)
    {
        int index = 0;
        int max = priorities.get(0);

        for (int i = 1; i < priorities.size(); i++)
        {

            if (priorities.get(i) > max)
            {
                index = i;
                max = priorities.get(i);
            }
        }

        return index;
    }

    @SuppressWarnings("Duplicates")
    private static MemoryLocation[] getRelevantVariables(Program p)
    {
        HashSet<MemoryLocation> variables = new HashSet<>();

        for (int i : p.getUsedThreadIDs())
        {
            for (Instruction instruction : p.getInstructionsForThread(i))
            {
                if (instruction.getKeyword() == InstructionKeyword.ST)
                {
                    Store s = (Store) instruction;
                    variables.add(s.getMemoryLocation());
                }
                else if (instruction.getKeyword() == InstructionKeyword.LD)
                {
                    Load l = (Load) instruction;
                    variables.add(l.getMemoryLocation());
                }
            }
        }

        return variables.toArray(new MemoryLocation[0]);
    }
}

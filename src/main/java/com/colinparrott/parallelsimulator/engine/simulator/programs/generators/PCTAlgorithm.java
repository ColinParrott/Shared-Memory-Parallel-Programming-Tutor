package com.colinparrott.parallelsimulator.engine.simulator.programs.generators;

import com.colinparrott.parallelsimulator.engine.hardware.Machine;
import com.colinparrott.parallelsimulator.engine.hardware.SimulatorThread;
import com.colinparrott.parallelsimulator.engine.instructions.Instruction;
import com.colinparrott.parallelsimulator.engine.simulator.programs.Program;
import com.colinparrott.parallelsimulator.programs.ProgramFileReader;

import java.util.*;

public class PCTAlgorithm
{

    public static void main(String[] args)
    {
        ArrayList<Program> programs = ProgramFileReader.readPrograms();

        for (Program p : programs)
        {
            System.out.println(p.getName());
            ArrayList<ArrayList<Instruction>> instructions = new ArrayList<>();
            for (int i = 0; i < p.getUsedThreadIDs().length; i++)
            {
                instructions.add(p.getInstructionsForThread(i));
            }

            ArrayList<Integer> seq = generateSequence(instructions, 20, 1);
            System.out.print("[");
            for (int i : seq)
            {
                System.out.print(i + " ");
            }
            System.out.println("]");
        }
    }

    public static ArrayList<Integer> generateSequence(ArrayList<ArrayList<Instruction>> threads, int steps, int depth)
    {
        ArrayList<Integer> schedule = new ArrayList<>();
        ArrayList<Integer> priorities = new ArrayList<>();
        ArrayList<Integer> changePoints = new ArrayList<>();
        int currentThread = -1;

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
}

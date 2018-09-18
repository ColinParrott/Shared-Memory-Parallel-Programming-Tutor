package com.colinparrott.parallelsimulator.engine.simulator;

import com.colinparrott.parallelsimulator.engine.hardware.Machine;
import com.colinparrott.parallelsimulator.engine.hardware.MemoryLocation;
import com.colinparrott.parallelsimulator.engine.hardware.Register;
import com.colinparrott.parallelsimulator.engine.hardware.SimulatorThread;
import com.colinparrott.parallelsimulator.engine.instructions.AddImmediate;
import com.colinparrott.parallelsimulator.engine.instructions.Instruction;
import com.colinparrott.parallelsimulator.engine.instructions.Load;
import com.colinparrott.parallelsimulator.engine.instructions.Store;

import java.util.ArrayList;
import java.util.Scanner;

public class CLISimulator extends Simulator
{

    private static final char[] validCommands = {'0', '1', '2', '3', 'b'};

    public void start()
    {
        runIncrementExample();
    }

    private void runIncrementExample()
    {
        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new Load(0, MemoryLocation.x));
        instructions.add(new AddImmediate(0, 0, 1));
        instructions.add(new Store(0, MemoryLocation.x));

        SimulatorThread[] threads = new SimulatorThread[2];
        threads[0] = machine.createThread(0);
        threads[1] = machine.createThread(1);
        threads[0].queueInstructions(instructions);
        threads[1].queueInstructions(instructions);

        stateHistory.addState(machine);

        System.out.println("Loaded program (" + threads.length + " threads): \n{\nx=0;\nx++ // x++\n}\n");
        printState(new MemoryLocation[]{MemoryLocation.x}, new int[]{0, 1});

        Scanner scanner = new Scanner(System.in);
        while (threadsHaveInstructionsLeft())
        {
            char input;
            do
            {
                System.out.println("Enter number of thread to advance or 'b' to go back a step:");
                input = scanner.next().charAt(0);
            } while (!isValidCommand(input));

            if (input == 'b')
            {
                stepBackward();
                System.out.println("Stepped backwards, new state:\n");
                printState(new MemoryLocation[]{MemoryLocation.x}, new int[]{0, 1});
            }
            else if (input == '0' || input == '1' || input == '2' || input == '3')
            {

                int threadId = Integer.valueOf(String.valueOf(input));

                if (threadId < threads.length)
                {
                    if (machine.getThread(threadId).getNextInstruction() != null)
                    {
                        stepForward(threadId);
                        printState(new MemoryLocation[]{MemoryLocation.x}, new int[]{0, 1});
                    }
                    else
                    {
                        System.out.println("Thread " + threadId + " has no instructions left!\n");
                    }
                }
                else
                {
                    System.out.println("Thread not in use!");
                }
            }



        }

        System.out.println("\nAll instructions executed.");

    }

    private boolean threadsHaveInstructionsLeft()
    {
        for (int i = 0; i < machine.numberUsedThreads(); i++)
        {
            if (machine.getThread(i) != null && machine.getThread(i).getNextInstruction() != null)
                return true;
        }

        return false;
    }

    private boolean isValidCommand(char check)
    {
        for (char c : validCommands)
        {
            if (c == check)
                return true;
        }

        return false;
    }

    private void printState(MemoryLocation[] variables, int[] threadIds)
    {
        System.out.println("--- MEMORY ---");
        for (MemoryLocation v : variables)
        {
            System.out.println(v + ": " + machine.getMemory().getValue(MemoryLocation.x));
        }

        for (int id : threadIds)
        {
            System.out.println("--- THREAD " + id + " ---");
            SimulatorThread thread = machine.getThread(id);

            for (Register r : thread.getRegisters())
            {
                System.out.print(String.format("$R%d: %d\t", r.getRegisterNum(), r.getValue()));
            }

            System.out.println();
            System.out.println("Next Instruction: " + thread.getNextInstruction());
            System.out.println("Pointer: " + thread.getInstructionPointer());

        }
        System.out.println();

    }

    private char validateInput(char... validChars)
    {
        return '?';
    }
}

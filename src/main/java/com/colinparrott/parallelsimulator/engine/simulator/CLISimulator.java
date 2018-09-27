package com.colinparrott.parallelsimulator.engine.simulator;

import com.colinparrott.parallelsimulator.engine.hardware.*;
import com.colinparrott.parallelsimulator.engine.instructions.*;

import java.util.ArrayList;
import java.util.Scanner;

public class CLISimulator extends Simulator
{

    private static final char[] validCommands = {'0', '1', '2', '3', 'b'};

    public void start()
    {
        System.out.println("Enter a program number to run:\n");
        System.out.println("1) Single variable increment ( x++ // x++)");
        System.out.println("2) Single variable increment using atomic");
        System.out.println("3) Simple await example");
        System.out.println();

        int selection = 0;

        Scanner scanner = new Scanner(System.in);

        do
        {
            String s = scanner.nextLine();
            boolean validNum = true;

            try
            {
                selection = Integer.valueOf(s);
            }
            catch (NumberFormatException e)
            {
                validNum = false;
            }

            if(!validNum || (selection != 1 && selection != 2 && selection != 3))
                System.out.println("Please enter either 1, 2 or 3.\n");

        }while(selection != 1 && selection != 2 && selection != 3);

        if(selection == 1) runIncrementExample();
        else if(selection == 2) runIncrementExampleAtomic();
        else if(selection == 3) runAwaitExample();
        else System.out.println("Don't know how we got here!");
    }

    private void runIncrementExampleAtomic()
    {
        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new Atomic());
        instructions.add(new Load(0, MemoryLocation.x));
        instructions.add(new AddImmediate(0, 0, 1));
        instructions.add(new Store(0, MemoryLocation.x));
        instructions.add(new EndAtomic());

        Memory initMemory = new Memory();
        Program p = new Program(initMemory);
        p.setInstructionsForThread(0, instructions);
        p.setInstructionsForThread(1, instructions);

        System.out.println("Loaded program (" + p.getUsedThreadIDs().length + " threads): \n{\nx=0;\n<x++;> // <x++;>\n}\n");
        executeProgram(p, new MemoryLocation[]{MemoryLocation.x});
    }

    private void runAwaitExample()
    {
        ArrayList<Instruction> instructsThreadOne = new ArrayList<>();
        instructsThreadOne.add(new LoadImmediate(0, 25));
        instructsThreadOne.add(new Store(0, MemoryLocation.a));
        instructsThreadOne.add(new LoadImmediate(0, 1));
        instructsThreadOne.add(new Store(0, MemoryLocation.x));


        ArrayList<Instruction> instructsThreadTwo = new ArrayList<>();
        instructsThreadTwo.add(new Atomic());
        instructsThreadTwo.add(new Await(MemoryLocation.x, MemoryLocation.z, AwaitComparator.EQ)); // x == 1
        instructsThreadTwo.add(new Load(0, MemoryLocation.a));
        instructsThreadTwo.add(new Store(0, MemoryLocation.x));
        instructsThreadTwo.add(new EndAtomic());

        Memory initMemory = new Memory();
        initMemory.setVariable(MemoryLocation.z, 1);
        Program p = new Program(initMemory);
        p.setInstructionsForThread(0, instructsThreadOne);
        p.setInstructionsForThread(1, instructsThreadTwo);

        System.out.println("Loaded program (" + p.getUsedThreadIDs().length + " threads): \n{\na=0; x=0; z=1;\nco\n {a=25; x=1;}\n //\n <await (x==z) x=a;>\noc\n}\n");
        executeProgram(p, new MemoryLocation[]{MemoryLocation.a, MemoryLocation.x, MemoryLocation.z});
    }

    private void runIncrementExample()
    {

        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new Load(0, MemoryLocation.x));
        instructions.add(new AddImmediate(0, 0, 1));
        instructions.add(new Store(0, MemoryLocation.x));

        Memory initMemory = new Memory();
        Program p = new Program(initMemory);
        p.setInstructionsForThread(0, instructions);
        p.setInstructionsForThread(1, instructions);

        System.out.println("Loaded program (" + p.getUsedThreadIDs().length + " threads): \n{\nx=0;\nx++; // x++;\n}\n");
        executeProgram(p, new MemoryLocation[]{MemoryLocation.x});

    }

    private void executeProgram(Program p, MemoryLocation[] relevantVariables)
    {
        SimulatorThread[] threads = new SimulatorThread[p.getUsedThreadIDs().length];

        for(int i = 0; i < p.getUsedThreadIDs().length; i++)
        {
            threads[i] = machine.createThread(i);
            threads[i].queueInstructions(p.getInstructionsForThread(i));
        }

        setInitialMemory(p.getInitialMemory());

        stateHistory.addState(machine);
        printState(relevantVariables, new int[]{0, 1});

        Scanner scanner = new Scanner(System.in);
        while (threadsHaveInstructionsLeft())
        {
            char input;
            do
            {
                System.out.println("Enter number of thread to advance or 'b' to go back a step:");
                input = scanner.next().charAt(0);
            } while (!isValidCommand(input));

            System.out.println();

            if (input == 'b')
            {
                stepBackward();
                System.out.println("Stepped backwards, new state:\n");
                printState(relevantVariables, new int[]{0, 1});
            }
            else if (input == '0' || input == '1' || input == '2' || input == '3')
            {

                int threadId = Integer.valueOf(String.valueOf(input));

                if (threadId < threads.length)
                {
                    if (machine.getThread(threadId).getNextInstruction() != null)
                    {
                        stepForward(threadId);
                        printState(relevantVariables, new int[]{0, 1});
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
        System.out.println("Press enter to exit...");
        new Scanner(System.in).nextLine();
    }

    /**
     * Check if a thread has any instructions left
     * @return True if instructions left; false otherwise
     */
    private boolean threadsHaveInstructionsLeft()
    {
        for (int i = 0; i < machine.numberUsedThreads(); i++)
        {
            if (machine.getThread(i) != null && machine.getThread(i).getNextInstruction() != null)
                return true;
        }

        return false;
    }

    /**
     * Helper function that checks if char is in list of valid command characters
     * @param check Character to check
     * @return True if valid, false otherwise
     */
    private boolean isValidCommand(char check)
    {
        for (char c : validCommands)
        {
            if (c == check)
                return true;
        }

        return false;
    }

    /**
     * Prints the state of the memory and each thread
     * @param variables Variables in memory to show
     * @param threadIds Threads to show
     */
    private void printState(MemoryLocation[] variables, int[] threadIds)
    {
        System.out.println("--- MEMORY ---");
        for (MemoryLocation v : variables)
        {
            System.out.println(v + ": " + machine.getMemory().getValue(v));
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

             Instruction next = thread.getNextInstruction();

             if(!(next instanceof Atomic))
                System.out.println("Next Instruction: " + thread.getNextInstruction());
             else
             {
                 ArrayList<Instruction> batch = new ArrayList<>();
                 for(int i = thread.getInstructionPointer(); i < thread.getInstructionsList().size(); i++)
                 {
                     Instruction instruction = thread.getInstructionsList().get(i);
                     batch.add(instruction);
                     if(instruction instanceof EndAtomic) break;
                 }

                 StringBuilder s = new StringBuilder();
                 s.append("Next Instruction: ");

                 for(Instruction i : batch)
                 {
                     s.append(i.toString());
                     s.append("\t");
                 }

                 System.out.println(s.toString());
             }

        }
        System.out.println();


    }

}

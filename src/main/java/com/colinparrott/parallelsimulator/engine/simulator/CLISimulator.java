package com.colinparrott.parallelsimulator.engine.simulator;

import com.colinparrott.parallelsimulator.engine.hardware.Memory;
import com.colinparrott.parallelsimulator.engine.hardware.MemoryLocation;
import com.colinparrott.parallelsimulator.engine.hardware.Register;
import com.colinparrott.parallelsimulator.engine.hardware.SimulatorThread;
import com.colinparrott.parallelsimulator.engine.instructions.*;
import com.colinparrott.parallelsimulator.engine.simulator.programs.Program;
import com.colinparrott.parallelsimulator.engine.simulator.programs.ProgramList;
import com.colinparrott.parallelsimulator.engine.simulator.programs.generators.ExecutionSequenceStateAnalyser;
import com.colinparrott.parallelsimulator.engine.simulator.programs.generators.GenUtils;
import com.colinparrott.parallelsimulator.engine.simulator.programs.generators.askoutcome.GenMethod;
import com.colinparrott.parallelsimulator.engine.simulator.programs.generators.askoutcome.ThreadSequenceGen;
import com.colinparrott.parallelsimulator.engine.simulator.programs.generators.heuristics.Scorer;
import javafx.util.Pair;

import java.util.*;

public class CLISimulator extends Simulator
{

    private static final char[] validCommands = {'0', '1', '2', '3', '4', '5', 'b'};

    public void start()
    {

        System.out.println("Select a generation method\n");
        System.out.println("1) Random seq (max 10 steps)");

        switch (getValidInt(1, 3))
        {
            case 1:
                useGenMethod(GenMethod.RANDOM_MAX_GLOBAL_STEPS_IGNORE_COMPLETE_THREADS);
                break;
        }

//        useGenMethod(GenMethod.RANDOM_MAX_GLOBAL_STEPS_IGNORE_COMPLETE_THREADS);

//        originalCLI();

    }

    private void useGenMethod(GenMethod method)
    {
        System.out.println("Choose a program\n");
        Program p = getProgram();

        System.out.println("Choose a scoring metric\n");
        System.out.println("1) Rarest Sequence in 10000 sims");

        switch (getValidInt(1, 1))
        {
            case 1:
                int[][] sequences = new int[10000][];
                for (int i = 0; i < sequences.length; i++)
                {
                    int[] seq = ThreadSequenceGen.generateThreadSequence(p, 10, method);
                    sequences[i] = seq;
                }

                System.out.println("done");

                Pair<int[], Memory> result = Scorer.getMostUniqueSeq(p, sequences);
                System.out.print("Chosen sequence:");
                for (int j = 0; j < result.getKey().length; j++) System.out.print(" " + result.getKey()[j]);
                System.out.println();
                System.out.println("--- MEMORY ---");

                for (MemoryLocation v : result.getValue().getVariables().keySet())
                {
                    System.out.print(v + ":" + result.getValue().getValue(v) + " ");
                }
        }


    }

    private Program getProgram()
    {
        Program p;
        ProgramList programList = new ProgramList();

        System.out.println("1) x++");
        System.out.println("2) x++ (atomic)");
        System.out.println("3) Await flag");
        System.out.println("4) b=a+a");
        System.out.println("5) <b=a+a>");
        System.out.println();

        int selection = getValidInt(1, 5);


        switch (selection)
        {
            case 1:
                p = programList.loadXPlusPlusTwoThreads();
                return p;
            case 2:
                p = programList.loadXPlusPlusAtomicTwoThreads();
                return p;
            case 3:
                p = programList.loadAwaitFlag();
                return p;
            case 4:
                p = programList.loadBEqualsAPlusA();
                return p;
            case 5:
                p = programList.loadBEqualsAPlusAAtomic();
                return p;
            default:
                System.out.println("Don't know how we got here!");
                return null;
        }
    }

    private int getValidInt(int min, int max)
    {
        Scanner scanner = new Scanner(System.in);
        int input = min - 1;

        do
        {

            try
            {
                input = Integer.valueOf(scanner.nextLine());
            }
            catch (Exception e)
            {
                input = min - 1;

            }

            if (input < min || input > max)
                System.out.println("Enter a number between " + min + " and " + max);
        } while (input < min || input > max);

        return input;
    }

    private void originalCLI()
    {
        ProgramList programList = new ProgramList();

        System.out.println("Enter a program number to run:\n");
        System.out.println("1) Single variable increment ( x++ // x++)");
        System.out.println("2) Single variable increment using atomic");
        System.out.println("3) Simple await example");
        System.out.println("4) x++ Game");
        System.out.println("5) x++ Game (ignore finished threads)");
        System.out.println("6) x++ Game Atomic (ignore finished threads)");

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
            } catch (NumberFormatException e)
            {
                validNum = false;
            }

            if (!validNum || (selection != 1 && selection != 2 && selection != 3 && selection != 4 && selection != 5 && selection != 6))
                System.out.println("Please a number between 1-6.\n");

        } while (selection != 1 && selection != 2 && selection != 3 && selection != 4 && selection != 5 && selection != 6);

        Program p;
        switch (selection)
        {
            case 1:
                p = programList.loadXPlusPlusTwoThreads();
                System.out.println("Loaded program (" + p.getUsedThreadIDs().length + " threads): \n{\nx=0;\nx++; // x++;\n}\n");
                executeProgram(p);
                break;
            case 2:
                p = programList.loadXPlusPlusAtomicTwoThreads();
                System.out.println("Loaded program (" + p.getUsedThreadIDs().length + " threads): \n{\nx=0;\n<x++;> // <x++;>\n}\n");
                executeProgram(p);
                break;
            case 3:
                p = programList.loadAwaitFlag();
                System.out.println("Loaded program (" + p.getUsedThreadIDs().length + " threads): \n{\na=0; x=0; z=1;\nco\n {a=25; x=1;}\n //\n <await (x==1) x=a;>\noc\n}\n");
                executeProgram(p);
                break;
            case 4:
                p = programList.loadXPlusPlusTwoThreads();
                executeGame(p, 6, GenMethod.RANDOM_MAX_GLOBAL_STEPS);
                break;
            case 5:
                p = programList.loadXPlusPlusTwoThreads();
                executeGame(p, 6, GenMethod.RANDOM_MAX_GLOBAL_STEPS_IGNORE_COMPLETE_THREADS);
                break;
            case 6:
                p = programList.loadXPlusPlusAtomicTwoThreads();
                executeGame(p, 6, GenMethod.RANDOM_MAX_GLOBAL_STEPS_IGNORE_COMPLETE_THREADS);
                break;
            default:
                System.out.println("Don't know how we got here!");
        }
    }

    private void executeGame(Program p, int steps, GenMethod method)
    {
        int[] seq = ThreadSequenceGen.generateThreadSequence(p, steps, method);
        ArrayList<Memory> memories = ExecutionSequenceStateAnalyser.calculateMemoryStates(p, seq);
        HashMap<Integer, ArrayList<Instruction>> instructions = ExecutionSequenceStateAnalyser.getListOfInstructions(p, seq);

        ArrayList<MemoryLocation> changedVariables = new ArrayList<>();
        Memory initial = memories.get(0);
        Memory end = memories.get(memories.size() - 1);
        ArrayList<MemoryLocation> changed = GenUtils.changedVariables(initial, end);

        Random r = new Random();
        MemoryLocation chosenVar = changed.get(r.nextInt(changed.size()));
        int answer = end.getValue(chosenVar);

        for(int i : p.getUsedThreadIDs())
        {
            System.out.println("-- THREAD " + i + " --");
            for(Instruction ins : p.getInstructionsForThread(i))
            {
                System.out.println(ins);
            }
            System.out.println();
        }

        System.out.println("-- MEMORY --\n" + p.getInitialMemory());

        System.out.println();
        System.out.print("Sequence: ");
        for(int j : seq)
        {
            System.out.print(j + " ");
        }
        System.out.println();



        Scanner scanner = new Scanner(System.in);
        int selection = -1;
        do
        {
            System.out.println("What will the value of " + chosenVar + " be at the end?");
            String s = scanner.nextLine();
            boolean validNum = true;

            try
            {
                selection = Integer.valueOf(s);
            } catch (NumberFormatException e)
            {
                validNum = false;
            }

            if (!validNum)
                System.out.println("Please enter an integer.\n");
            else if (selection != answer)
                System.out.println("Incorrect. Try Again!");

        } while (selection != answer);

        System.out.println("Correct! \n" + chosenVar + ": " + answer + "\n");

        String input = "";
        while (!input.equals("retry") && !input.equals("reset") && !input.equals("quit"))
        {
            System.out.println("Enter 'retry' to play again, 'reset' to restart program or 'quit' to quit!");
            input = scanner.nextLine();
            System.out.println();
        }

        switch (input)
        {
            case "retry":
                executeGame(p, steps, method);
                break;
            case "reset":
                start();
                break;
            case "quit":
                System.exit(0);
                break;
            default:
                System.out.println("Don't know how we got here!");
        }

    }

    private void executeProgram(Program p)
    {
        SimulatorThread[] threads = new SimulatorThread[p.getUsedThreadIDs().length];

        for (int i = 0; i < p.getUsedThreadIDs().length; i++)
        {
            threads[i] = machine.createThread(i);
            threads[i].queueInstructions(p.getInstructionsForThread(i));
        }

        MemoryLocation[] relevantVariables = getRelevantVariables(p);

        setInitialMemory(p.getInitialMemory());

        stateHistory.addState(machine);
        printState(relevantVariables, p.getUsedThreadIDs());

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
                printState(relevantVariables, p.getUsedThreadIDs());
            } else if (input == '0' || input == '1' || input == '2' || input == '3')
            {

                int threadId = Integer.valueOf(String.valueOf(input));

                if (threadId < threads.length)
                {
                    if (machine.getThread(threadId).getNextInstruction() != null)
                    {
                        stepForward(threadId);
                        printState(relevantVariables, p.getUsedThreadIDs());
                    } else
                    {
                        System.out.println("Thread " + threadId + " has no instructions left!\n");
                    }
                } else
                {
                    System.out.println("Thread not in use!");
                }
            }


        }

        System.out.println("\nAll instructions executed.");
        System.out.println("Press enter to exit...");
        new Scanner(System.in).nextLine();
    }

    private MemoryLocation[] getRelevantVariables(Program p)
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

    /**
     * Check if a thread has any instructions left
     *
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
     *
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
     *
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

            if (!(next instanceof Atomic))
                System.out.println("Next Instruction: " + thread.getNextInstruction());
            else
            {
                ArrayList<Instruction> batch = new ArrayList<>();
                for (int i = thread.getInstructionPointer(); i < thread.getInstructionsList().size(); i++)
                {
                    Instruction instruction = thread.getInstructionsList().get(i);
                    batch.add(instruction);
                    if (instruction instanceof EndAtomic) break;
                }

                StringBuilder s = new StringBuilder();
                s.append("Next Instruction: ");

                for (Instruction i : batch)
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

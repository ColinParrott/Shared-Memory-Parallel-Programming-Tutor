package com.colinparrott.parallelsimulator.engine.simulator;

import com.colinparrott.parallelsimulator.engine.hardware.Memory;
import com.colinparrott.parallelsimulator.engine.hardware.MemoryLocation;
import com.colinparrott.parallelsimulator.engine.hardware.SimulatorThread;
import com.colinparrott.parallelsimulator.engine.instructions.Instruction;
import com.colinparrott.parallelsimulator.engine.instructions.InstructionKeyword;
import com.colinparrott.parallelsimulator.engine.instructions.Load;
import com.colinparrott.parallelsimulator.engine.instructions.Store;
import com.colinparrott.parallelsimulator.engine.simulator.programs.CLIUtils;
import com.colinparrott.parallelsimulator.engine.simulator.programs.Program;
import com.colinparrott.parallelsimulator.engine.simulator.programs.ProgramList;
import com.colinparrott.parallelsimulator.engine.simulator.programs.generators.ExecutionSequenceStateAnalyser;
import com.colinparrott.parallelsimulator.engine.simulator.programs.generators.GenUtils;
import com.colinparrott.parallelsimulator.engine.simulator.programs.generators.askoutcome.GenMethod;
import com.colinparrott.parallelsimulator.engine.simulator.programs.generators.askoutcome.ThreadSequenceGen;
import com.colinparrott.parallelsimulator.engine.simulator.programs.generators.heuristics.BatchScorer;
import com.colinparrott.parallelsimulator.engine.simulator.programs.generators.heuristics.ScoreMethod;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CLISimulator extends Simulator
{

    private static final char[] validCommands = {'0', '1', '2', '3', '4', '5', 'b'};
    private String[] args;
    private static ProgramList programList;

    public CLISimulator(String[] args)
    {
        this.args = args;
        programList = new ProgramList();
    }

    public void start()
    {
        Program[] programs = new Program[]{programList.loadXPlusPlusTwoThreads(), programList.loadXPlusPlusAtomicTwoThreads(), programList.loadAwaitFlag(), programList.loadBEqualsAPlusA(), programList.loadBEqualsAPlusAAtomic(), programList.loadWhileLoop()};
        GenMethod[] genMethods = new GenMethod[]{GenMethod.RANDOM_MAX_GLOBAL_STEPS, GenMethod.PROBABILISTIC_MOST_STORES_STATIC, GenMethod.PROBABILISTIC_MOST_STORES_STATIC_SHUFFLE, GenMethod.PROBABILISTIC_MOST_STORES_AND_BRANCHES_STATIC};
        ScoreMethod[] scoreMethods = new ScoreMethod[]{/*ScoreMethod.VARIABLE_CHANGE_COUNT, ScoreMethod.VARIABLE_CHANGE_START_AND_END, */ScoreMethod.COUNT_UNIQUE_OUTCOMES};

        final int numRuns = 5000;
        final int maxSteps = 15;

        HashMap<String, HashMap<String, HashMap<String, Float>>> programScores = new HashMap<>();

        ExecutorService es = Executors.newCachedThreadPool();


        for (Program p : programs)
        {

            if (!programScores.containsKey(p.getName()))
                programScores.put(p.getName(), new HashMap<>());

            for (ScoreMethod scoreMethod : scoreMethods)
            {
                if (!programScores.get(p.getName()).containsKey(scoreMethod.toString()))
                    programScores.get(p.getName()).put(scoreMethod.toString(), new HashMap<>());

                for (GenMethod genMethod : genMethods)
                {
                    es.execute(() -> {
                        int[][] sequences = new int[numRuns][];
                        for (int i = 0; i < sequences.length; i++)
                        {
                            sequences[i] = ThreadSequenceGen.generateThreadSequence(p, maxSteps, genMethod);
                        }

                        float score = new BatchScorer().calculateScore(sequences, p, scoreMethod) / (float) numRuns;
                        programScores.get(p.getName()).get(scoreMethod.toString()).put(genMethod.toString(), score);
                        System.out.printf("Program: %s\nGeneration: %s\nScorer: %s\nScore: %.2f\n\n", p.getName(), genMethod.toString(), scoreMethod.toString(), score);
                    });
                }

            }

        }
        es.shutdown();

        try
        {
            es.awaitTermination(5, TimeUnit.MINUTES);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

//        for (String p : programScores.keySet())
//        {
//            System.out.println(p);
//            System.out.println();
//            for(String scorer : programScores.get(p).keySet())
//            {
//                System.out.println(scorer);
//                System.out.println();
//
//                for(String gen : programScores.get(p).get(scorer).keySet())
//                {
//                    System.out.println(gen + ": " + programScores.get(p).get(scorer).get(gen));
//                }
//                System.out.println();
//            }
//            System.out.println("\n");
//        }

        JsonObject jsonObject = new Gson().toJsonTree(programScores).getAsJsonObject();

        BufferedWriter writer = null;
        try
        {
            writer = new BufferedWriter(new FileWriter("results.json"));
            writer.write(jsonObject.toString());
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


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
            }
            catch (NumberFormatException e)
            {
                validNum = false;
            }

            if (!validNum || (selection != 1 && selection != 2 && selection != 3 && selection != 4 && selection != 5 && selection != 6))
                System.out.println("Please a number between 1-6.\n");

        }
        while (selection != 1 && selection != 2 && selection != 3 && selection != 4 && selection != 5 && selection != 6);

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

        for (int i : p.getUsedThreadIDs())
        {
            System.out.println("-- THREAD " + i + " --");
            for (Instruction ins : p.getInstructionsForThread(i))
            {
                System.out.println(ins);
            }
            System.out.println();
        }

        System.out.println("-- MEMORY --\n" + p.getInitialMemory());

        System.out.println();
        System.out.print("Sequence: ");
        for (int j : seq)
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
            }
            catch (NumberFormatException e)
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
        CLIUtils.printState(machine, relevantVariables, p.getUsedThreadIDs());

        Scanner scanner = new Scanner(System.in);
        while (threadsHaveInstructionsLeft())
        {
            char input;
            do
            {
                System.out.println("Enter number of thread to advance or 'b' to go back a step:");
                input = scanner.next().charAt(0);
            } while (!CLIUtils.isValidCommand(input, validCommands));

            System.out.println();

            if (input == 'b')
            {
                stepBackward();
                System.out.println("Stepped backwards, new state:\n");
                CLIUtils.printState(machine, relevantVariables, p.getUsedThreadIDs());
            }
            else if (input == '0' || input == '1' || input == '2' || input == '3')
            {

                int threadId = Integer.valueOf(String.valueOf(input));

                if (threadId < threads.length)
                {
                    if (machine.getThread(threadId).getNextInstruction() != null)
                    {
                        stepForward(threadId);
                        CLIUtils.printState(machine, relevantVariables, p.getUsedThreadIDs());
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

}

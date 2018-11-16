package com.colinparrott.parallelsimulator.engine.simulator.programs.generators.askoutcome;

import com.colinparrott.parallelsimulator.engine.hardware.Machine;
import com.colinparrott.parallelsimulator.engine.hardware.SimulatorThread;
import com.colinparrott.parallelsimulator.engine.instructions.Instruction;
import com.colinparrott.parallelsimulator.engine.instructions.InstructionKeyword;
import com.colinparrott.parallelsimulator.engine.simulator.programs.Program;
import com.colinparrott.parallelsimulator.engine.simulator.programs.generators.GenUtils;
import com.colinparrott.parallelsimulator.engine.simulator.programs.generators.GenerationSim;
import javafx.util.Pair;
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
            case PROBABILISTIC_MOST_STORES_DYNAMIC:
                return generateProbabilisticMostStoresWithSim(p, steps);
            case PROBABILISTIC_MOST_STORES_STATIC:
                return generateProbabilisticMostStores(p, steps);
            case PROBABILISTIC_MOST_STORES_STATIC_SHUFFLE:
                return generateProbabilisticMostStoresShuffle(p, steps);
            case PROBABILISTIC_MOST_STORES_AND_BRANCHES_STATIC:
                return generateProbabilisticMostStoresBranches(p, steps);
            default:
                throw new NotImplementedException();
        }
    }

    /**
     * Generates sequence using probabilistic measures using sim to count stores left dynamically
     *
     * @param p        Program to generate sequence for
     * @param numSteps Max number of steps to gen
     * @return Int sequence of thread ids
     */
    private static int[] generateProbabilisticMostStoresWithSim(Program p, int numSteps)
    {
        ArrayList<Integer> seq = new ArrayList<>();

        Random r = new Random();

        HashMap<Integer, Integer> threadStoresCount = GenUtils.instructionPerThreadCount(p, InstructionKeyword.ST);

//        printMap(threadStoresCount, "Store counts", "Thread");

        for (int i = 0; i < numSteps; i++)
        {
            ArrayList<Pair<Integer, Float>> threadProb = calcProbs(threadStoresCount);
//            printPairList(threadProb, "Probabilities", "Thread", "Prob");
            float rand = r.nextFloat();
            int thread = selectThread(threadProb, rand);

            if (thread == -1) break;

            seq.add(thread);
//            System.out.println("Chosen: " + thread);

            threadStoresCount.put(thread, countNumberStoresLeft(p, seq, thread));
        }

        return seq.stream().mapToInt(Integer::intValue).toArray();
    }

    /**
     * Generate sequence using probabilistic measures statically counting stores
     *
     * @param p        Program to generate sequence for
     * @param numSteps Max number of steps to gen
     * @return Int sequence of thread ids
     */
    private static int[] generateProbabilisticMostStores(Program p, int numSteps)
    {
        ArrayList<Integer> seq = new ArrayList<>();

        Random r = new Random();
        HashMap<Integer, Integer> threadStoresCount = GenUtils.instructionPerThreadCount(p, InstructionKeyword.ST);

        for (int i = 0; i < numSteps; i++)
        {

            // Checks if all store counts are at 0
            boolean countsAllZero = true;
            for (int count : threadStoresCount.values())
            {
                if (count != 0)
                {
                    countsAllZero = false;
                    break;
                }
            }

            // If all counts are non-zero choose a thread weighted by store count, otherwise
            // uniformly choose a thread
            int thread;
            if (!countsAllZero)
            {
                ArrayList<Pair<Integer, Float>> threadProb = calcProbs(threadStoresCount);
                float rand = r.nextFloat();
                thread = selectThread(threadProb, rand);
            }
            else
            {
                int rand = r.nextInt(threadStoresCount.size() - 1);
                thread = threadStoresCount.keySet().stream().mapToInt(Integer::intValue).toArray()[rand];
            }


            seq.add(thread);
            threadStoresCount.put(thread, (threadStoresCount.get(thread) > 0) ? threadStoresCount.get(thread) - 1 : 0);
        }


        return seq.stream().mapToInt(Integer::intValue).toArray();
    }

    private static int[] generateProbabilisticMostStoresBranches(Program p, int numSteps)
    {
        ArrayList<Integer> seq = new ArrayList<>();

        Random r = new Random();
        HashMap<Integer, Integer> threadStoresCount = GenUtils.instructionPerThreadCount(p, InstructionKeyword.ST, InstructionKeyword.BEQ, InstructionKeyword.BGT, InstructionKeyword.BLT, InstructionKeyword.BNE);

        for (int i = 0; i < numSteps; i++)
        {

            // Checks if all store counts are at 0
            boolean countsAllZero = true;
            for (int count : threadStoresCount.values())
            {
                if (count != 0)
                {
                    countsAllZero = false;
                    break;
                }
            }

            // If all counts are non-zero choose a thread weighted by store count, otherwise
            // uniformly choose a thread
            int thread;
            if (!countsAllZero)
            {
                ArrayList<Pair<Integer, Float>> threadProb = calcProbs(threadStoresCount);
                float rand = r.nextFloat();
                thread = selectThread(threadProb, rand);
            }
            else
            {
                int rand = r.nextInt(threadStoresCount.size() - 1);
                thread = threadStoresCount.keySet().stream().mapToInt(Integer::intValue).toArray()[rand];
            }


            seq.add(thread);
            threadStoresCount.put(thread, (threadStoresCount.get(thread) > 0) ? threadStoresCount.get(thread) - 1 : 0);
        }


        return seq.stream().mapToInt(Integer::intValue).toArray();
    }

    /**
     * Generate sequence using probabilistic measures statically counting stores (shuffles end result)
     *
     * @param p        Program to generate sequence for
     * @param numSteps Max number of steps to gen
     * @return Int sequence of thread ids
     */
    private static int[] generateProbabilisticMostStoresShuffle(Program p, int numSteps)
    {
        int[] seq = generateProbabilisticMostStores(p, numSteps);
        return GenUtils.shuffle(seq);
    }


    /**
     * Generates a thread sequence with max steps globally (not per thread)
     *
     * @param p        Program to generate sequence from
     * @param numSteps Max number of steps to take
     * @return Array of ordered thread IDs representing the sequence
     */
    private static int[] generateThreadSequenceMaxSteps(Program p, int numSteps)
    {

        int[] seq = new int[numSteps];
        int[] usedThreads = p.getUsedThreadIDs();

        Random r = new Random();
        for (int i = 0; i < numSteps; i++)
        {
            seq[i] = usedThreads[r.nextInt(usedThreads.length)];
        }

        return seq;
    }

    /**
     * Similar to generateThreadSequenceMaxSteps but ensures a thread isn't added to sequence when it has no instructions left
     *
     * @param p        Program to generate sequence from
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
        for (int i = 0; i < usedThreads.length; i++)
        {
            usedThreads[i] = p.getUsedThreadIDs()[i];
        }

        // HashSet of all thread ids with possible instructions left
        Set<Integer> aliveThreads = new HashSet<>(Arrays.asList(usedThreads));

        Random r = new Random();

        // Loop through up to numSteps (max)
        for (int i = 0; i < numSteps; i++)
        {
            if (aliveThreads.size() > 0)
            {
                // Choose random thread from set of alive threads, simulate step and add to seq
                int randThread = (int) aliveThreads.toArray()[r.nextInt(aliveThreads.size())];
                sim.stepForward(randThread);
                seq.add(randThread);

                // If no instructions left remove thread from alive set
                if (machine.getThread(randThread).getInstructionPointer() >= machine.getThread(randThread).getInstructionsList().size())
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


    private static <S, T> void printPairList(ArrayList<Pair<S, T>> list, String title, String left, String right)
    {
        System.out.println("---- " + title + " ----");

        for (Pair p : list)
        {
            System.out.println(String.format(left + " %s: %s (%s)", p.getKey().toString(), p.getValue().toString(), right));
        }
        System.out.println("---- " + title + " ----\n");
    }


    private static <S, T> void printMap(Map<S, T> map, String title, String key)
    {
        System.out.println("---- " + title + " ----");
        for (S t : map.keySet())
        {
            System.out.println(String.format(key + " %s: %s", t.toString(), map.get(t).toString()));
        }
        System.out.println("---- " + title + " ----\n");
    }

    private static int selectThread(ArrayList<Pair<Integer, Float>> threadProbs, float rand)
    {
        ArrayList<Pair<Integer, Float>> probBounds = new ArrayList<>();

        if (threadProbs.size() > 0)
            probBounds = getProbBounds(threadProbs);

//        System.out.println("Rand: " + rand);
        for (Pair p : probBounds)
        {
            if (rand >= (float) p.getValue())
                return (Integer) p.getKey();
        }

        return -1;
    }

    private static ArrayList<Pair<Integer, Float>> getProbBounds(ArrayList<Pair<Integer, Float>> threadProbs)
    {
        ArrayList<Pair<Integer, Float>> bounds = new ArrayList<>();
        float currentBound = 1f;

        for (Pair p : threadProbs)
        {
            currentBound -= (float) p.getValue();
            bounds.add(new Pair<>((Integer) p.getKey(), currentBound));
        }

        bounds.sort(Comparator.comparing(Pair::getValue));
        Collections.reverse(bounds);
        // printPairList(bounds, "Prob bounds", "Thread", "bound");

        return bounds;
    }

    private static ArrayList<Pair<Integer, Float>> calcProbs(HashMap<Integer, Integer> instructCounts)
    {
        int total = 0;
        ArrayList<Pair<Integer, Float>> result = new ArrayList<>();

        for (int v : instructCounts.values())
        {
            if (v > 0)
                total += v;
        }


        for (int k : instructCounts.keySet())
        {
            if (instructCounts.get(k) > 0)
            {
                float prob = (float) instructCounts.get(k) / (Math.max(1, total));
                result.add(new Pair<>(k, prob));
            }

        }

        result.sort(Comparator.comparing(Pair::getKey));
        return result;

    }

    private static int countNumberStoresLeft(Program p, ArrayList<Integer> seq, int threadID)
    {
//        System.out.println("--- NUM STORES LEFT ---");
        GenerationSim sim = new GenerationSim();
        sim.simSequence(p, seq.stream().mapToInt(Integer::intValue).toArray());

        SimulatorThread thread = sim.getMachine().getThread(threadID);
        ArrayList<Instruction> instructions = thread.getInstructionsList();

        int count = 0;
        for (int i = thread.getInstructionPointer(); i < instructions.size(); i++)
        {
//            System.out.println(instructions.get(i));
            if (instructions.get(i).getKeyword() == InstructionKeyword.ST)
                count++;
        }

//        System.out.println("Thread " + threadID + " stores: " + count);

        return count;
    }
}

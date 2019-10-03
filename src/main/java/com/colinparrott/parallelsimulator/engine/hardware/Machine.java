package com.colinparrott.parallelsimulator.engine.hardware;

import java.util.ArrayList;
import java.util.List;

public class Machine
{
    public static final int MAX_THREADS = 4;

    private List<SimulatorThread> threads = new ArrayList<>(MAX_THREADS);
    private Memory memory;

    public Machine()
    {
        memory = new Memory();
    }


    public SimulatorThread createThread(int id) {
        threads.add(id, new SimulatorThread(memory, id));
        return threads.get(id);
    }

    /**
     * Asks a thread to execute its next instruction
     * @param threadId ID of the thread to execute
     */
    public void executeInstruction(int threadId)
    {
        threads.get(threadId).executeInstruction();
    }

    /**
     * Get a thread
     * @param id ID of thread
     * @return The thread
     */
    public SimulatorThread getThread(int id)
    {
//        System.out.println("Threads size: " + threads.size());
//        System.out.println("Thread index: " + id);
        return threads.get(id);
    }

    /**
     * Get the memory of the machine
     * @return Memory
     */
    public Memory getMemory()
    {
        return memory;
    }


    /**
     * Get the number of threads used in the current program
     * @return Number of threads used in the program
     */
    public int numberUsedThreads()
    {
        return threads.size();
    }

}

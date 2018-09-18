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

    public void executeInstruction(int threadId)
    {
        threads.get(threadId).executeInstruction();
    }

    public SimulatorThread getThread(int id)
    {
        return threads.get(id);
    }

    public Memory getMemory()
    {
        return memory;
    }

    public int numberUsedThreads()
    {
        return threads.size();
    }

}

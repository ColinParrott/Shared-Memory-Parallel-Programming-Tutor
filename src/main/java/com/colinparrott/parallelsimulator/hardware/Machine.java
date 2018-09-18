package com.colinparrott.parallelsimulator.hardware;

import com.colinparrott.parallelsimulator.instructions.Instruction;

import java.util.ArrayList;
import java.util.List;

public class Machine
{
    private static final int MAX_THREADS = 4;

    private int numberOfThreads = 0;
    private List<SimulatorThread> threads = new ArrayList<>(MAX_THREADS);
    private Memory memory;

    public Machine()
    {
        memory = new Memory();
    }

    public void createThread(int id) {
        threads.add(new SimulatorThread(memory, id));
        numberOfThreads++;
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
}

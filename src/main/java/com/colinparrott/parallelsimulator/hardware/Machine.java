package com.colinparrott.parallelsimulator.hardware;

import com.colinparrott.parallelsimulator.instructions.Instruction;

import java.util.ArrayList;
import java.util.List;

public class Machine
{
    private static final int MAX_THREADS = 4;

    private List<SimulatorThread> threads = new ArrayList<>(MAX_THREADS);
    private static Memory memory;



    public void executeInstruction(int threadId, Instruction instruction)
    {

    }
}

package com.colinparrott.parallelsimulator.hardware;

import com.colinparrott.parallelsimulator.instructions.Instruction;



public class SimulatorThread
{
    private static final int REGISTERS_PER_THREAD = 10;

    private int threadId;
    private static int threadCount = 0;
    private Instruction currentInstruction;
    private  Register[] registers;
    private static Memory memory;

    public SimulatorThread(Instruction instruction, Memory memory)
    {
        threadCount++;
        this.threadId = threadCount;
        this.currentInstruction = instruction;
        memory = memory;
        initialiseRegisters();
    }

    private void initialiseRegisters()
    {
        registers = new Register[REGISTERS_PER_THREAD];

        for(int i = 0; i < registers.length; i++)
        {
            registers[i] = new Register(i);
        }
    }

    public void loadInstruction(Instruction instruction)
    {
        this.currentInstruction = instruction;
    }

    public void executeInstruction()
    {
        currentInstruction.execute(memory, registers);

        // TODO: better way possibly, sanity reasons for now
        this.currentInstruction = null;
    }

    public Register[] getRegisters()
    {
        return registers;
    }

    public int getThreadId()
    {
        return threadId;
    }

    public Memory getMemory()
    {
        return memory;
    }
}

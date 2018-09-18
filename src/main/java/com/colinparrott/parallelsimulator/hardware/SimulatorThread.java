package com.colinparrott.parallelsimulator.hardware;

import com.colinparrott.parallelsimulator.instructions.Instruction;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;


public class SimulatorThread
{
    private static final int REGISTERS_PER_THREAD = 10;

    private int threadId;
    private Queue<Instruction> instructionQueue;
    private  Register[] registers;
    private static Memory memory;

    public SimulatorThread(Memory m, int id)
    {
        this.threadId = id;
        instructionQueue = new LinkedList<>();
        memory = m;
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

    public void queueInstructions(ArrayList<Instruction> instructions)
    {
        instructionQueue.addAll(instructions);
    }

    public void executeInstruction()
    {
        Instruction currentInstruction = instructionQueue.remove();
        currentInstruction.execute(memory, registers);
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

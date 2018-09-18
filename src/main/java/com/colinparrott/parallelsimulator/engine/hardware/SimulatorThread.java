package com.colinparrott.parallelsimulator.engine.hardware;

import com.colinparrott.parallelsimulator.engine.instructions.Instruction;

import java.util.ArrayList;
import java.util.Queue;


public class SimulatorThread
{
    private static final int REGISTERS_PER_THREAD = 10;

    private int threadId;


    private ArrayList<Instruction> instructionsList;
    private int instructionPointer = 0;
    private  Register[] registers;
    private Memory memory;

    public SimulatorThread(Memory m, int id)
    {
        this.threadId = id;
        instructionsList = new ArrayList<>();
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
        this.instructionsList.addAll(instructions);
    }

    public boolean executeInstruction()
    {
        if(instructionPointer < instructionsList.size())
        {
            instructionsList.get(instructionPointer).execute(memory, registers);
            instructionPointer++;
            return true;
        }

        return false;
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

    public int getInstructionPointer()
    {
        return instructionPointer;
    }

    public ArrayList<Instruction> getInstructionsList()
    {
        return instructionsList;
    }

    public Instruction getNextInstruction()
    {
        if(instructionPointer < instructionsList.size())
        {
            return instructionsList.get(instructionPointer);
        }
        else
        {
            return null;
        }
    }
}

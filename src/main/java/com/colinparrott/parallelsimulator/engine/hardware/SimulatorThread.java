package com.colinparrott.parallelsimulator.engine.hardware;

import com.colinparrott.parallelsimulator.engine.instructions.Await;
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
    private boolean inAtomicSection = false;

    public SimulatorThread(Memory m, int id)
    {
        this.threadId = id;
        instructionsList = new ArrayList<>();
        memory = m;
        initialiseRegisters();
    }

    /**
     * Initialise the registers of the thread
     */
    private void initialiseRegisters()
    {
        registers = new Register[REGISTERS_PER_THREAD];

        for(int i = 0; i < registers.length; i++)
        {
            registers[i] = new Register(i);
        }
    }

    /**
     * Load program (set of instructions) onto thread
     * @param instructions Set of instructions
     */
    public void queueInstructions(ArrayList<Instruction> instructions)
    {
        this.instructionsList.addAll(instructions);
    }

    /**
     * Executes the next instruction on the thread
     * @return True if instructions left (executed), False if no instructions left (failed)
     */
    public boolean executeInstruction()
    {
        // If pointer is not pointing beyond the last instruction execute and increment pointer
        if(instructionPointer < instructionsList.size())
        {
            // If not in atomic section (or await) execute like normal
            if(!inAtomicSection)
            {
                System.out.println("Executing: " + instructionsList.get(instructionPointer).toString());
                instructionsList.get(instructionPointer).execute(memory, registers, this);
                instructionPointer++;
            }


            // If in atomic section execute all instructions
            while(inAtomicSection)
            {
                System.out.println("Atomic Executing: " + instructionsList.get(instructionPointer).toString());
                Instruction i = instructionsList.get(instructionPointer);
                i.execute(memory, registers, this);

                // Increment instruction pointer only if not await instruction
                // Await instruction increments pointer itself if its condition is true (and resets if false) so we
                // don't want to override that here
                if(!(i instanceof Await))
                {
                    instructionPointer++;
                }

            }

            return true;
        }

        return false;
    }

    /**
     * Returns the next instruction to execute
     * @return Instruction if available, null if none left
     */
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

    public void setInstructionPointer(int pos)
    {
        instructionPointer = pos;
    }

    public ArrayList<Instruction> getInstructionsList()
    {
        return instructionsList;
    }


    public boolean isInAtomicSection()
    {
        return inAtomicSection;
    }

    public void setInAtomicSection(boolean inAtomicSection)
    {
        this.inAtomicSection = inAtomicSection;
    }
}

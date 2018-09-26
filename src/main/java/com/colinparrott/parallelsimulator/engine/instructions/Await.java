package com.colinparrott.parallelsimulator.engine.instructions;

import com.colinparrott.parallelsimulator.engine.hardware.Memory;
import com.colinparrott.parallelsimulator.engine.hardware.MemoryLocation;
import com.colinparrott.parallelsimulator.engine.hardware.Register;
import com.colinparrott.parallelsimulator.engine.hardware.SimulatorThread;

public class Await extends Instruction
{
    private MemoryLocation firstVariable;
    private MemoryLocation secondVariable;
    private AwaitComparator comparator;
    private int restorePos = -1;

    // AWAIT <variable_1> <comparator> <variable_2>
    public Await(MemoryLocation firstVariable, MemoryLocation secondVariable, AwaitComparator comparator)
    {
        super(InstructionKeyword.AWAIT);
        this.firstVariable = firstVariable;
        this.secondVariable = secondVariable;
        this.comparator = comparator;
    }

    @Override
    public void execute(Memory memory, Register[] registers, SimulatorThread thread)
    {
        // Instruction to restore thread to if condition is false ("ATOMIC" instruction above the "AWAIT");
        if(restorePos == -1)
            restorePos = thread.getInstructionPointer() - 2;

        int firstValue = memory.getValue(firstVariable);
        int secondValue = memory.getValue(secondVariable);

        switch (comparator)
        {

            case EQ:
                if (firstValue == secondValue)
                    thread.setInstructionPointer(thread.getInstructionPointer() + 1) ;
                else
                    moveThreadBackToStart(thread);
                break;
            case NE:
                if (firstValue != secondValue)
                    thread.setInstructionPointer(thread.getInstructionPointer() + 1) ;
                else
                    moveThreadBackToStart(thread);
                break;
            case GT:
                if (firstValue > secondValue)
                    thread.setInstructionPointer(thread.getInstructionPointer() + 1) ;
                else
                    moveThreadBackToStart(thread);
                break;
            case GE:
                if (firstValue >= secondValue)
                    thread.setInstructionPointer(thread.getInstructionPointer() + 1) ;
                else
                    moveThreadBackToStart(thread);
                break;
            case LT:
                if (firstValue < secondValue)
                    thread.setInstructionPointer(thread.getInstructionPointer() + 1) ;
                else
                    moveThreadBackToStart(thread);
                break;
            case LE:
                if (firstValue <= secondValue)
                    thread.setInstructionPointer(thread.getInstructionPointer() + 1) ;
                else
                    moveThreadBackToStart(thread);
                break;
        }
    }

    private void printInstruction(int pointer, SimulatorThread t)
    {
        System.out.println("Instruction: " + t.getInstructionsList().get(pointer).getClass().getSimpleName() + "\tPointer: " + pointer);
    }

    /**
     * Moves instruction pointer to another instruction and reset atomic state
     * @param t Thread to modify
     */
    private void moveThreadBackToStart(SimulatorThread t)
    {
        t.setInAtomicSection(false);
        t.setInstructionPointer(restorePos);
    }

    @Override
    public String toString()
    {
        return String.format("%s %s %s %s", this.getKeyword(), firstVariable, comparator, secondVariable);
    }

}

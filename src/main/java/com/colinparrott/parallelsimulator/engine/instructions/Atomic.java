package com.colinparrott.parallelsimulator.engine.instructions;

import com.colinparrott.parallelsimulator.engine.hardware.Memory;
import com.colinparrott.parallelsimulator.engine.hardware.Register;
import com.colinparrott.parallelsimulator.engine.hardware.SimulatorThread;

public class Atomic extends Instruction
{
    public Atomic()
    {
        super(InstructionKeyword.ATOMIC);
    }

    @Override
    public void execute(Memory memory, Register[] registers, SimulatorThread thread)
    {
        // Tell thread its executing an atomic instruction
        thread.setInAtomicSection(true);

        // Move pointer to first atomic instruction
        thread.setInstructionPointer(thread.getInstructionPointer() + 1);

        // Execute first instruction to start process (thread will handle executing the rest)
        thread.executeInstruction();
    }

    @Override
    public ParameterType[] getExpectedParams()
    {
        return new ParameterType[0];
    }

    @Override
    public String toString()
    {
        return String.format("%s", this.getKeyword());
    }
}

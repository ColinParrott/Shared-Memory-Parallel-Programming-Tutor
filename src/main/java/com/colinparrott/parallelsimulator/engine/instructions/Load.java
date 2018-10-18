package com.colinparrott.parallelsimulator.engine.instructions;

import com.colinparrott.parallelsimulator.engine.hardware.Memory;
import com.colinparrott.parallelsimulator.engine.hardware.MemoryLocation;
import com.colinparrott.parallelsimulator.engine.hardware.Register;
import com.colinparrott.parallelsimulator.engine.hardware.SimulatorThread;

public class Load extends Instruction
{

    private int registerNumber;
    private MemoryLocation memoryLocation;

    public Load(int registerNumber, MemoryLocation memoryLocation) {
        super(InstructionKeyword.LD);
        this.registerNumber = registerNumber;
        this.memoryLocation = memoryLocation;
    }

    @Override
    public void execute(Memory memory, Register[] registers, SimulatorThread thread) {

        registers[registerNumber].setValue(memory.getValue(this.memoryLocation));
    }

    @Override
    public String toString()
    {
        return String.format("%s $R%s %s", this.getKeyword(), registerNumber, memoryLocation);
    }

    public int getRegisterNumber()
    {
        return registerNumber;
    }

    public MemoryLocation getMemoryLocation()
    {
        return memoryLocation;
    }
}

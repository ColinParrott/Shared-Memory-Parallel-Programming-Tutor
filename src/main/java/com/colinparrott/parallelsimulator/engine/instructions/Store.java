package com.colinparrott.parallelsimulator.engine.instructions;

import com.colinparrott.parallelsimulator.engine.hardware.Memory;
import com.colinparrott.parallelsimulator.engine.hardware.MemoryLocation;
import com.colinparrott.parallelsimulator.engine.hardware.Register;
import com.colinparrott.parallelsimulator.engine.hardware.SimulatorThread;

public class Store extends Instruction
{

    private int registerNumber;
    private MemoryLocation memoryLocation;

    public Store(int registerNumber, MemoryLocation memoryLocation) {
        super(InstructionKeyword.ST);
        this.registerNumber = registerNumber;
        this.memoryLocation = memoryLocation;
    }

    @Override
    public void execute(Memory memory, Register[] registers, SimulatorThread thread) {
        memory.setVariable(memoryLocation, registers[registerNumber].getValue());
    }

    @Override
    public ParameterType[] getExpectedParams()
    {
        return new ParameterType[]{ParameterType.REGISTER, ParameterType.MEMORY_LOCATION};
    }

    @Override
    public String toString()
    {
        return String.format("%s %s $R%s", this.getKeyword(), memoryLocation, registerNumber);
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

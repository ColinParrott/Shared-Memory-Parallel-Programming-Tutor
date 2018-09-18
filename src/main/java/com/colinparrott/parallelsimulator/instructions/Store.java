package com.colinparrott.parallelsimulator.instructions;

import com.colinparrott.parallelsimulator.hardware.Memory;
import com.colinparrott.parallelsimulator.hardware.MemoryLocation;
import com.colinparrott.parallelsimulator.hardware.Register;

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
    public void execute(Memory memory, Register[] registers) {
        memory.setVariable(memoryLocation, registers[registerNumber].getValue());
    }
}

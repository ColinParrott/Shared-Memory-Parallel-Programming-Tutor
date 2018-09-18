package com.colinparrott.parallelsimulator.instructions;

import com.colinparrott.parallelsimulator.hardware.Memory;
import com.colinparrott.parallelsimulator.hardware.MemoryLocation;
import com.colinparrott.parallelsimulator.hardware.Register;

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
    public void execute(Memory memory, Register[] registers) {

        registers[registerNumber].setValue(memory.getValue(this.memoryLocation));
    }
}

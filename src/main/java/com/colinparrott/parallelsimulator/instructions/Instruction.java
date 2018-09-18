package com.colinparrott.parallelsimulator.instructions;

import com.colinparrott.parallelsimulator.hardware.Memory;
import com.colinparrott.parallelsimulator.hardware.Register;

public abstract class Instruction
{
    private InstructionKeyword keyword;

    public Instruction(InstructionKeyword keyword)
    {
        this.keyword = keyword;
    }

    public abstract void execute(Memory memory, Register[] registers);

    public InstructionKeyword getKeyword()
    {
        return keyword;
    }
}

package com.colinparrott.parallelsimulator.engine.instructions;

import com.colinparrott.parallelsimulator.engine.hardware.Memory;
import com.colinparrott.parallelsimulator.engine.hardware.Register;

public abstract class Instruction
{
    private InstructionKeyword keyword;

    Instruction(InstructionKeyword keyword)
    {
        this.keyword = keyword;
    }

    public abstract void execute(Memory memory, Register[] registers);

    InstructionKeyword getKeyword()
    {
        return keyword;
    }
}

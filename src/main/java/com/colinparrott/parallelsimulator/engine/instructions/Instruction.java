package com.colinparrott.parallelsimulator.engine.instructions;

import com.colinparrott.parallelsimulator.engine.hardware.Memory;
import com.colinparrott.parallelsimulator.engine.hardware.Register;
import com.colinparrott.parallelsimulator.engine.hardware.SimulatorThread;

public abstract class Instruction
{
    private InstructionKeyword keyword;

    Instruction(InstructionKeyword keyword)
    {
        this.keyword = keyword;
    }

    public abstract void execute(Memory memory, Register[] registers, SimulatorThread thread);

    public InstructionKeyword getKeyword()
    {
        return keyword;
    }

    public abstract ParameterType[] getExpectedParams();
}

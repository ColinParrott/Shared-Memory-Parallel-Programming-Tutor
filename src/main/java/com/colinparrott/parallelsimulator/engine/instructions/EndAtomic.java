package com.colinparrott.parallelsimulator.engine.instructions;

import com.colinparrott.parallelsimulator.engine.hardware.Memory;
import com.colinparrott.parallelsimulator.engine.hardware.Register;
import com.colinparrott.parallelsimulator.engine.hardware.SimulatorThread;

public class EndAtomic extends Instruction
{
    public EndAtomic()
    {
        super(InstructionKeyword.ENDATOMIC);
    }

    @Override
    public void execute(Memory memory, Register[] registers, SimulatorThread thread)
    {
        thread.setInAtomicSection(false);
    }

    @Override
    public String toString()
    {
        return String.format("%s", this.getKeyword());
    }
}

package com.colinparrott.parallelsimulator.engine.instructions;

import com.colinparrott.parallelsimulator.engine.hardware.Memory;
import com.colinparrott.parallelsimulator.engine.hardware.Register;
import com.colinparrott.parallelsimulator.engine.hardware.SimulatorThread;

public class Label extends Instruction
{
    private String label;

    public Label(String name)
    {
        super(InstructionKeyword.LABEL);
        this.label = name;
    }

    @Override
    public void execute(Memory memory, Register[] registers, SimulatorThread thread)
    {
        // label so no need to modify anything
    }

    public String getLabel()
    {
        return label;
    }

    @Override
    public String toString()
    {
//        return String.format("%s %s", this.getKeyword(), this.label);
        return label + ":";
    }
}

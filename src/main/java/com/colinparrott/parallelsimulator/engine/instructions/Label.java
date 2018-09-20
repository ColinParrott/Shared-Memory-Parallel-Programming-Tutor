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

    }

    public String getLabel()
    {
        return label;
    }
}

package com.colinparrott.parallelsimulator.engine.instructions;

import com.colinparrott.parallelsimulator.engine.hardware.Memory;
import com.colinparrott.parallelsimulator.engine.hardware.Register;
import com.colinparrott.parallelsimulator.engine.hardware.SimulatorThread;

public class LoadImmediate extends Instruction
{

    private int registerNumber;
    private int constant;

    public LoadImmediate(int registerNumber, int constant) {
        super(InstructionKeyword.LDI);
        this.registerNumber = registerNumber;
        this.constant = constant;
    }

    @Override
    public void execute(Memory memory, Register[] registers, SimulatorThread thread) {

        registers[registerNumber].setValue(this.constant);
    }

    @Override
    public ParameterType[] getExpectedParams()
    {
        return new ParameterType[]{ParameterType.REGISTER, ParameterType.INT_LITERAL};
    }

    @Override
    public String toString()
    {
        return String.format("%s $R%d %d", this.getKeyword(), registerNumber, constant);
    }
}

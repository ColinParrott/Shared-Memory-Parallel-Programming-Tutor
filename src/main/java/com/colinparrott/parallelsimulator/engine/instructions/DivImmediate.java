package com.colinparrott.parallelsimulator.engine.instructions;

import com.colinparrott.parallelsimulator.engine.hardware.Memory;
import com.colinparrott.parallelsimulator.engine.hardware.Register;
import com.colinparrott.parallelsimulator.engine.hardware.SimulatorThread;

public class DivImmediate extends Instruction
{

    private int destRegisterNumber;
    private int sourceRegisterNumber;
    private int constant;

    public DivImmediate(int destRegisterNumber, int sourceRegisterNumber, int constant) {
        super(InstructionKeyword.DIVI);
        this.destRegisterNumber = destRegisterNumber;
        this.sourceRegisterNumber = sourceRegisterNumber;
        this.constant = constant;
    }

    @Override
    public void execute(Memory memory, Register[] registers, SimulatorThread thread) {
        int result = registers[sourceRegisterNumber].getValue() / constant;
        registers[destRegisterNumber].setValue(result);
    }

    @Override
    public ParameterType[] getExpectedParams()
    {
        return new ParameterType[]{ParameterType.REGISTER, ParameterType.REGISTER, ParameterType.CONSTANT};
    }


    @Override
    public String toString()
    {
        return String.format("%s $R%d $R%d %d", this.getKeyword(), destRegisterNumber, sourceRegisterNumber, constant);
    }
}

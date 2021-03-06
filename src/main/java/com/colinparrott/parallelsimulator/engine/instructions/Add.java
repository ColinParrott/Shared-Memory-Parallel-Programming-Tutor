package com.colinparrott.parallelsimulator.engine.instructions;

import com.colinparrott.parallelsimulator.engine.hardware.Memory;
import com.colinparrott.parallelsimulator.engine.hardware.Register;
import com.colinparrott.parallelsimulator.engine.hardware.SimulatorThread;

public class Add extends Instruction
{

    private int destRegisterNumber;
    private int firstRegisterNumber;
    private int secondRegisterNumber;

    public Add(int destRegisterNumber, int firstRegisterNumber, int secondRegisterNumber) {
        super(InstructionKeyword.ADD);
        this.destRegisterNumber = destRegisterNumber;
        this.firstRegisterNumber = firstRegisterNumber;
        this.secondRegisterNumber = secondRegisterNumber;
    }

    @Override
    public void execute(Memory memory, Register[] registers, SimulatorThread thread) {
        int result = registers[firstRegisterNumber].getValue() + registers[secondRegisterNumber].getValue();
        registers[destRegisterNumber].setValue(result);
    }

    @Override
    public ParameterType[] getExpectedParams()
    {
        return new ParameterType[]{ParameterType.REGISTER, ParameterType.REGISTER, ParameterType.REGISTER};
    }

    @Override
    public String toString()
    {
        return String.format("%s $R%d $R%d $R%d", this.getKeyword(), destRegisterNumber, firstRegisterNumber, secondRegisterNumber);
    }
}

package com.colinparrott.parallelsimulator.engine.instructions;

import com.colinparrott.parallelsimulator.engine.hardware.Memory;
import com.colinparrott.parallelsimulator.engine.hardware.Register;
import com.colinparrott.parallelsimulator.engine.hardware.SimulatorThread;

/**
 * Sets a register to 1 if two registers are equal, 0 otherwise
 */

public class SetIfEqual extends Instruction
{
    private int setRegister;
    private int firstRegister;
    private int secondRegister;


    public SetIfEqual(int setRegister, int firstRegister, int secondRegister)
    {
        super(InstructionKeyword.SEQ);
        this.setRegister = setRegister;
        this.firstRegister = firstRegister;
        this.secondRegister = secondRegister;
    }

    @Override
    public void execute(Memory memory, Register[] registers, SimulatorThread thread)
    {
        if (registers[firstRegister].getValue() == registers[secondRegister].getValue())
        {
            registers[setRegister].setValue(1);
        }
        else
        {
            registers[setRegister].setValue(0);
        }
    }


    @Override
    public ParameterType[] getExpectedParams()
    {
        return new ParameterType[0];
    }

    @Override
    public String toString()
    {
        return String.format("%s $R%d $R%d $R%d", this.getKeyword(), setRegister, firstRegister, secondRegister);
    }
}

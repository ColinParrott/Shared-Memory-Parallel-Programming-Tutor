package com.colinparrott.parallelsimulator.instructions;

import com.colinparrott.parallelsimulator.hardware.Memory;
import com.colinparrott.parallelsimulator.hardware.Register;

public class AddImmediate extends Instruction
{

    private int destRegisterNumber;
    private int sourceRegisterNumber;
    private int constant;

    public AddImmediate(int destRegisterNumber, int sourceRegisterNumber, int constant) {
        super(InstructionKeyword.ADDI);
        this.destRegisterNumber = destRegisterNumber;
        this.sourceRegisterNumber = sourceRegisterNumber;
        this.constant = constant;
    }

    @Override
    public void execute(Memory memory, Register[] registers) {
        int sum = registers[sourceRegisterNumber].getValue() + constant;
        registers[destRegisterNumber].setValue(sum);
    }
}

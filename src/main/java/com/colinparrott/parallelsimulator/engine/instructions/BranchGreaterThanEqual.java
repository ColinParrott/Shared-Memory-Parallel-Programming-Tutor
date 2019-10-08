package com.colinparrott.parallelsimulator.engine.instructions;

import com.colinparrott.parallelsimulator.engine.hardware.Memory;
import com.colinparrott.parallelsimulator.engine.hardware.Register;
import com.colinparrott.parallelsimulator.engine.hardware.SimulatorThread;

public class BranchGreaterThanEqual extends Instruction
{
    private int firstRegisterNumber;
    private int secondRegisterNumber;
    private String label;

    public BranchGreaterThanEqual(int firstRegisterNumber, int secondRegisterNumber, String label)
    {
        super(InstructionKeyword.BGE);
        this.firstRegisterNumber = firstRegisterNumber;
        this.secondRegisterNumber = secondRegisterNumber;
        this.label = label;
    }

    @Override
    public void execute(Memory memory, Register[] registers, SimulatorThread thread)
    {
        // If first register greater or equal than second need to move instruction pointer
        if (registers[firstRegisterNumber].getValue() >= registers[secondRegisterNumber].getValue())
        {
            // Find position of label (0-based) in threads instructions and set its pointer to that label
            int labelPos = 0;
            for (Instruction instruction : thread.getInstructionsList())
            {
                if (instruction.getKeyword() == InstructionKeyword.LABEL)
                {
                    // Check labels equal (need to convert Instruction to Label first to access label)
                    Label l = (Label) instruction;
                    if (l.getLabel().equals(label))
                    {
                        break;
                    }
                }
                labelPos++;
            }
            // Set thread pointer to label
            thread.setInstructionPointer(labelPos);
        }

    }

    @Override
    public ParameterType[] getExpectedParams()
    {
        return new ParameterType[]{ParameterType.REGISTER, ParameterType.REGISTER, ParameterType.LABEL_STRING};
    }

    public String toString()
    {
        return String.format("%s $R%d $R%d %s", this.getKeyword(), firstRegisterNumber, secondRegisterNumber, label);
    }

    public String getLabel()
    {
        return label;
    }
}

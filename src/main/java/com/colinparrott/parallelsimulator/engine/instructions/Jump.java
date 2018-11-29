package com.colinparrott.parallelsimulator.engine.instructions;

import com.colinparrott.parallelsimulator.engine.hardware.Memory;
import com.colinparrott.parallelsimulator.engine.hardware.Register;
import com.colinparrott.parallelsimulator.engine.hardware.SimulatorThread;

public class Jump extends Instruction
{
    private String label;

    public Jump(String label)
    {
        super(InstructionKeyword.JUMP);
        this.label = label;
    }

    @Override
    public void execute(Memory memory, Register[] registers, SimulatorThread thread)
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

    public String getLabel()
    {
        return label;
    }

    @Override
    public String toString()
    {
        return "JUMP " + label;
    }
}

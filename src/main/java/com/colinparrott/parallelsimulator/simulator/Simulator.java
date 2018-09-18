package com.colinparrott.parallelsimulator.simulator;

import com.colinparrott.parallelsimulator.hardware.Machine;
import com.colinparrott.parallelsimulator.hardware.MemoryLocation;
import com.colinparrott.parallelsimulator.instructions.*;

import java.util.ArrayList;

public class Simulator
{
    private static Machine machine;
    private static MachineStateHolder stateHistory;

    public static void main(String[] args)
    {
        machine = new Machine();
        stateHistory = new MachineStateHolder();

        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new Load(0, MemoryLocation.x));
        instructions.add(new AddImmediate(0, 1, 1));
        instructions.add(new Store(0, MemoryLocation.x));

        machine.createThread(0);
        machine.getThread(0).queueInstructions(instructions);
        machine.executeInstruction(0);
        machine.executeInstruction(0);
        machine.executeInstruction(0);
        System.out.println(machine.getMemory());
    }


    public void stepForward()
    {
       // fgg
       stateHistory.addState(machine);
    }

    public void stepBackward()
    {
        stateHistory.popState();
        machine = stateHistory.getCurrentState();
    }
}

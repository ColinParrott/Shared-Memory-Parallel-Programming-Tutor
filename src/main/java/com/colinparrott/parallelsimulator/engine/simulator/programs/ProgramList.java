package com.colinparrott.parallelsimulator.engine.simulator.programs;

import com.colinparrott.parallelsimulator.engine.hardware.MemoryLocation;
import com.colinparrott.parallelsimulator.engine.instructions.*;

import java.util.ArrayList;

/**
 * Class that holds list of preset programs
 */
public class ProgramList
{
    public Program loadXPlusPlusTwoThreads()
    {
        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new Load(0, MemoryLocation.x));
        instructions.add(new AddImmediate(0, 0, 1));
        instructions.add(new Store(0, MemoryLocation.x));

        Program p = new Program();
        p.setInstructionsForThread(0, instructions);
        p.setInstructionsForThread(1, instructions);

        return p;
    }

    public Program loadXPlusPlusAtomicTwoThreads()
    {
        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new Atomic());
        instructions.add(new Load(0, MemoryLocation.x));
        instructions.add(new AddImmediate(0, 0, 1));
        instructions.add(new Store(0, MemoryLocation.x));
        instructions.add(new EndAtomic());

        Program p = new Program();
        p.setInstructionsForThread(0, instructions);
        p.setInstructionsForThread(1, instructions);

        return p;
    }

    public Program loadAwaitFlag()
    {
        ArrayList<Instruction> instructsThreadOne = new ArrayList<>();
        instructsThreadOne.add(new LoadImmediate(0, 25));
        instructsThreadOne.add(new Store(0, MemoryLocation.a));
        instructsThreadOne.add(new LoadImmediate(0, 1));
        instructsThreadOne.add(new Store(0, MemoryLocation.x));


        ArrayList<Instruction> instructsThreadTwo = new ArrayList<>();
        instructsThreadTwo.add(new Atomic());
        instructsThreadTwo.add(new Await(MemoryLocation.x, AwaitComparator.EQ, 1)); // x == 1
        instructsThreadTwo.add(new Load(0, MemoryLocation.a));
        instructsThreadTwo.add(new Store(0, MemoryLocation.x));
        instructsThreadTwo.add(new EndAtomic());

        Program p = new Program();
        p.setInstructionsForThread(0, instructsThreadOne);
        p.setInstructionsForThread(1, instructsThreadTwo);
        return p;
    }
}
package com.colinparrott.parallelsimulator.engine.simulator.programs;

import com.colinparrott.parallelsimulator.engine.hardware.Memory;
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

    public Program loadBEqualsAPlusA()
    {
        ArrayList<Instruction> instructsThreadOne = new ArrayList<>();
        ArrayList<Instruction> instructsThreadTwo = new ArrayList<>();
        ArrayList<Instruction> instructsThreadThree = new ArrayList<>();

        instructsThreadOne.add(new LoadImmediate(0, 1));
        instructsThreadOne.add(new Store(0, MemoryLocation.a));

        instructsThreadTwo.add(new LoadImmediate(0, 2));
        instructsThreadTwo.add(new Store(0, MemoryLocation.a));

        instructsThreadThree.add(new Load(1, MemoryLocation.a));
        instructsThreadThree.add(new Load(2, MemoryLocation.a));
        instructsThreadThree.add(new Add(3, 1, 2));
        instructsThreadThree.add(new Store(3, MemoryLocation.b));

        Program p = new Program();
        p.setInstructionsForThread(0, instructsThreadOne);
        p.setInstructionsForThread(1, instructsThreadTwo);
        p.setInstructionsForThread(2, instructsThreadThree);
        return p;

    }

    public Program loadXEqualsFourAwait()
    {
        ArrayList<Instruction> instructsThreadOne = new ArrayList<>();
        ArrayList<Instruction> instructsThreadTwo = new ArrayList<>();
        ArrayList<Instruction> instructsThreadThree = new ArrayList<>();

        Program p = new Program();

        instructsThreadOne.add(new Atomic());
        instructsThreadOne.add(new Await(MemoryLocation.x, AwaitComparator.GT, 2));
        instructsThreadOne.add(new LoadImmediate(0, 1));
        instructsThreadOne.add(new Store(0, MemoryLocation.x));
        instructsThreadOne.add(new EndAtomic());

        instructsThreadTwo.add(new LoadImmediate(0, 4));
        instructsThreadTwo.add(new Store(0, MemoryLocation.x));

        instructsThreadOne.add(new Atomic());
        instructsThreadOne.add(new Await(MemoryLocation.x, AwaitComparator.GT, 3));
        instructsThreadOne.add(new LoadImmediate(0, 10));
        instructsThreadOne.add(new Store(0, MemoryLocation.x));
        instructsThreadOne.add(new EndAtomic());

        p.setInstructionsForThread(0, instructsThreadOne);
        p.setInstructionsForThread(1, instructsThreadTwo);
        p.setInstructionsForThread(2, instructsThreadThree);
        return p;
    }

    public Program loadALessThanBFourThreads()
    {
        ArrayList<Instruction> instructions = new ArrayList<>();

        instructions.add(new Load(0, MemoryLocation.a));
        instructions.add(new Load(1, MemoryLocation.b));
        instructions.add(new BranchLessThan(0, 1, "incr_a"));
        instructions.add(new Load(1, MemoryLocation.b));
        instructions.add(new AddImmediate(1, 1, 1));
        instructions.add(new Store(1, MemoryLocation.b));
        instructions.add(new Jump("end"));

        instructions.add(new Label("incr_a"));
        instructions.add(new Load(0, MemoryLocation.a));
        instructions.add(new AddImmediate(0, 0, 1));
        instructions.add(new Store(0, MemoryLocation.a));
        instructions.add(new Label("end"));

        Memory m = new Memory();
        m.setVariable(MemoryLocation.b, 4);
        Program p = new Program(m);

        for (int i = 0; i < 4; i++)
        {
            p.setInstructionsForThread(i, instructions);
        }

        return p;
    }

    public Program loadALessThanBFourThreadsAtomic()
    {
        ArrayList<Instruction> instructions = new ArrayList<>();

        instructions.add(new Load(0, MemoryLocation.a));
        instructions.add(new Load(1, MemoryLocation.b));
        instructions.add(new BranchLessThan(0, 1, "incr_a"));

        instructions.add(new Atomic());
        instructions.add(new Load(1, MemoryLocation.b));
        instructions.add(new AddImmediate(1, 1, 1));
        instructions.add(new Store(1, MemoryLocation.b));
        instructions.add(new EndAtomic());

        instructions.add(new Jump("end"));
        instructions.add(new Label("incr_a"));

        instructions.add(new Atomic());
        instructions.add(new Load(0, MemoryLocation.a));
        instructions.add(new AddImmediate(0, 0, 1));
        instructions.add(new Store(0, MemoryLocation.a));
        instructions.add(new EndAtomic());

        instructions.add(new Label("end"));

        Memory m = new Memory();
        m.setVariable(MemoryLocation.b, 4);
        Program p = new Program(m);

        for (int i = 0; i < 4; i++)
        {
            p.setInstructionsForThread(i, instructions);
        }

        return p;
    }
}

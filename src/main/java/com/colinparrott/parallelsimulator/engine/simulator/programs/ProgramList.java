package com.colinparrott.parallelsimulator.engine.simulator.programs;

import com.colinparrott.parallelsimulator.engine.hardware.Memory;
import com.colinparrott.parallelsimulator.engine.hardware.MemoryLocation;
import com.colinparrott.parallelsimulator.engine.instructions.*;

import java.util.ArrayList;
import java.util.Random;

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

        Program p = new Program("x++");
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

        Program p = new Program("<x++>");
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

        Program p = new Program("Await flag");
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

        Program p = new Program("a=1 // a=2 // b=a+a");
        p.setInstructionsForThread(0, instructsThreadOne);
        p.setInstructionsForThread(1, instructsThreadTwo);
        p.setInstructionsForThread(2, instructsThreadThree);
        return p;

    }

    public Program loadBEqualsAPlusAAtomic()
    {
        ArrayList<Instruction> instructsThreadOne = new ArrayList<>();
        ArrayList<Instruction> instructsThreadTwo = new ArrayList<>();
        ArrayList<Instruction> instructsThreadThree = new ArrayList<>();

        instructsThreadOne.add(new LoadImmediate(0, 1));
        instructsThreadOne.add(new Store(0, MemoryLocation.a));

        instructsThreadTwo.add(new LoadImmediate(0, 2));
        instructsThreadTwo.add(new Store(0, MemoryLocation.a));

        instructsThreadThree.add(new Atomic());
        instructsThreadThree.add(new Load(1, MemoryLocation.a));
        instructsThreadThree.add(new Load(2, MemoryLocation.a));
        instructsThreadThree.add(new Add(3, 1, 2));
        instructsThreadThree.add(new Store(3, MemoryLocation.b));
        instructsThreadThree.add(new EndAtomic());

        Program p = new Program("a=1 // a=2 // <b=a+a>");
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

    public Program loadWhileLoop()
    {
        ArrayList<Instruction> instructionsThreadOne = new ArrayList<>();
        instructionsThreadOne.add(new LoadImmediate(1, 4));
        instructionsThreadOne.add(new Label("loop"));
        instructionsThreadOne.add(new Load(0, MemoryLocation.i));
        instructionsThreadOne.add(new BranchGreaterThan(0, 1, "exit"));
        instructionsThreadOne.add(new AddImmediate(0, 0, 1));
        instructionsThreadOne.add(new Store(0, MemoryLocation.i));
        instructionsThreadOne.add(new Jump("loop"));
        instructionsThreadOne.add(new Label("exit"));

        ArrayList<Instruction> instructionsThreadTwo = new ArrayList<>();
        instructionsThreadTwo.add(new LoadImmediate(1, 4));
        instructionsThreadTwo.add(new Label("loop"));
        instructionsThreadTwo.add(new Load(0, MemoryLocation.i));
        instructionsThreadTwo.add(new BranchGreaterThan(0, 1, "exit"));
        instructionsThreadTwo.add(new AddImmediate(0, 0, -1));
        instructionsThreadTwo.add(new Store(0, MemoryLocation.i));
        instructionsThreadTwo.add(new Jump("loop"));
        instructionsThreadTwo.add(new Label("exit"));

        Memory m = new Memory();
        m.setVariable(MemoryLocation.i, 4);
        Program p = new Program(m, "while(i <= 4){i++ // i--};");

        p.setInstructionsForThread(0, instructionsThreadOne);
        p.setInstructionsForThread(1, instructionsThreadTwo);

        return p;

    }

    public Program loadThreadLoadingValues()
    {
        ArrayList<Instruction> instructions = new ArrayList<>();
        ArrayList<Instruction> instructions2 = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < 5; i++)
        {
            int r1 = r.nextInt(10);
            int r2 = r.nextInt(10);
            instructions.add(new LoadImmediate(r1, r.nextInt(50)));
            instructions.add(new Store(r1, MemoryLocation.values()[r.nextInt(MemoryLocation.values().length)]));
            instructions2.add(new LoadImmediate(r2, r.nextInt(50)));
            instructions2.add(new Store(r2, MemoryLocation.values()[r.nextInt(MemoryLocation.values().length)]));
        }

        Memory m = new Memory();
        Program p = new Program(m, "Loading values");

        p.setInstructionsForThread(0, instructions);
        p.setInstructionsForThread(1, instructions2);
        return p;
    }
}

import com.colinparrott.parallelsimulator.engine.hardware.Machine;
import com.colinparrott.parallelsimulator.engine.hardware.MemoryLocation;
import com.colinparrott.parallelsimulator.engine.hardware.SimulatorThread;
import com.colinparrott.parallelsimulator.engine.instructions.*;
import com.colinparrott.parallelsimulator.engine.simulator.MachineStateHolder;
import com.colinparrott.parallelsimulator.engine.simulator.Simulator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class BacktrackingTests
{
    private Simulator sim;
    private Machine machine;
    private MachineStateHolder stateHistory;

    @Before
    public void initialise()
    {
        sim = new Simulator(){};
        machine = sim.getMachine();
        stateHistory = sim.getStateHistory();

    }

    @Test
    public void singleLoadImmediateAndBacktrack()
    {
        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new LoadImmediate(0,5));
        instructions.add(new LoadImmediate(0 ,10));
        SimulatorThread t = machine.createThread(0);
        t.queueInstructions(instructions);
        sim.stepForward(0);
        sim.stepForward(0);
        sim.stepBackward();

        Assert.assertEquals(5, sim.getMachine().getThread(0).getRegisters()[0].getValue());
    }

    @Test
    public void twoAtomicLoadRollback()
    {
        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new Atomic());
        instructions.add(new LoadImmediate(0, 1));
        instructions.add(new LoadImmediate(1, 3));
        instructions.add(new EndAtomic());

        SimulatorThread t = machine.createThread(0);
        t.queueInstructions(instructions);

        // Does job implemented simulator would do
        stateHistory.addState(machine);

        sim.stepForward(0);

        Assert.assertEquals(1, sim.getMachine().getThread(0).getRegisters()[0].getValue());
        Assert.assertEquals(3, sim.getMachine().getThread(0).getRegisters()[1].getValue());

        sim.stepBackward();

        Assert.assertEquals(0, sim.getMachine().getThread(0).getRegisters()[0].getValue());
        Assert.assertEquals(0, sim.getMachine().getThread(0).getRegisters()[1].getValue());
    }

    @Test
    public void awaitTrueSingleLoadRollback()
    {
        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new LoadImmediate(9, 1));
        instructions.add(new Store(9, MemoryLocation.x));
        instructions.add(new LoadImmediate(9, 1));
        instructions.add(new Store(9, MemoryLocation.y));

        instructions.add(new Atomic());
        instructions.add(new Await(MemoryLocation.x, AwaitComparator.EQ, MemoryLocation.y));
        instructions.add(new LoadImmediate(0, 1));
        instructions.add(new EndAtomic());

        SimulatorThread t = machine.createThread(0);
        t.queueInstructions(instructions);

        // Does job implemented simulator would do
        stateHistory.addState(machine);

        sim.stepForward(0); // LDI $R9 1
        sim.stepForward(0); // ST $R9 x
        sim.stepForward(0); // LDI $R9 1
        sim.stepForward(0); // ST $R9 y
        sim.stepForward(0); // ATOMIC AWAIT

        Assert.assertEquals(1, sim.getMachine().getThread(0).getRegisters()[0].getValue());
        sim.stepBackward();
        Assert.assertEquals(0, sim.getMachine().getThread(0).getRegisters()[1].getValue());
    }

    @Test
    public void awaitFalseSingleLoadRollback()
    {
        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new LoadImmediate(9, 0));
        instructions.add(new Store(9, MemoryLocation.x));
        instructions.add(new LoadImmediate(9, 1));
        instructions.add(new Store(9, MemoryLocation.y));

        instructions.add(new Atomic());
        instructions.add(new Await(MemoryLocation.x, AwaitComparator.EQ, MemoryLocation.y));
        instructions.add(new LoadImmediate(0, 1));
        instructions.add(new EndAtomic());

        SimulatorThread t = machine.createThread(0);
        t.queueInstructions(instructions);

        // Does job implemented simulator would do
        stateHistory.addState(machine);

        sim.stepForward(0); // LDI $R9 0
        sim.stepForward(0); // ST $R9 x
        sim.stepForward(0); // LDI $R9 1
        sim.stepForward(0); // ST $R9 y
        sim.stepForward(0); // ATOMIC AWAIT

        Assert.assertEquals(0, sim.getMachine().getThread(0).getRegisters()[0].getValue());
        sim.stepBackward();
        Assert.assertEquals(0, sim.getMachine().getThread(0).getRegisters()[1].getValue());
    }

    @Test
    public void twoAwaitsBothTrueThenSingleRollback()
    {
        ArrayList<Instruction> instructions = new ArrayList<>();
        // Set x & y to 1
        instructions.add(new LoadImmediate(9, 1));
        instructions.add(new Store(9, MemoryLocation.x));
        instructions.add(new LoadImmediate(9, 1));
        instructions.add(new Store(9, MemoryLocation.y));

        // Set a & b to 1
        instructions.add(new LoadImmediate(9, 5));
        instructions.add(new Store(9, MemoryLocation.a));
        instructions.add(new LoadImmediate(9, 5));
        instructions.add(new Store(9, MemoryLocation.b));

        // Load 1 into register 0 and 2 into register 1 if x == y
        instructions.add(new Atomic());
        instructions.add(new Await(MemoryLocation.x, AwaitComparator.EQ, MemoryLocation.y));
        instructions.add(new LoadImmediate(0, 1));
        instructions.add(new LoadImmediate(1, 2));
        instructions.add(new EndAtomic());

        // Load 10 into register 0 and 20 into register 1 if a == b
        instructions.add(new Atomic());
        instructions.add(new Await(MemoryLocation.a, AwaitComparator.EQ, MemoryLocation.b));
        instructions.add(new LoadImmediate(0, 10));
        instructions.add(new LoadImmediate(1, 20));
        instructions.add(new EndAtomic());

        SimulatorThread t = machine.createThread(0);
        t.queueInstructions(instructions);

        // Does job implemented simulator would do
        stateHistory.addState(machine);

        sim.stepForward(0); // LDI $R9 0
        sim.stepForward(0); // ST $R9 x
        sim.stepForward(0); // LDI $R9 1
        sim.stepForward(0); // ST $R9 y

        sim.stepForward(0); // LDI $R9 5
        sim.stepForward(0); // ST $R9 a
        sim.stepForward(0); // LDI $R9 5
        sim.stepForward(0); // ST $R9 b

        sim.stepForward(0); // 1st ATOMIC AWAIT (x & y)
        sim.stepForward(0); // 2nd ATOMIC AWAIT (a & b)

        sim.stepBackward();
        Assert.assertEquals(1, sim.getMachine().getThread(0).getRegisters()[0].getValue());
        Assert.assertEquals(2, sim.getMachine().getThread(0).getRegisters()[1].getValue());

    }





}


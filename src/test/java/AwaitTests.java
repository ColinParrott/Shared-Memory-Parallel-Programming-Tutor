import com.colinparrott.parallelsimulator.engine.hardware.Machine;
import com.colinparrott.parallelsimulator.engine.hardware.MemoryLocation;
import com.colinparrott.parallelsimulator.engine.hardware.SimulatorThread;
import com.colinparrott.parallelsimulator.engine.instructions.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class AwaitTests
{

    private Machine machine;

    @Before
    public void initialise()
    {
        machine = new Machine();
    }

    @Test
    public void awaitConditionEqualTrueFirstTry()
    {
        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new LoadImmediate(0, 7));
        instructions.add(new Store(0, MemoryLocation.x));
        instructions.add(new LoadImmediate(0, 7));
        instructions.add(new Store(0, MemoryLocation.y));
        instructions.add(new Atomic());
        instructions.add(new Await(MemoryLocation.x, AwaitComparator.EQ, MemoryLocation.y));
        instructions.add(new LoadImmediate(2, 100));
        instructions.add(new EndAtomic());
        instructions.add(new LoadImmediate(4, 29));

        SimulatorThread t = machine.createThread(0);
        t.queueInstructions(instructions);
        t.executeInstruction(); // LI $R0 7
        t.executeInstruction(); // ST $R0 x
        t.executeInstruction(); // LI $R0 7
        t.executeInstruction(); // ST $R0 y
        t.executeInstruction(); // ATOMIC (and therefore await)

        Assert.assertNotEquals(29, t.getRegisters()[4].getValue());
        Assert.assertEquals(100, t.getRegisters()[2].getValue());

    }

    @Test
    public void awaitConditionConstantEqualTrueFirstTry()
    {
        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new LoadImmediate(0, 7));
        instructions.add(new Store(0, MemoryLocation.x));
        instructions.add(new Atomic());
        instructions.add(new Await(MemoryLocation.x, AwaitComparator.EQ, 7));
        instructions.add(new LoadImmediate(2, 100));
        instructions.add(new EndAtomic());
        instructions.add(new LoadImmediate(4, 29));

        SimulatorThread t = machine.createThread(0);
        t.queueInstructions(instructions);
        t.executeInstruction(); // LI $R0 7
        t.executeInstruction(); // ST $R0 x
        t.executeInstruction(); // ATOMIC (and therefore await)

        Assert.assertNotEquals(29, t.getRegisters()[4].getValue());
        Assert.assertEquals(100, t.getRegisters()[2].getValue());

    }

    @Test
    public void awaitConditionEqualFalseFirstTry()
    {
        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new LoadImmediate(0, 7));
        instructions.add(new Store(0, MemoryLocation.x));
        instructions.add(new LoadImmediate(0, 10));
        instructions.add(new Store(0, MemoryLocation.y));
        instructions.add(new Atomic());
        instructions.add(new Await(MemoryLocation.x, AwaitComparator.EQ, MemoryLocation.y));
        instructions.add(new LoadImmediate(2, 100));
        instructions.add(new EndAtomic());

        SimulatorThread t = machine.createThread(0);
        t.queueInstructions(instructions);
        t.executeInstruction(); // LI $R0 7
        t.executeInstruction(); // ST $R0 x
        t.executeInstruction(); // LI $R0 10
        t.executeInstruction(); // ST $R0 y
        t.executeInstruction(); // ATOMIC (and therefore await)

        Assert.assertNotEquals(100, t.getRegisters()[2].getValue());
        Assert.assertTrue(t.getInstructionsList().get(t.getInstructionPointer()) instanceof Atomic);

    }

    @Test
    public void awaitConditionNotEqualTrueFirstTry()
    {
        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new LoadImmediate(0, 7));
        instructions.add(new Store(0, MemoryLocation.x));
        instructions.add(new LoadImmediate(0, 10));
        instructions.add(new Store(0, MemoryLocation.y));
        instructions.add(new Atomic());
        instructions.add(new Await(MemoryLocation.x, AwaitComparator.NE, MemoryLocation.y));
        instructions.add(new LoadImmediate(2, 100));
        instructions.add(new EndAtomic());

        SimulatorThread t = machine.createThread(0);
        t.queueInstructions(instructions);
        t.executeInstruction(); // LI $R0 7
        t.executeInstruction(); // ST $R0 x
        t.executeInstruction(); // LI $R0 10
        t.executeInstruction(); // ST $R0 y
        t.executeInstruction(); // ATOMIC (and therefore await)

        Assert.assertEquals(100, t.getRegisters()[2].getValue());

    }

    @Test
    public void awaitConditionNotEqualFalseFirstTry()
    {
        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new LoadImmediate(0, 7));
        instructions.add(new Store(0, MemoryLocation.x));
        instructions.add(new LoadImmediate(0, 7));
        instructions.add(new Store(0, MemoryLocation.y));
        instructions.add(new Atomic());
        instructions.add(new Await(MemoryLocation.x, AwaitComparator.NE, MemoryLocation.y));
        instructions.add(new LoadImmediate(2, 100));
        instructions.add(new EndAtomic());

        SimulatorThread t = machine.createThread(0);
        t.queueInstructions(instructions);
        t.executeInstruction(); // LI $R0 7
        t.executeInstruction(); // ST $R0 x
        t.executeInstruction(); // LI $R0 7
        t.executeInstruction(); // ST $R0 y
        t.executeInstruction(); // ATOMIC (and therefore await)

        Assert.assertNotEquals(100, t.getRegisters()[2].getValue());
        Assert.assertTrue(t.getInstructionsList().get(t.getInstructionPointer()) instanceof Atomic);

    }

    @Test
    public void awaitConditionGreaterThanTrueFirstTry()
    {
        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new LoadImmediate(0, 7));
        instructions.add(new Store(0, MemoryLocation.x));
        instructions.add(new LoadImmediate(0, 6));
        instructions.add(new Store(0, MemoryLocation.y));
        instructions.add(new Atomic());
        instructions.add(new Await(MemoryLocation.x, AwaitComparator.GT, MemoryLocation.y));
        instructions.add(new LoadImmediate(2, 100));
        instructions.add(new EndAtomic());

        SimulatorThread t = machine.createThread(0);
        t.queueInstructions(instructions);
        t.executeInstruction(); // LI $R0 7
        t.executeInstruction(); // ST $R0 x
        t.executeInstruction(); // LI $R0 6
        t.executeInstruction(); // ST $R0 y
        t.executeInstruction(); // ATOMIC (and therefore await)

        Assert.assertEquals(100, t.getRegisters()[2].getValue());

    }

    @Test
    public void awaitConditionGreaterThanFalseFirstTry()
    {
        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new LoadImmediate(0, 10));
        instructions.add(new Store(0, MemoryLocation.x));
        instructions.add(new LoadImmediate(0, 11));
        instructions.add(new Store(0, MemoryLocation.y));
        instructions.add(new Atomic());
        instructions.add(new Await(MemoryLocation.x, AwaitComparator.GT, MemoryLocation.y));
        instructions.add(new LoadImmediate(2, 100));
        instructions.add(new EndAtomic());

        SimulatorThread t = machine.createThread(0);
        t.queueInstructions(instructions);
        t.executeInstruction(); // LI $R0 10
        t.executeInstruction(); // ST $R0 x
        t.executeInstruction(); // LI $R0 11
        t.executeInstruction(); // ST $R0 y
        t.executeInstruction(); // ATOMIC (and therefore await)

        Assert.assertNotEquals(100, t.getRegisters()[2].getValue());
        Assert.assertTrue(t.getInstructionsList().get(t.getInstructionPointer()) instanceof Atomic);

    }

    @Test
    public void awaitConditionGreaterEqualTrueFirstTry()
    {
        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new LoadImmediate(0, 5));
        instructions.add(new Store(0, MemoryLocation.x));
        instructions.add(new LoadImmediate(0, 5));
        instructions.add(new Store(0, MemoryLocation.y));
        instructions.add(new Atomic());
        instructions.add(new Await(MemoryLocation.x, AwaitComparator.GE, MemoryLocation.y));
        instructions.add(new LoadImmediate(2, 100));
        instructions.add(new EndAtomic());

        SimulatorThread t = machine.createThread(0);
        t.queueInstructions(instructions);
        t.executeInstruction(); // LI $R0 5
        t.executeInstruction(); // ST $R0 x
        t.executeInstruction(); // LI $R0 5
        t.executeInstruction(); // ST $R0 y
        t.executeInstruction(); // ATOMIC (and therefore await)

        Assert.assertEquals(100, t.getRegisters()[2].getValue());

    }

    @Test
    public void awaitConditionGreaterEqualFalseFirstTry()
    {
        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new LoadImmediate(0, 7));
        instructions.add(new Store(0, MemoryLocation.x));
        instructions.add(new LoadImmediate(0, 11));
        instructions.add(new Store(0, MemoryLocation.y));
        instructions.add(new Atomic());
        instructions.add(new Await(MemoryLocation.x, AwaitComparator.GE, MemoryLocation.y));
        instructions.add(new LoadImmediate(2, 100));
        instructions.add(new EndAtomic());

        SimulatorThread t = machine.createThread(0);
        t.queueInstructions(instructions);
        t.executeInstruction(); // LI $R0 7
        t.executeInstruction(); // ST $R0 x
        t.executeInstruction(); // LI $R0 11
        t.executeInstruction(); // ST $R0 y
        t.executeInstruction(); // ATOMIC (and therefore await)

        Assert.assertNotEquals(100, t.getRegisters()[2].getValue());
        Assert.assertTrue(t.getInstructionsList().get(t.getInstructionPointer()) instanceof Atomic);

    }

    @Test
    public void awaitConditionLessThanTrueFirstTry()
    {
        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new LoadImmediate(0, 4));
        instructions.add(new Store(0, MemoryLocation.x));
        instructions.add(new LoadImmediate(0, 5));
        instructions.add(new Store(0, MemoryLocation.y));
        instructions.add(new Atomic());
        instructions.add(new Await(MemoryLocation.x, AwaitComparator.LT, MemoryLocation.y));
        instructions.add(new LoadImmediate(2, 100));
        instructions.add(new EndAtomic());

        SimulatorThread t = machine.createThread(0);
        t.queueInstructions(instructions);
        t.executeInstruction(); // LI $R0 4
        t.executeInstruction(); // ST $R0 x
        t.executeInstruction(); // LI $R0 5
        t.executeInstruction(); // ST $R0 y
        t.executeInstruction(); // ATOMIC (and therefore await)

        Assert.assertEquals(100, t.getRegisters()[2].getValue());

    }

    @Test
    public void awaitConditionLessThanFalseFirstTry()
    {
        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new LoadImmediate(0, 7));
        instructions.add(new Store(0, MemoryLocation.x));
        instructions.add(new LoadImmediate(0, 7));
        instructions.add(new Store(0, MemoryLocation.y));
        instructions.add(new Atomic());
        instructions.add(new Await(MemoryLocation.x, AwaitComparator.LT, MemoryLocation.y));
        instructions.add(new LoadImmediate(2, 100));
        instructions.add(new EndAtomic());

        SimulatorThread t = machine.createThread(0);
        t.queueInstructions(instructions);
        t.executeInstruction(); // LI $R0 7
        t.executeInstruction(); // ST $R0 x
        t.executeInstruction(); // LI $R0 7
        t.executeInstruction(); // ST $R0 y
        t.executeInstruction(); // ATOMIC (and therefore await)

        Assert.assertNotEquals(100, t.getRegisters()[2].getValue());
        Assert.assertTrue(t.getInstructionsList().get(t.getInstructionPointer()) instanceof Atomic);

    }

    @Test
    public void awaitConditionLessEqualTrueFirstTry()
    {
        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new LoadImmediate(0, 5));
        instructions.add(new Store(0, MemoryLocation.x));
        instructions.add(new LoadImmediate(0, 9));
        instructions.add(new Store(0, MemoryLocation.y));
        instructions.add(new Atomic());
        instructions.add(new Await(MemoryLocation.x, AwaitComparator.LE, MemoryLocation.y));
        instructions.add(new LoadImmediate(2, 100));
        instructions.add(new EndAtomic());

        SimulatorThread t = machine.createThread(0);
        t.queueInstructions(instructions);
        t.executeInstruction(); // LI $R0 5
        t.executeInstruction(); // ST $R0 x
        t.executeInstruction(); // LI $R0 9
        t.executeInstruction(); // ST $R0 y
        t.executeInstruction(); // ATOMIC (and therefore await)

        Assert.assertEquals(100, t.getRegisters()[2].getValue());

    }

    @Test
    public void awaitConditionLessEqualFalseFirstTry()
    {
        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new LoadImmediate(0, 8));
        instructions.add(new Store(0, MemoryLocation.x));
        instructions.add(new LoadImmediate(0, 7));
        instructions.add(new Store(0, MemoryLocation.y));
        instructions.add(new Atomic());
        instructions.add(new Await(MemoryLocation.x, AwaitComparator.LE, MemoryLocation.y));
        instructions.add(new LoadImmediate(2, 100));
        instructions.add(new EndAtomic());

        SimulatorThread t = machine.createThread(0);
        t.queueInstructions(instructions);
        t.executeInstruction(); // LI $R0 8
        t.executeInstruction(); // ST $R0 x
        t.executeInstruction(); // LI $R0 7
        t.executeInstruction(); // ST $R0 y
        t.executeInstruction(); // ATOMIC (and therefore await)

        Assert.assertNotEquals(100, t.getRegisters()[2].getValue());
        Assert.assertTrue(t.getInstructionsList().get(t.getInstructionPointer()) instanceof Atomic);

    }

    @Test
    public void awaitConditionEqualTrueSecondTry()
    {
        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new LoadImmediate(0, 7));
        instructions.add(new Store(0, MemoryLocation.x));
        instructions.add(new LoadImmediate(0, 10));
        instructions.add(new Store(0, MemoryLocation.y));
        instructions.add(new Atomic());
        instructions.add(new Await(MemoryLocation.x, AwaitComparator.EQ, MemoryLocation.y));
        instructions.add(new LoadImmediate(2, 100));
        instructions.add(new EndAtomic());

        SimulatorThread t = machine.createThread(0);
        t.queueInstructions(instructions);
        t.executeInstruction(); // LI $R0 7
        t.executeInstruction(); // ST $R0 x
        t.executeInstruction(); // LI $R0 7
        t.executeInstruction(); // ST $R0 y
        t.executeInstruction(); // ATOMIC (and therefore AWAIT)

        Assert.assertNotEquals(100, t.getRegisters()[2].getValue());
        Assert.assertTrue(t.getInstructionsList().get(t.getInstructionPointer()) instanceof  Atomic);
        
        // Another thread updates memory
        t.getMemory().setVariable(MemoryLocation.y, 7);
        t.executeInstruction();

        Assert.assertEquals(100, t.getRegisters()[2].getValue());


    }

    @Test
    public void awaitConditionEqualMultipleFalsesEventuallyTrue()
    {
        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new LoadImmediate(0, 7));
        instructions.add(new Store(0, MemoryLocation.x));
        instructions.add(new LoadImmediate(0, 10));
        instructions.add(new Store(0, MemoryLocation.y));
        instructions.add(new Atomic());
        instructions.add(new Await(MemoryLocation.x, AwaitComparator.EQ, MemoryLocation.y));
        instructions.add(new LoadImmediate(2, 100));
        instructions.add(new EndAtomic());

        SimulatorThread t = machine.createThread(0);
        t.queueInstructions(instructions);
        t.executeInstruction(); // LI $R0 7
        t.executeInstruction(); // ST $R0 x
        t.executeInstruction(); // LI $R0 7
        t.executeInstruction(); // ST $R0 y
        t.executeInstruction(); // ATOMIC (and therefore AWAIT)
        t.executeInstruction(); // Await condition fail
        t.executeInstruction(); // Await condition fail
        t.executeInstruction(); // Await condition fail
        t.executeInstruction(); // Await condition fail

        Assert.assertNotEquals(100, t.getRegisters()[2].getValue());
        Assert.assertTrue(t.getInstructionsList().get(t.getInstructionPointer()) instanceof  Atomic);

        // Another thread updates memory
        t.getMemory().setVariable(MemoryLocation.y, 7);
        t.executeInstruction();

        Assert.assertEquals(100, t.getRegisters()[2].getValue());


    }

    @Test
    public void awaitDoesNotExecuteInstructionsAfter()
    {
        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new LoadImmediate(0, 10));
        instructions.add(new Store(0, MemoryLocation.x));
        instructions.add(new LoadImmediate(0, 10));
        instructions.add(new Store(0, MemoryLocation.y));
        instructions.add(new Atomic());
        instructions.add(new Await(MemoryLocation.x, AwaitComparator.EQ, MemoryLocation.y));
        instructions.add(new LoadImmediate(2, 100));
        instructions.add(new EndAtomic());
        instructions.add(new LoadImmediate(5, 23));

        SimulatorThread t = machine.createThread(0);
        t.queueInstructions(instructions);
        t.executeInstruction(); // LI $R0 7
        t.executeInstruction(); // ST $R0 x
        t.executeInstruction(); // LI $R0 7
        t.executeInstruction(); // ST $R0 y
        t.executeInstruction(); // ATOMIC (and therefore AWAIT)

        // Make sure it doesn't execute LDI $R5 23 automatically (check it leaves atomic mode)
        Assert.assertNotEquals(23, t.getRegisters()[5].getValue());


    }
}

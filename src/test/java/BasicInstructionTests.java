import com.colinparrott.parallelsimulator.engine.hardware.Machine;
import com.colinparrott.parallelsimulator.engine.hardware.MemoryLocation;
import com.colinparrott.parallelsimulator.engine.hardware.SimulatorThread;
import com.colinparrott.parallelsimulator.engine.instructions.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class BasicInstructionTests
{
    private Machine machine;

    @Before
    public void initialise()
    {
        machine = new Machine();
    }


    @Test
    public void singleLoadImmediate()
    {
        Instruction i = new LoadImmediate(0, 5);
        SimulatorThread t = machine.createThread(0);
        t.queueInstructions(new ArrayList<Instruction>(Arrays.asList(i)));
        t.executeInstruction();

        Assert.assertEquals(5, t.getRegisters()[0].getValue());
    }

    @Test
    public void singleLoad()
    {
        machine.getMemory().setVariable(MemoryLocation.x, 20);
        Instruction i = new Load(8, MemoryLocation.x);
        SimulatorThread t = machine.createThread(0);
        t.queueInstructions(new ArrayList<Instruction>(Arrays.asList(i)));
        t.executeInstruction();

        Assert.assertEquals(20, t.getRegisters()[8].getValue());
    }

    @Test
    public void singleStore()
    {
        Instruction i = new Store(8, MemoryLocation.a);
        SimulatorThread t = machine.createThread(0);
        t.getRegisters()[8].setValue(4);
        t.queueInstructions(new ArrayList<Instruction>(Arrays.asList(i)));
        t.executeInstruction();

        Assert.assertEquals(4, machine.getMemory().getValue(MemoryLocation.a));
    }

    @Test
    public void singleAdd()
    {
        Instruction i = new Add(8, 7, 3);
        SimulatorThread t = machine.createThread(0);
        t.getRegisters()[7].setValue(32);
        t.getRegisters()[3].setValue(10);
        t.queueInstructions(new ArrayList<Instruction>(Arrays.asList(i)));
        t.executeInstruction();

        Assert.assertEquals(42, t.getRegisters()[8].getValue());
    }


    @Test
    public void singleAddImmediate()
    {
        Instruction i = new AddImmediate(8, 7, 3);
        SimulatorThread t = machine.createThread(0);
        t.getRegisters()[8].setValue(19);
        t.getRegisters()[7].setValue(33);
        t.queueInstructions(new ArrayList<Instruction>(Arrays.asList(i)));
        t.executeInstruction();

        Assert.assertEquals(36, t.getRegisters()[8].getValue());
    }

    @Test
    public void singleSubtract()
    {
        Instruction i = new Sub(8, 7, 3);
        SimulatorThread t = machine.createThread(0);
        t.getRegisters()[7].setValue(3);
        t.getRegisters()[3].setValue(20);
        t.queueInstructions(new ArrayList<Instruction>(Arrays.asList(i)));
        t.executeInstruction();

        Assert.assertEquals(-17, t.getRegisters()[8].getValue());
    }


    @Test
    public void singleSubtractImmediate()
    {
        Instruction i = new SubImmediate(8, 7, 3);
        SimulatorThread t = machine.createThread(0);
        t.getRegisters()[7].setValue(33);
        t.queueInstructions(new ArrayList<Instruction>(Arrays.asList(i)));
        t.executeInstruction();

        Assert.assertEquals(30, t.getRegisters()[8].getValue());
    }

    @Test
    public void singleMultiply()
    {
        Instruction i = new Mul(8, 7, 3);
        SimulatorThread t = machine.createThread(0);
        t.getRegisters()[7].setValue(3);
        t.getRegisters()[3].setValue(2);
        t.queueInstructions(new ArrayList<Instruction>(Arrays.asList(i)));
        t.executeInstruction();

        Assert.assertEquals(6, t.getRegisters()[8].getValue());
    }


    @Test
    public void singleMultiplyImmediate()
    {
        Instruction i = new MulImmediate(8, 7, 8);
        SimulatorThread t = machine.createThread(0);
        t.getRegisters()[7].setValue(7);
        t.queueInstructions(new ArrayList<Instruction>(Arrays.asList(i)));
        t.executeInstruction();

        Assert.assertEquals(56, t.getRegisters()[8].getValue());
    }

    @Test
    public void singleDivNoRemainder()
    {
        Instruction i = new Div(8, 7, 3);
        SimulatorThread t = machine.createThread(0);
        t.getRegisters()[7].setValue(14);
        t.getRegisters()[3].setValue(2);
        t.queueInstructions(new ArrayList<Instruction>(Arrays.asList(i)));
        t.executeInstruction();

        Assert.assertEquals(7, t.getRegisters()[8].getValue());
    }

    @Test
    public void singleDivWithRemainder()
    {
        Instruction i = new Div(8, 7, 3);
        SimulatorThread t = machine.createThread(0);
        t.getRegisters()[7].setValue(9);
        t.getRegisters()[3].setValue(2);
        t.queueInstructions(new ArrayList<Instruction>(Arrays.asList(i)));
        t.executeInstruction();

        Assert.assertEquals(4, t.getRegisters()[8].getValue());
    }

    @Test
    public void singleDivImmediateNoRemainder()
    {
        Instruction i = new DivImmediate(8, 7, 3);
        SimulatorThread t = machine.createThread(0);
        t.getRegisters()[7].setValue(33);
        t.queueInstructions(new ArrayList<Instruction>(Arrays.asList(i)));
        t.executeInstruction();

        Assert.assertEquals(11, t.getRegisters()[8].getValue());
    }

    @Test
    public void singleDivImmediateWithRemainder()
    {
        Instruction i = new DivImmediate(8, 7, 3);
        SimulatorThread t = machine.createThread(0);
        t.getRegisters()[7].setValue(20);
        t.queueInstructions(new ArrayList<Instruction>(Arrays.asList(i)));
        t.executeInstruction();

        Assert.assertEquals(6, t.getRegisters()[8].getValue());
    }

    @Test
    public void labelAdvancesPointer()
    {
        Instruction i = new Label("abc");
        SimulatorThread t = machine.createThread(0);
        t.queueInstructions(new ArrayList<Instruction>(Arrays.asList(i)));
        t.executeInstruction();

        Assert.assertEquals(1, t.getInstructionPointer());
    }

    @Test
    public void branchIfEqual()
    {
        ArrayList<Instruction> instructions = new ArrayList<>();
        // TODO: COMPLETE
    }

}

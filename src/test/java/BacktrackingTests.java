import com.colinparrott.parallelsimulator.engine.hardware.Machine;
import com.colinparrott.parallelsimulator.engine.hardware.SimulatorThread;
import com.colinparrott.parallelsimulator.engine.instructions.Atomic;
import com.colinparrott.parallelsimulator.engine.instructions.EndAtomic;
import com.colinparrott.parallelsimulator.engine.instructions.Instruction;
import com.colinparrott.parallelsimulator.engine.instructions.LoadImmediate;
import com.colinparrott.parallelsimulator.engine.simulator.MachineStateHolder;
import com.colinparrott.parallelsimulator.engine.simulator.Simulator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class BacktrackingTests
{
    private Simulator sim;
    private Machine machine;
    private MachineStateHolder stateHistory;

    @Before
    public void initialise()
    {
        sim = new Simulator(){};
        machine = Simulator.getMachine();
        stateHistory = Simulator.getStateHistory();

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

        Assert.assertEquals(5, Simulator.getMachine().getThread(0).getRegisters()[0].getValue());
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

        Assert.assertEquals(1, Simulator.getMachine().getThread(0).getRegisters()[0].getValue());
        Assert.assertEquals(3, Simulator.getMachine().getThread(0).getRegisters()[1].getValue());

        sim.stepBackward();

        Assert.assertEquals(0, Simulator.getMachine().getThread(0).getRegisters()[0].getValue());
        Assert.assertEquals(0, Simulator.getMachine().getThread(0).getRegisters()[1].getValue());
    }

}


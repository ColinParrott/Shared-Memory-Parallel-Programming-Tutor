import com.colinparrott.parallelsimulator.engine.hardware.Machine;
import com.colinparrott.parallelsimulator.engine.hardware.SimulatorThread;
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
        t.executeInstruction();
        t.executeInstruction();
        sim.stepBackward();

        Assert.assertEquals(5, t.getRegisters()[0].getValue());
    }

}


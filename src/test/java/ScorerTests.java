import com.colinparrott.parallelsimulator.engine.hardware.Memory;
import com.colinparrott.parallelsimulator.engine.hardware.MemoryLocation;
import com.colinparrott.parallelsimulator.engine.simulator.programs.generators.heuristics.old.ScoringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class ScorerTests
{

    @Test
    public void memoryStatesEqualEmptyTrue()
    {
        Memory m1 = new Memory();
        Memory m2 = new Memory();

        Assert.assertTrue(ScoringUtils.memoryStatesEqual(m1, m2));
    }

    @Test
    public void memoryStatesEqualTrue()
    {
        Memory m1 = new Memory();
        Memory m2 = new Memory();
        m1.setVariable(MemoryLocation.a, 3);
        m2.setVariable(MemoryLocation.a, 3);

        Assert.assertTrue(ScoringUtils.memoryStatesEqual(m1, m2));
    }

    @Test
    public void memoryStatesEqualFalse()
    {
        Memory m1 = new Memory();
        Memory m2 = new Memory();
        m1.setVariable(MemoryLocation.a, 2);
        m2.setVariable(MemoryLocation.a, 3);

        Assert.assertFalse(ScoringUtils.memoryStatesEqual(m1, m2));
    }

    @Test
    public void avoidsExpectedOutcomesContainsTrue()
    {
        Memory m1 = new Memory();
        Memory m2 = new Memory();
        m1.setVariable(MemoryLocation.a, 2);
        m1.setVariable(MemoryLocation.z, 5);
        m2.setVariable(MemoryLocation.b, 3);
        m2.setVariable(MemoryLocation.p, 10);

        ArrayList<Memory> expectedOutcomes = new ArrayList<>();
        expectedOutcomes.add(m1);
        expectedOutcomes.add(m2);

        Memory testMemory = new Memory();
        testMemory.setVariable(MemoryLocation.a, 2);
        testMemory.setVariable(MemoryLocation.z, 5);


        Assert.assertFalse(ScoringUtils.outcomeAvoidsExpectedOutcomes(expectedOutcomes, testMemory));
    }

    @Test
    public void avoidsExpectedOutcomesContainsFalse()
    {
        Memory m1 = new Memory();
        Memory m2 = new Memory();
        m1.setVariable(MemoryLocation.a, 2);
        m1.setVariable(MemoryLocation.z, 5);
        m2.setVariable(MemoryLocation.b, 3);
        m2.setVariable(MemoryLocation.p, 10);

        ArrayList<Memory> expectedOutcomes = new ArrayList<>();
        expectedOutcomes.add(m1);
        expectedOutcomes.add(m2);

        Memory testMemory = new Memory();
        testMemory.setVariable(MemoryLocation.a, 2);
        testMemory.setVariable(MemoryLocation.z, 4);


        Assert.assertTrue(ScoringUtils.outcomeAvoidsExpectedOutcomes(expectedOutcomes, testMemory));
    }

    @Test
    public void avoidsExpectedOutcomesContainsFalse2()
    {
        Memory m1 = new Memory();
        Memory m2 = new Memory();
        m1.setVariable(MemoryLocation.a, 2);
        m1.setVariable(MemoryLocation.z, 5);
        m2.setVariable(MemoryLocation.b, 3);
        m2.setVariable(MemoryLocation.p, 10);

        ArrayList<Memory> expectedOutcomes = new ArrayList<>();
        expectedOutcomes.add(m1);
        expectedOutcomes.add(m2);

        Memory testMemory = new Memory();
        testMemory.setVariable(MemoryLocation.a, 2);
        testMemory.setVariable(MemoryLocation.b, 3);


        Assert.assertTrue(ScoringUtils.outcomeAvoidsExpectedOutcomes(expectedOutcomes, testMemory));
    }
}

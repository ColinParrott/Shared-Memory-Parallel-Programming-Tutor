package gen;

import com.colinparrott.parallelsimulator.engine.hardware.Memory;
import com.colinparrott.parallelsimulator.engine.hardware.MemoryLocation;
import org.junit.Assert;
import org.junit.Test;

public class UtilTests
{
    @Test
    public void MemoryHashCodesEqual()
    {
        Memory m1 = new Memory();
        Memory m2 = new Memory();

        m1.setVariable(MemoryLocation.x, 10);
        m2.setVariable(MemoryLocation.x, 10);

        Assert.assertEquals(m1.hashCode(), m2.hashCode());
    }

    @Test
    public void MemoryHashCodesUnequalValue()
    {
        Memory m1 = new Memory();
        Memory m2 = new Memory();

        m1.setVariable(MemoryLocation.x, 14);
        m2.setVariable(MemoryLocation.x, 10);

        Assert.assertNotEquals(m1.hashCode(), m2.hashCode());
    }


    @Test
    public void MemoryHashCodesUnequalVariable()
    {
        Memory m1 = new Memory();
        Memory m2 = new Memory();

        m1.setVariable(MemoryLocation.x, 10);
        m2.setVariable(MemoryLocation.y, 10);

        Assert.assertNotEquals(m1.hashCode(), m2.hashCode());
    }
}

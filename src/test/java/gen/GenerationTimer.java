package gen;

import com.colinparrott.parallelsimulator.engine.simulator.programs.Program;
import com.colinparrott.parallelsimulator.engine.simulator.programs.ProgramList;
import com.colinparrott.parallelsimulator.engine.simulator.programs.generators.askoutcome.GenMethod;
import com.colinparrott.parallelsimulator.engine.simulator.programs.generators.askoutcome.ThreadSequenceGen;
import org.junit.Test;

public class GenerationTimer
{

    private final long maxRunTime = 2; // seconds

    @Test
    public void timeGenerationRandom()
    {
        ProgramList list = new ProgramList();
        Program[] programs = new Program[]{list.loadXPlusPlusTwoThreads(), list.loadXPlusPlusAtomicTwoThreads(), list.loadAwaitFlag(), list.loadBEqualsAPlusAAtomic(), list.loadBEqualsAPlusA(),
                list.loadWhileLoop()};

        for (GenMethod method : GenMethod.values())
        {
            int programTotal = 0;
            for (Program p : programs)
            {
                long totalTime = maxRunTime * 1000000000; // in nanoseconds
                long startTime = System.nanoTime();
                boolean toFinish = false;
                long count = 0;
                while (!toFinish)
                {
                    ThreadSequenceGen.generateThreadSequence(p, 20, method);
                    count++;
                    toFinish = (System.nanoTime() - startTime >= totalTime);
                }

                programTotal += count;
            }
            System.out.println(method + ": " + (programTotal / programs.length));

        }
    }


}

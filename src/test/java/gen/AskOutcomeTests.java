package gen;

import com.colinparrott.parallelsimulator.engine.hardware.Memory;
import com.colinparrott.parallelsimulator.engine.simulator.programs.ProgramList;
import com.colinparrott.parallelsimulator.engine.simulator.programs.generators.ExecutionSequenceStateAnalyser;
import com.colinparrott.parallelsimulator.engine.simulator.programs.generators.askoutcome.GenMethod;
import com.colinparrott.parallelsimulator.engine.simulator.programs.generators.askoutcome.ThreadSequenceGen;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

public class AskOutcomeTests
{
    private static ProgramList programList;

    @BeforeClass
    public static void init()
    {
        programList = new ProgramList();
    }

    @Test
    public void CompletelyRandomThreadSeq()
    {
        int[] r = ThreadSequenceGen.generateThreadSequence(programList.loadXPlusPlusTwoThreads(), 6, GenMethod.RANDOM_MAX_GLOBAL_STEPS_IGNORE_COMPLETE_THREADS);
        ArrayList<Memory> memories = ExecutionSequenceStateAnalyser.calculateMemoryStates(programList.loadXPlusPlusTwoThreads(), r);

        int j = 0;
        for(int i : r)
        {
            System.out.print(i + "\n" + memories.get(j) + "\n");
            j++;
        }
    }

}

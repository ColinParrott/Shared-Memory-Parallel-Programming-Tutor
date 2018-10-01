import com.colinparrott.parallelsimulator.engine.simulator.programs.ProgramList;
import com.colinparrott.parallelsimulator.engine.simulator.programs.generators.EqualThreadSteps;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

public class GeneratorTests
{
    private static ProgramList programList;

    @BeforeClass
    public static void init()
    {
        programList = new ProgramList();
    }

    @Test
    public void EqualStepsThreadTest()
    {
        int[] seq = EqualThreadSteps.generateRandomThreadSequence(programList.loadXPlusPlusTwoThreads(), 3);

        for(int i : seq)
        {
            System.out.println(i);
        }


    }

    @Test
    public void EqualStepsThreadAllPermutationsTest()
    {
        ArrayList<ArrayList<Integer>> sequences =  EqualThreadSteps.generateAllPossibleSequences(programList.loadXPlusPlusTwoThreads(), 4);
        for(ArrayList<Integer> list : sequences)
        {
            for(int i : list)
            {
                System.out.print(i  + " ");
            }
            System.out.println();
        }

        System.out.println(sequences.size());
    }
}

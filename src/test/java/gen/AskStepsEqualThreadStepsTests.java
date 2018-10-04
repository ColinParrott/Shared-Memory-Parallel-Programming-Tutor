package gen;

import com.colinparrott.parallelsimulator.engine.hardware.Memory;
import com.colinparrott.parallelsimulator.engine.simulator.programs.ProgramList;
import com.colinparrott.parallelsimulator.engine.simulator.programs.generators.asksteps.EqualThreadSteps;
import com.colinparrott.parallelsimulator.engine.simulator.programs.generators.ExecutionSequenceStateAnalyser;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

public class AskStepsEqualThreadStepsTests
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
    public void EqualStepsThreadAndSimulateTest()
    {
        int[] seq = EqualThreadSteps.generateRandomThreadSequence(programList.loadXPlusPlusTwoThreads(), 3);
        ArrayList<Memory> memories = ExecutionSequenceStateAnalyser.calculateMemoryStates(programList.loadXPlusPlusTwoThreads(), seq);

        for(Memory m : memories)
        {
            System.out.println(m.toString());
        }


    }

    @Test
    public void EqualStepsThreadAllPermutationsTest()
    {

        ArrayList<ArrayList<Integer>> sequences =  EqualThreadSteps.generateAllPossibleSequences(programList.loadXPlusPlusTwoThreads(), 5);
        for(ArrayList<Integer> list : sequences)
        {
           // ExecutionSequenceStateAnalyser.calculateMemoryStates(programList.loadXPlusPlusTwoThreads(), list);
            for(int i : list)
            {

//                System.out.print(i  + " ");
            }
//            System.out.println();
        }

        System.out.println(sequences.size());
    }
}

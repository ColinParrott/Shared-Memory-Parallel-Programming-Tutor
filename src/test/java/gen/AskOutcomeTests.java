package gen;

import com.colinparrott.parallelsimulator.engine.hardware.Memory;
import com.colinparrott.parallelsimulator.engine.instructions.*;
import com.colinparrott.parallelsimulator.engine.simulator.programs.Program;
import com.colinparrott.parallelsimulator.engine.simulator.programs.ProgramList;
import com.colinparrott.parallelsimulator.engine.simulator.programs.generators.ExecutionSequenceStateAnalyser;
import com.colinparrott.parallelsimulator.engine.simulator.programs.generators.askoutcome.GenMethod;
import com.colinparrott.parallelsimulator.engine.simulator.programs.generators.askoutcome.ThreadSequenceGen;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

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

//        int j = 0;
//        for(int i : r)
//        {
//            System.out.print(i + "\n" + memories.get(j) + "\n");
//            j++;
//        }
    }

    @Test
    public void ProbabilisticStoresSeq()
    {
        int[] r = ThreadSequenceGen.generateThreadSequence(programList.loadBEqualsAPlusA(), 100, GenMethod.PROBABILISTIC_MOST_STORES_DYNAMIC);
        ArrayList<Memory> memories = ExecutionSequenceStateAnalyser.calculateMemoryStates(programList.loadBEqualsAPlusA(), r);

//        for (int i = 1; i < memories.size(); i++)
//        {
//            System.out.print(r[i - 1] + "\n" + memories.get(i) + "\n");
//        }
    }


    @Test
    public void SmartRandom()
    {
        Program p = new ProgramList().loadXPlusPlusAtomicTwoThreads();

        for (int id : p.getUsedThreadIDs())
        {
            ArrayList<Instruction> instructions = p.getInstructionsForThread(id);

            boolean inAtomic = false;

            SequenceType sequenceType = SequenceType.NO_BRANCHES_OR_AWAITS;
            boolean foundBranch = false;
            boolean foundAwait = false;
            boolean foundLoop = false;
            int lastStorePointer = -1;

            HashMap<String, Integer> labelPositions = new HashMap<>();

            int instructionPointer = 0;
            for (Instruction i : instructions)
            {
                if (isBranch(i))
                {
                    foundBranch = true;

                    if (labelPositions.size() > 0)
                    {
                        for (String jumpLabel : labelPositions.keySet())
                        {
                            // if label appears before this jump (assumes the labels before don't have jumps at the end, should never generate such programs anyway it'd be weird)
                            if (jumpLabel.equals(getBranchLabel(i)) && labelPositions.get(jumpLabel) < instructionPointer)
                            {
                                foundLoop = true;
                                break;
                            }
                        }
                    }
                }
                else if (i.getKeyword() == InstructionKeyword.AWAIT)
                {
                    foundAwait = true;
                }
                else if (i.getKeyword() == InstructionKeyword.LABEL)
                {
                    labelPositions.put(((Label) i).getLabel(), instructionPointer);
                }
                else if (i.getKeyword() == InstructionKeyword.ST)
                {
                    lastStorePointer = instructionPointer;
                }
                else if (i.getKeyword() == InstructionKeyword.ATOMIC)
                {
                    inAtomic = true;
                }
                else if (i.getKeyword() == InstructionKeyword.ENDATOMIC)
                {
                    inAtomic = false;
                }

                if (!inAtomic)
                    instructionPointer++;
            }

            System.out.println(p.getName() + " [#" + id + "]");
            System.out.println("Branch: " + foundBranch);
            System.out.println("Await: " + foundAwait);
            System.out.println("Loop: " + foundLoop);
            System.out.println("Last Store: " + lastStorePointer);
            System.out.println();
        }


    }

    private String getBranchLabel(Instruction i)
    {
        if (i.getKeyword() == InstructionKeyword.BEQ)
        {
            return ((BranchIfEqual) i).getLabel();
        }
        else if (i.getKeyword() == InstructionKeyword.BNE)
        {
            return ((BranchNotEqual) i).getLabel();
        }
        else if (i.getKeyword() == InstructionKeyword.BLT)
        {
            return ((BranchLessThan) i).getLabel();
        }
        else if (i.getKeyword() == InstructionKeyword.BGT)
        {
            return ((BranchGreaterThan) i).getLabel();
        }
        else if (i.getKeyword() == InstructionKeyword.JUMP)
        {
            return ((Jump) i).getLabel();
        }
        else
        {
            assert false;
            return null;
        }
    }

    private boolean isBranch(Instruction i)
    {
        return i.getKeyword() == InstructionKeyword.BEQ || i.getKeyword() == InstructionKeyword.BGT || i.getKeyword() == InstructionKeyword.BLT || i.getKeyword() == InstructionKeyword.BNE
                || i.getKeyword() == InstructionKeyword.JUMP;
    }

    private enum SequenceType
    {
        NO_BRANCHES_OR_AWAITS,
        BRANCHES_NO_LOOP_NO_AWAIT,
        BRANCHES_LOOP_NO_AWAIT,
        AWAIT_NO_BRANCHES,
        AWAIT_AND_BRANCHES

    }


}

package com.colinparrott.parallelsimulator.engine.simulator.programs.generators.algorithm;

import com.colinparrott.parallelsimulator.engine.hardware.MemoryLocation;
import com.colinparrott.parallelsimulator.engine.instructions.*;
import com.colinparrott.parallelsimulator.engine.simulator.programs.Program;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Preprocesses assembly programs so that they can be used
 * as input for ML/natural computing algorithms
 * <p>
 * TODO: track which registers refer to which variables (if feasible)
 *
 * Options for differentiating same instructions between different threads:
 * 1) Add thread-switch elements into sequence (i.e. 0 = go to thread 0, 1 =
 * 2) Add a constant (depending on thread number) to each instruction (can still clash, number has to be big enough)
 */
public class Preprocessor {

    public static ArrayList<Integer> Preprocess(Program p) {

        ArrayList<Integer> output = new ArrayList<>();
        HashMap<Integer, MemoryLocation> variableMap = generateVariableMap(p);

        for (int id : p.getUsedThreadIDs()) {

        }

        return null;
    }

    private static HashMap<Integer, MemoryLocation> generateVariableMap(Program p) {
        HashMap<Integer, MemoryLocation> result = new HashMap<>();

        for (int id : p.getUsedThreadIDs()) {
            for (Instruction i : p.getInstructionsForThread(id)) {
                MemoryLocation[] referencedVariables = getVariablesReferencedInInstruction(i);


            }
        }

        return null;
    }

    private static MemoryLocation[] getVariablesReferencedInInstruction(Instruction i) {
        InstructionKeyword k = i.getKeyword();

        if (k == InstructionKeyword.LD) {
            return new MemoryLocation[]{((Load) i).getMemoryLocation()};
        } else if (k == InstructionKeyword.ST) {
            return new MemoryLocation[]{((Store) i).getMemoryLocation()};
        } else if (k == InstructionKeyword.AWAIT) {
            return new MemoryLocation[]{((Await) i).getFirstVariable(), ((Await) i).getSecondVariable()};
        } else {
            return new MemoryLocation[0];
        }
    }


}

package com.colinparrott.parallelsimulator.programs;

import com.colinparrott.parallelsimulator.engine.hardware.Memory;
import com.colinparrott.parallelsimulator.engine.hardware.MemoryLocation;
import com.colinparrott.parallelsimulator.engine.instructions.Instruction;
import com.colinparrott.parallelsimulator.engine.simulator.programs.Program;
import com.colinparrott.parallelsimulator.programs.parser.AssemblyParser;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

/**
 * Class to reprsent JSON assembly program
 */
public class ProgramFile {

    private String name;
    private HashMap<String, Integer> initialMemory;
    private String[] highLevelCode;
    private String[][] threadInstructions;
    private boolean desiredSequences;
    private int[][] interestingSequences;

    public ProgramFile(String name, HashMap<String, Integer> initialMemory, String[] highLevelCode, String[][] threadInstructions, boolean desiredSequences, int[][] interestingSequences) {
        this.name = name;
        this.threadInstructions = threadInstructions;
        this.initialMemory = initialMemory;
        this.highLevelCode = highLevelCode;
        this.desiredSequences = desiredSequences;
        this.interestingSequences = interestingSequences;
    }

    public Program generateProgram() {
        AssemblyParser assemblyParser = new AssemblyParser();
        Memory m = new Memory();

        for(String s : initialMemory.keySet()){
            m.setVariable(MemoryLocation.valueOf(s.toLowerCase()), initialMemory.get(s));
        }

        HashMap<Integer, ArrayList<Instruction>> instructionLists = new HashMap<>();

        for(int i = 0; i < threadInstructions.length; i++){
            Pair<ArrayList<Instruction>, Optional<String>> data = assemblyParser.parseAssemblyCode(threadInstructions[i]);
            if(!data.getValue().isPresent()){
                instructionLists.put(i, assemblyParser.parseAssemblyCode(threadInstructions[i]).getKey());
            }
            else{
                System.out.println("program invalid!");
                return null;
            }
        }


        return new Program(m, name, highLevelCode, instructionLists, this);
    }

    public String getName() {
        return name;
    }

    public HashMap<String, Integer> getInitialMemory() {
        return initialMemory;
    }

    public String[] getHighLevelCode() {
        return highLevelCode;
    }

    public String[][] getThreadInstructions() {
        return threadInstructions;
    }

    public boolean isDesiredSequences() {
        return desiredSequences;
    }

    public int[][] getInterestingSequences() {
        return interestingSequences;
    }
}

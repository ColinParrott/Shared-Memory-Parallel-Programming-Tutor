package com.colinparrott.parallelsimulator;

import com.colinparrott.parallelsimulator.engine.instructions.Instruction;
import com.colinparrott.parallelsimulator.engine.simulator.programs.Program;
import com.colinparrott.parallelsimulator.engine.simulator.programs.generators.PCTAlgorithm;
import com.colinparrott.parallelsimulator.engine.simulator.programs.generators.algorithm.RoundRobinAlgorithm;
import com.colinparrott.parallelsimulator.engine.simulator.programs.generators.askoutcome.GenMethod;
import com.colinparrott.parallelsimulator.engine.simulator.programs.generators.askoutcome.ThreadSequenceGen;
import com.colinparrott.parallelsimulator.engine.simulator.programs.generators.heuristics.old.ScoringUtils;
import com.colinparrott.parallelsimulator.programs.ProgramFile;
import com.colinparrott.parallelsimulator.programs.ProgramFileReader;

import java.util.ArrayList;
import java.util.Objects;

public class GenAlgTester {

    private static ArrayList<ProgramFile> getProgramsWithExpectedOutcomesAvailabe(ArrayList<ProgramFile> programFiles){
        ArrayList<ProgramFile> result = new ArrayList<>();
        for(ProgramFile pf : programFiles){
            if(pf.getExpectedOutcomes() != null && pf.getExpectedOutcomes().size() > 0)
                result.add(pf);
        }

        return result;
    }

    public static void main(String[] args) {
        ArrayList<ProgramFile> pfs = getProgramsWithExpectedOutcomesAvailabe(Objects.requireNonNull(ProgramFileReader.readProgramFiles()));
        int steps = 40;
        int sequencesToGenerate = 1000;
        ratePCT(pfs, steps, sequencesToGenerate);
        rateOldAlgorithms(pfs, steps, sequencesToGenerate);
        rateRR(pfs, steps, sequencesToGenerate, true);
        rateRR(pfs, steps, sequencesToGenerate, false);
    }

    private static void rateRR(ArrayList<ProgramFile> pfs, int steps, int sequencesToGenerate, boolean checkForComplete){
        double total = 0.0;
        for(ProgramFile pf : pfs){
            ArrayList<int[]> programSequences = new ArrayList<>();
            for(int i = 0; i < sequencesToGenerate; i++){
                ArrayList<Integer> seq = new RoundRobinAlgorithm(checkForComplete).generateSequence(pf.generateProgram(), steps);

//                if(i < 5)
//                {
//                    System.out.print("[");
//                    for(int s : seq)
//                        System.out.print(s + " ");
//                    System.out.println("]");
//                }

                programSequences.add(seq.stream().mapToInt(x -> x).toArray());
            }

            double percentage = ScoringUtils.scoreSequences(pf, programSequences);
//            System.out.println(String.format("%s: %.5f", pf.getName(), percentage));
            total += percentage;
        }

        System.out.println(String.format("Round Robin (%s) : %.0f / %d", checkForComplete, total, pfs.size() * sequencesToGenerate));
    }
    private static void rateOldAlgorithms(ArrayList<ProgramFile> pfs, int steps, int sequencesToGenerate){
        for(GenMethod genMethod : GenMethod.values()){
            double genMethodTotal = 0.0;
            for(ProgramFile pf : pfs){

                ArrayList<int[]> programSequences = new ArrayList<>();
                for(int i = 0; i < sequencesToGenerate; i++){
                    int[] seq = ThreadSequenceGen.generateThreadSequence(pf.generateProgram(), steps, genMethod);
                    programSequences.add(seq);
                }

                double percentage = ScoringUtils.scoreSequences(pf, programSequences);
                genMethodTotal += percentage;
            }

            System.out.println(String.format("%s: %.0f / %d", genMethod, genMethodTotal, pfs.size() * sequencesToGenerate));
        }
    }
    private static void ratePCT(ArrayList<ProgramFile> pfs, int steps, int sequencesToGenerate){
        for(int depth = 1; depth <= 10; depth++){
            double total = 0.0;
            for(ProgramFile programFile : pfs){
//            System.out.println(programFile.getName());
                Program p = programFile.generateProgram();
                ArrayList<ArrayList<Instruction>> threadInstructions = new ArrayList<>();
                for(int i : p.getUsedThreadIDs()){
                    threadInstructions.add(p.getInstructionsForThread(i));
                }

                ArrayList<int[]> sequences = new ArrayList<>();
                for(int i = 0; i < sequencesToGenerate; i++){
                    ArrayList<Integer> seq = PCTAlgorithm.generateSequence(threadInstructions, steps, depth);
                    sequences.add(seq.stream().mapToInt(x -> x).toArray());
                }

                double percentage = ScoringUtils.scoreSequences(programFile, sequences);
                total += percentage;
            }

            System.out.println(String.format("Depth %d: %.0f / %d", depth, total, pfs.size() * sequencesToGenerate));
        }
    }
}

package com.colinparrott.parallelsimulator.engine.simulator.programs.generators.algorithm;

import com.colinparrott.parallelsimulator.engine.hardware.Machine;
import com.colinparrott.parallelsimulator.engine.hardware.SimulatorThread;
import com.colinparrott.parallelsimulator.engine.simulator.programs.Program;
import com.colinparrott.parallelsimulator.engine.simulator.programs.generators.GenerationSim;

import java.util.*;
import java.util.stream.Collectors;

public class RoundRobinAlgorithm extends GenerationAlgorithm {

    private boolean checkForComplete;

    public RoundRobinAlgorithm(boolean checkForComplete) {
        this.checkForComplete = checkForComplete;
    }

    @Override
    public ArrayList<Integer> generateSequence(Program p, int steps) {
        ArrayList<Integer> sequence = new ArrayList<>();

        ArrayList<Integer> roundRobinSchedule = setInitialSchedule(p.getUsedThreadIDs());
        int schedulePointer = 0;

        GenerationSim sim = new GenerationSim();
        Machine machine = sim.getMachine();
        SimulatorThread[] simThreads = new SimulatorThread[p.getUsedThreadIDs().length];

        if (checkForComplete) {
            for (int i = 0; i < p.getUsedThreadIDs().length; i++) {
                simThreads[i] = machine.createThread(i);
                simThreads[i].queueInstructions(p.getInstructionsForThread(i));
            }
        }


        for (int i = 0; i < steps; i++) {
            if(roundRobinSchedule.size() == 0)
                return sequence;

            int chosenThread = roundRobinSchedule.get(schedulePointer);
            sequence.add(chosenThread);

            if(checkForComplete){
                sim.stepForward(chosenThread);
                if (machine.getThread(chosenThread).getInstructionPointer() >= machine.getThread(chosenThread).getInstructionsList().size())
                {
                    roundRobinSchedule.remove(schedulePointer);
                }

            }

            schedulePointer++;

            if (schedulePointer > roundRobinSchedule.size() - 1)
                schedulePointer = 0;
        }

        return sequence;
    }

    private ArrayList<Integer> setInitialSchedule(int[] threads) {
        ArrayList<Integer> threadsList = (ArrayList<Integer>) Arrays.stream(threads).boxed().collect(Collectors.toList());
        ArrayList<Integer> schedule = new ArrayList<>();

        for (int i = 0; i < threads.length; i++) {
            int randIndex = new Random().nextInt(threadsList.size());
            schedule.add(threadsList.get(randIndex));
            threadsList.remove(randIndex);
        }

        return schedule;
    }
}

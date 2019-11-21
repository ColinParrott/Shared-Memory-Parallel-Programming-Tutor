package com.colinparrott.parallelsimulator.engine.simulator.programs.generators.algorithm;

import com.colinparrott.parallelsimulator.engine.simulator.programs.Program;
import com.colinparrott.parallelsimulator.programs.ProgramFile;

import java.util.ArrayList;
import java.util.Arrays;

public class CustomAlgorithm
{

    private double[] threadWeights;

    public int[] generateSequence(ProgramFile pf, int steps)
    {
        ArrayList<Integer> sequence = new ArrayList<>();
        Program p = pf.generateProgram();
        int numThreads = p.getUsedThreadIDs().length;
        threadWeights = generateThreadWeights(numThreads);

        for (double d : threadWeights)
        {
            System.out.println(d);
        }

        return sequence.stream().mapToInt(i -> i).toArray();
    }

    private void updateWeight(int threadIndex, double updateAmount)
    {
        threadWeights[threadIndex] += updateAmount;
    }

    // Uniform weights
    private double[] generateThreadWeights(int numThreads)
    {
        if (numThreads == 1) return new double[]{1.0};

        double weight = (double) 1 / numThreads;
        double[] weights = new double[numThreads];
        Arrays.fill(weights, weight);
        return weights;
    }
}

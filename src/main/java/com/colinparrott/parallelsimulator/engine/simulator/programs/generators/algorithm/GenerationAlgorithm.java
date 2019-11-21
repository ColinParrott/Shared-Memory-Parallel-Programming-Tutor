package com.colinparrott.parallelsimulator.engine.simulator.programs.generators.algorithm;

import com.colinparrott.parallelsimulator.engine.simulator.programs.Program;

import java.util.ArrayList;

public abstract class GenerationAlgorithm {

    public abstract ArrayList<Integer> generateSequence(Program p, int steps);
}

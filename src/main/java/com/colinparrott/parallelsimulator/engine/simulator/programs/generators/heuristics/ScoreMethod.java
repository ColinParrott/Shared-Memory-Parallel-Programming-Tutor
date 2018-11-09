package com.colinparrott.parallelsimulator.engine.simulator.programs.generators.heuristics;

public enum ScoreMethod
{
    // Counts every single time a variable changes value in memory
    VARIABLE_CHANGE_COUNT,

    // Counts the number of variables with different values compared only from the initial memory state and the end
    VARIABLE_CHANGE_START_AND_END,

    COUNT_UNIQUE_OUTCOMES,

    COMPARE_TO_HUMAN
}

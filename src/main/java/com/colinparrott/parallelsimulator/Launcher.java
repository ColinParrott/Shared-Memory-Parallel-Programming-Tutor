package com.colinparrott.parallelsimulator;

import com.colinparrott.parallelsimulator.engine.simulator.CLISimulator;

public class Launcher
{
    public static void main(String[] args)
    {
        CLISimulator simulator = new CLISimulator(args);
        simulator.start();
    }
}

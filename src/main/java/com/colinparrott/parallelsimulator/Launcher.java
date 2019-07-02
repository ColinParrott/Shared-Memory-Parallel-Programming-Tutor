package com.colinparrott.parallelsimulator;

import com.colinparrott.parallelsimulator.engine.simulator.gui.GUISimulator;

public class Launcher
{
    public static void main(String[] args)
    {
//        CLISimulator simulator = new CLISimulator(args);
//        simulator.originalCLI();
        GUISimulator.main(args);
    }
}

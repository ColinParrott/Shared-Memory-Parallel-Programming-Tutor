package com.colinparrott.parallelsimulator.simulator;

import com.colinparrott.parallelsimulator.hardware.Machine;

public class Simulator
{
    private static Machine machine;
    private static MachineStateHolder stateHistory;

    public static void main(String[] args)
    {
        machine = new Machine();
        stateHistory = new MachineStateHolder();
    }
}

package com.colinparrott.parallelsimulator.engine.simulator.gui.controls;

import com.jfoenix.controls.JFXButton;

public class JFXHistoryButton extends JFXButton
{
    private int machineStep;

    public JFXHistoryButton(String text, int machineStep)
    {
        super(text);
        this.machineStep = machineStep;
    }


    /**
     * Gets machineStep.
     *
     * @return Value of machineStep.
     */
    public int getMachineStep()
    {
        return machineStep;
    }
}

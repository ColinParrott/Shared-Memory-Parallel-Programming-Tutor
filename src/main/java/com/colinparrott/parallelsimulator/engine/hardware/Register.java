package com.colinparrott.parallelsimulator.engine.hardware;

/**
 * Represents a register on a processor core
 */

public class Register
{
    private int registerNum;
    private int value;
    private SimulatorThread thread;

    Register(int registerNum, SimulatorThread thread)
    {
        this.thread = thread;
        this.registerNum = registerNum;
        this.value = 0;
    }

    /**
     * Sets register number
     *
     * @param registerNum New value of registerNum.
     */
    public void setRegisterNum(int registerNum)
    {
        this.registerNum = registerNum;
    }

    /**
     * Sets new value.
     *
     * @param value New value of value.
     */
    public void setValue(int value)
    {
        this.value = value;
        thread.setLastUpdatedRegister(this.registerNum);
    }

    /**
     * Gets registerNum.
     *
     * @return Value of registerNum.
     */
    public int getRegisterNum()
    {
        return registerNum;
    }

    /**
     * Gets value.
     *
     * @return Value of value.
     */
    public int getValue()
    {
        return value;
    }

    public SimulatorThread getThread()
    {
        return thread;
    }
}

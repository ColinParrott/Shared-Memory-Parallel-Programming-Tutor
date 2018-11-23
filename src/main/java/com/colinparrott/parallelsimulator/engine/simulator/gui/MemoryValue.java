package com.colinparrott.parallelsimulator.engine.simulator.gui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class MemoryValue
{

    private final SimpleStringProperty locationName;
    private final SimpleIntegerProperty value;

    public MemoryValue()
    {
        this("", 0);
    }

    public MemoryValue(String fName, int lName)
    {
        this.locationName = new SimpleStringProperty(fName);
        this.value = new SimpleIntegerProperty(lName);
    }

    public String getLocationName()
    {
        return locationName.get();
    }

    public void setLocationName(String fName)
    {
        locationName.set(fName);
    }

    public int getValue()
    {
        return value.get();
    }

    public void setValue(int fName)
    {
        value.set(fName);
    }
}
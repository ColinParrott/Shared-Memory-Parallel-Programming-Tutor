package com.colinparrott.parallelsimulator.programs.parser;

import com.colinparrott.parallelsimulator.engine.instructions.ParameterType;

public class ParameterTypeData
{
    public ParameterType parameterType;
    public String data;

    public ParameterTypeData(ParameterType parameterType, String data)
    {
        this.parameterType = parameterType;
        this.data = data;
    }

    public ParameterType getParameterType()
    {
        return parameterType;
    }

    public void setParameterType(ParameterType parameterType)
    {
        this.parameterType = parameterType;
    }

    public String getData()
    {
        return data;
    }

    public void setData(String data)
    {
        this.data = data;
    }
}

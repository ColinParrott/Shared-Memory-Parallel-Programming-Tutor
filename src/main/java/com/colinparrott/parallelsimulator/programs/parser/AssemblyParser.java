package com.colinparrott.parallelsimulator.programs.parser;

import com.colinparrott.parallelsimulator.engine.hardware.SimulatorThread;
import com.colinparrott.parallelsimulator.engine.instructions.ParameterType;
import com.colinparrott.parallelsimulator.engine.simulator.programs.Program;
import javafx.util.Pair;

import java.util.Optional;

public class AssemblyParser
{
    /**
     * Parses a list of assembly code lines, returns a program if valid if not null and the error message
     *
     * @param lines Lines of assembly code
     * @return Pair containing program if valid, returns with error message if invalid
     */
    public static Pair<Program, Optional<String>> parseAssemblyCode(String[] lines)
    {
        // TODO: Complete
        return null;
    }


    private Pair<ParameterTypeData, Optional<String>> getParamType(String s)
    {
        int index = 0;

        // REGISTER
        if (s.charAt(index) == '$')
        {
            index++;
            if (s.charAt(index) == 'R')
            {
                index++;
                if (Character.isDigit(s.charAt(index)) && (int) s.charAt(index) < SimulatorThread.REGISTERS_PER_THREAD)
                {
                    return new Pair<>(new ParameterTypeData(ParameterType.INT_LITERAL, String.valueOf(s.charAt(index))), Optional.empty());
                }
            }
            else
            {
                return new Pair<>(new ParameterTypeData(ParameterType.INT_LITERAL, null), Optional.of("Expected register, got: \"" + s + "\""));
            }
        }
        // INT_LITERAL
        else if (isInteger(s, 10))
        {
            return new Pair<>(new ParameterTypeData(ParameterType.INT_LITERAL, s), Optional.empty());
        }
        // MEMORY_LOCATION
        else if (s.matches("^([a-zA-z])"))
        {
            return new Pair<>(new ParameterTypeData(ParameterType.LABEL_STRING, s), Optional.empty());
        }

        return null;
    }

    private boolean isInteger(String s, int radix)
    {
        if (s.isEmpty()) return false;
        for (int i = 0; i < s.length(); i++)
        {
            if (i == 0 && s.charAt(i) == '-')
            {
                if (s.length() == 1) return false;
                else continue;
            }
            if (Character.digit(s.charAt(i), radix) < 0) return false;
        }
        return true;
    }

}

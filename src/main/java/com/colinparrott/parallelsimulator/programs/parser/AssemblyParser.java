package com.colinparrott.parallelsimulator.programs.parser;

import com.colinparrott.parallelsimulator.engine.hardware.SimulatorThread;
import com.colinparrott.parallelsimulator.engine.instructions.Instruction;
import com.colinparrott.parallelsimulator.engine.instructions.InstructionKeyword;
import com.colinparrott.parallelsimulator.engine.instructions.ParameterType;
import com.colinparrott.parallelsimulator.engine.simulator.programs.Program;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Optional;

public class AssemblyParser
{
    /**
     * Parses a list of assembly code lines, returns a program if valid if not null and the error message
     *
     * @param lines Lines of assembly code
     * @return Pair containing program if valid, returns with error message if invalid
     */

    private int line;

    public Pair<ArrayList<Instruction>, Optional<String>> parseAssemblyCode(String[] lines)
    {
        // TODO: Complete

        for (line = 0; line < lines.length; line++)
        {
            // Trims whitespace and splits line on whitespace
            String[] parts = lines[line].trim().split("\\s+");
            String instruction = parts[line];
            InstructionKeyword keyword = null;

            try
            {
                keyword = InstructionKeyword.valueOf(instruction);
            }
            catch (IllegalArgumentException e)
            {
                return new Pair<>(null, generateErrorMessage(String.format("Unrecognised instruction: %s", instruction)));
            }

            Instruction instructionObject = parseInstruction(keyword, lines[line]);


        }
        return null;
    }

    private Instruction parseInstruction(InstructionKeyword keyword, String line)
    {
        // TODO: finish
        return null;
    }

    private Optional<String> generateErrorMessage(String errorMesssage)
    {
        return Optional.of(String.format("[Parse Error] %s  (Line %d)", errorMesssage, line));
    }

    private Pair<ParameterTypeData, Optional<String>> parseParamType(ParameterType type, String s) throws UnimplementedParseException
    {
        switch (type)
        {
            case REGISTER:
                return parseRegister(s);
            case MEMORY_LOCATION:
                return parseMemoryLocation(s);
            case CONSTANT:
                return parseConstant(s);
            case LABEL_STRING:
                return parseLabel(s);
            default:
                throw new UnimplementedParseException("Parser does not support token of type: " + type);
        }
    }

    private Pair<ParameterTypeData, Optional<String>> parseLabel(String s)
    {
        if (s.matches("[a-zA-z]"))
        {
            return new Pair<>(new ParameterTypeData(ParameterType.LABEL_STRING, s), Optional.empty());
        }

        return new Pair<>(new ParameterTypeData(ParameterType.ERROR_TYPE, null), generateErrorMessage(String.format("Invalid label name: \"%s\" (must only contain alphabetic characters)", s)));
    }

    private Pair<ParameterTypeData, Optional<String>> parseRegister(String s)
    {
        if (s.charAt(0) == '$')
        {
            if (s.charAt(1) == 'R')
            {
                if (Character.isDigit(s.charAt(2)) && (int) s.charAt(2) < SimulatorThread.REGISTERS_PER_THREAD)
                {
                    return new Pair<>(new ParameterTypeData(ParameterType.CONSTANT, String.valueOf(s.charAt(2))), Optional.empty());
                }
            }
            else
            {
                return new Pair<>(new ParameterTypeData(ParameterType.CONSTANT, null), generateErrorMessage("Expected register, got: \"" + s + "\""));
            }
        }

        return new Pair<>(new ParameterTypeData(ParameterType.ERROR_TYPE, null), generateErrorMessage(String.format("Unknown register: \"%s\"", s)));
    }

    private Pair<ParameterTypeData, Optional<String>> parseConstant(String s)
    {
        if (isInteger(s, 10))
        {
            return new Pair<>(new ParameterTypeData(ParameterType.CONSTANT, s), Optional.empty());
        }

        return new Pair<>(new ParameterTypeData(ParameterType.ERROR_TYPE, null), generateErrorMessage(String.format("Invalid constant value: %s", s)));
    }

    private Pair<ParameterTypeData, Optional<String>> parseMemoryLocation(String s)
    {
        if (s.matches("^([a-zA-z])"))
        {
            return new Pair<>(new ParameterTypeData(ParameterType.LABEL_STRING, s), Optional.empty());
        }

        return new Pair<>(new ParameterTypeData(ParameterType.ERROR_TYPE, null), generateErrorMessage(String.format("Invalid memory location: %s (must be from a-z)", s)));
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

    private class UnimplementedParseException extends Exception
    {
        UnimplementedParseException(String errorMessage)
        {
            super(errorMessage);
        }
    }



}

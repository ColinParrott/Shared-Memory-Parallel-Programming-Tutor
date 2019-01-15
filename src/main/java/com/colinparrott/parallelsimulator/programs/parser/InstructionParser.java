package com.colinparrott.parallelsimulator.programs.parser;

import com.colinparrott.parallelsimulator.engine.hardware.MemoryLocation;
import com.colinparrott.parallelsimulator.engine.hardware.SimulatorThread;
import com.colinparrott.parallelsimulator.engine.instructions.*;
import javafx.util.Pair;

import java.util.Optional;

class InstructionParser {

    private int line;

    InstructionParser(int line){
        this.line = line;
    }

    /**
     * Parses single line and returns instruction object if valid, else null and error message in string
     *
     * @param keyword Instruction keyword
     * @param params   Instruction params text
     * @return Returns Instruction (if valid) and error message (if invalid)
     */
    Pair<Instruction, Optional<String>> parseInstruction(InstructionKeyword keyword, String[] params) {
        Instruction instruction = null;

        // Verifies program is correct
        int i = 0;
        for(ParameterType type : InstructionKeyword.getExpectedParams(keyword)){
            Pair<ParameterTypeData, Optional<String>> data = parseParamType(type, params[i]);
            if(data.getValue().isPresent())
            {
                return new Pair<>(null, data.getValue());
            }
            i++;
        }

        switch (keyword)
        {
            case LD:
                instruction = new Load(registerNumFromString(params[0]), MemoryLocation.valueOf(params[1]));
                break;
            case ADDI:
                instruction = new AddImmediate(registerNumFromString(params[0]), Integer.valueOf(params[1]),registerNumFromString(params[2]));
                break;
            case ST:
                instruction = new Store(registerNumFromString(params[0]), MemoryLocation.valueOf(params[1]));
                break;
        }

        return new Pair<>(instruction, Optional.empty());
    }

    private int registerNumFromString(String s){
        return Integer.valueOf(s.substring(2, 3));
    }

    private Pair<ParameterTypeData, Optional<String>> parseParamType(ParameterType type, String s) {
        switch (type) {
            case REGISTER:
                return parseRegister(s);
            case MEMORY_LOCATION:
                return parseMemoryLocation(s);
            case CONSTANT:
                return parseConstant(s);
            case LABEL_STRING:
                return parseLabel(s);
            case COMPARATOR:
                return parseComparator(s);
            default:
                return new Pair<>(null, Optional.of("Parser does not support token of type: " + type));
        }
    }

    private Pair<ParameterTypeData, Optional<String>> parseComparator(String s) {
        switch (s) {
            case "EQ":
                return new Pair<>(new ParameterTypeData(ParameterType.COMPARATOR, "EQ"), Optional.empty());
            case "NE":
                return new Pair<>(new ParameterTypeData(ParameterType.COMPARATOR, "NE"), Optional.empty());
            case "GT":
                return new Pair<>(new ParameterTypeData(ParameterType.COMPARATOR, "GT"), Optional.empty());
            case "LT":
                return new Pair<>(new ParameterTypeData(ParameterType.COMPARATOR, "LT"), Optional.empty());
            case "GE":
                return new Pair<>(new ParameterTypeData(ParameterType.COMPARATOR, "GE"), Optional.empty());
            case "LE":
                return new Pair<>(new ParameterTypeData(ParameterType.COMPARATOR, "LE"), Optional.empty());
            default:
                return new Pair<>(null, Optional.of("Invalid comparator: %s"));
        }
    }

    private Pair<ParameterTypeData, Optional<String>> parseLabel(String s) {
        if (s.matches("[a-zA-z]")) {
            return new Pair<>(new ParameterTypeData(ParameterType.LABEL_STRING, s), Optional.empty());
        }

        return new Pair<>(new ParameterTypeData(ParameterType.ERROR_TYPE, null), generateErrorMessage(String.format("Invalid label name: \"%s\" (must only contain alphabetic characters)", s)));
    }

    private Pair<ParameterTypeData, Optional<String>> parseRegister(String s) {
        if (s.charAt(0) == '$') {
            if (s.charAt(1) == 'R') {
                if (Character.isDigit(s.charAt(2)) && Character.getNumericValue(s.charAt(2)) < SimulatorThread.REGISTERS_PER_THREAD) {
                    return new Pair<>(new ParameterTypeData(ParameterType.CONSTANT, String.valueOf(s.charAt(2))), Optional.empty());
                }
            } else {
                return new Pair<>(new ParameterTypeData(ParameterType.CONSTANT, null), generateErrorMessage("Expected register, got: \"" + s + "\""));
            }
        }

        return new Pair<>(new ParameterTypeData(ParameterType.ERROR_TYPE, null), generateErrorMessage(String.format("Unknown register: \"%s\"", s)));
    }

    private Pair<ParameterTypeData, Optional<String>> parseConstant(String s) {
        if (isInteger(s, 10)) {
            return new Pair<>(new ParameterTypeData(ParameterType.CONSTANT, s), Optional.empty());
        }

        return new Pair<>(new ParameterTypeData(ParameterType.ERROR_TYPE, null), generateErrorMessage(String.format("Invalid constant value: %s", s)));
    }

    private Pair<ParameterTypeData, Optional<String>> parseMemoryLocation(String s) {
        if (s.matches("^([a-zA-z])")) {
            return new Pair<>(new ParameterTypeData(ParameterType.LABEL_STRING, s), Optional.empty());
        }

        return new Pair<>(new ParameterTypeData(ParameterType.ERROR_TYPE, null), generateErrorMessage(String.format("Invalid memory location: %s (must be from a-z)", s)));
    }

    private boolean isInteger(String s, int radix) {
        if (s.isEmpty()) return false;
        for (int i = 0; i < s.length(); i++) {
            if (i == 0 && s.charAt(i) == '-') {
                if (s.length() == 1) return false;
                else continue;
            }
            if (Character.digit(s.charAt(i), radix) < 0) return false;
        }
        return true;
    }

    private Optional<String> generateErrorMessage(String errorMessage) {
        return Optional.of(String.format("[Parse Error] %s  (Line %d)", errorMessage, line));
    }

}

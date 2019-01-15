package com.colinparrott.parallelsimulator.programs.parser;

import com.colinparrott.parallelsimulator.engine.instructions.Instruction;
import com.colinparrott.parallelsimulator.engine.instructions.InstructionKeyword;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class AssemblyParser {
    /**
     * Parses a list of assembly code lines, returns a program if valid if not null and the error message
     *
     * @param lines Lines of assembly code
     * @return Pair containing program if valid, returns with error message if invalid
     */

    private int line;


    public Pair<ArrayList<Instruction>, Optional<String>> parseAssemblyCode(String[] lines) {
        // TODO: Complete


        ArrayList<Instruction> instructions = new ArrayList<>() ;
        for (line = 0; line < lines.length; line++) {
            // Trims whitespace and splits line on whitespace
            String[] parts = lines[line].trim().split("\\s+");
            String instruction = parts[0];
            InstructionKeyword keyword;


            try {
                keyword = InstructionKeyword.valueOf(instruction);
            } catch (IllegalArgumentException e) {
                return new Pair<>(null, generateErrorMessage(String.format("Unrecognised instruction: %s", instruction)));
            }

            InstructionParser instructionParser = new InstructionParser(line);
            String[] params = Arrays.copyOfRange(parts, 1, parts.length);
            Pair<Instruction, Optional<String>> instructionObject = instructionParser.parseInstruction(keyword, params);

            if (instructionObject != null && instructionObject.getValue().isPresent()) {
                return new Pair<>(null, generateErrorMessage(instructionObject.getValue().get()));
            }

//            System.out.println(instructionObject.getKey());
            instructions.add(instructionObject.getKey());
        }

        return new Pair<>(instructions, Optional.empty());
    }

    protected Optional<String> generateErrorMessage(String errorMessage) {
        return Optional.of(String.format("[Parse Error] %s  (Line %d)", errorMessage, line));
    }


    private class UnimplementedParseException extends Exception {
        UnimplementedParseException(String errorMessage) {
            super(errorMessage);
        }
    }


}

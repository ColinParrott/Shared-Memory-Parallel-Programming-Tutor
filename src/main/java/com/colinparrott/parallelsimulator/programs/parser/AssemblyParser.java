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

    private int lineNumber;


    public Pair<ArrayList<Instruction>, Optional<String>> parseAssemblyCode(String[] lines) {
        // TODO: Complete


        ArrayList<Instruction> instructions = new ArrayList<>() ;
        for (lineNumber = 0; lineNumber < lines.length; lineNumber++) {
            // Trims whitespace and splits lineNumber on whitespace
            String[] parts = lines[lineNumber].trim().split("\\s+");
            String instruction = parts[0];
            InstructionKeyword keyword;

            try {

                // Label is special case with no keyword
                if (!instruction.endsWith(":"))
                {
                    keyword = InstructionKeyword.valueOf(instruction);

                }
                else
                {
                    keyword = InstructionKeyword.LABEL;
                }

            } catch (IllegalArgumentException e) {
                System.out.println("hi");
                return new Pair<>(null, generateErrorMessage(String.format("Unrecognised instruction: %s", instruction)));
            }


            InstructionParser instructionParser = new InstructionParser(lineNumber);
            String[] params = Arrays.copyOfRange(parts, 1, parts.length);

            if (instruction.endsWith(":"))
            {
                params = new String[]{instruction.replace(":", "")};
            }
            Pair<Instruction, Optional<String>> instructionObject = instructionParser.parseInstruction(keyword, params);

            if (instructionObject != null && instructionObject.getValue().isPresent()) {
                System.out.println("instruction object null");
                System.out.println(generateErrorMessage(instructionObject.getValue().get()).get());
                return new Pair<>(null, generateErrorMessage(instructionObject.getValue().get()));
            }

//            System.out.println(instructionObject.getKey());
            instructions.add(instructionObject.getKey());
        }

        return new Pair<>(instructions, Optional.empty());
    }

    protected Optional<String> generateErrorMessage(String errorMessage) {
        return Optional.of(String.format("[Parse Error] %s  (Line %d)", errorMessage, lineNumber + 1));
    }


    private class UnimplementedParseException extends Exception {
        UnimplementedParseException(String errorMessage) {
            super(errorMessage);
        }
    }


}

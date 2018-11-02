package com.colinparrott.parallelsimulator.engine.simulator.programs;

import com.colinparrott.parallelsimulator.engine.hardware.Machine;
import com.colinparrott.parallelsimulator.engine.hardware.MemoryLocation;
import com.colinparrott.parallelsimulator.engine.hardware.Register;
import com.colinparrott.parallelsimulator.engine.hardware.SimulatorThread;
import com.colinparrott.parallelsimulator.engine.instructions.Atomic;
import com.colinparrott.parallelsimulator.engine.instructions.EndAtomic;
import com.colinparrott.parallelsimulator.engine.instructions.Instruction;

import java.util.ArrayList;
import java.util.Scanner;

public class CLIUtils
{

    /**
     * Prints the state of the memory and each thread
     *
     * @param machine
     * @param variables Variables in memory to show
     * @param threadIds Threads to show
     */
    public static void printState(Machine machine, MemoryLocation[] variables, int[] threadIds)
    {
        System.out.println("--- MEMORY ---");
        for (MemoryLocation v : variables)
        {
            System.out.println(v + ": " + machine.getMemory().getValue(v));
        }

        for (int id : threadIds)
        {
            System.out.println("--- THREAD " + id + " ---");
            SimulatorThread thread = machine.getThread(id);

            for (Register r : thread.getRegisters())
            {
                System.out.print(String.format("$R%d: %d\t", r.getRegisterNum(), r.getValue()));
            }

            System.out.println();

            Instruction next = thread.getNextInstruction();

            if (!(next instanceof Atomic))
                System.out.println("Next Instruction: " + thread.getNextInstruction());
            else
            {
                ArrayList<Instruction> batch = new ArrayList<>();
                for (int i = thread.getInstructionPointer(); i < thread.getInstructionsList().size(); i++)
                {
                    Instruction instruction = thread.getInstructionsList().get(i);
                    batch.add(instruction);
                    if (instruction instanceof EndAtomic) break;
                }

                StringBuilder s = new StringBuilder();
                s.append("Next Instruction: ");

                for (Instruction i : batch)
                {
                    s.append(i.toString());
                    s.append("\t");
                }

                System.out.println(s.toString());
            }

        }
        System.out.println();


    }

    /**
     * Helper function that checks if char is in list of valid command characters
     *
     * @param check Character to check
     * @return True if valid, false otherwise
     */
    public static boolean isValidCommand(char check, char[] validCommands)
    {
        for (char c : validCommands)
        {
            if (c == check)
                return true;
        }

        return false;
    }

    public char getValidChar(String validChars)
    {
        Scanner scanner = new Scanner(System.in);
        String s;

        do
        {
            s = scanner.nextLine();

            if (s.length() != 1 && !validChars.contains(s)) System.out.println("Please enter 'r' or 'q'.");
        } while (s.length() != 1 && !validChars.contains(s));

        return s.charAt(0);
    }

    public int getValidInt(int min, int max)
    {
        Scanner scanner = new Scanner(System.in);
        int input = min - 1;

        do
        {

            try
            {
                input = Integer.valueOf(scanner.nextLine());
            }
            catch (Exception e)
            {
                input = min - 1;

            }

            if (input < min || input > max)
                System.out.println("Enter a number between " + min + " and " + max);
        } while (input < min || input > max);

        return input;
    }
}

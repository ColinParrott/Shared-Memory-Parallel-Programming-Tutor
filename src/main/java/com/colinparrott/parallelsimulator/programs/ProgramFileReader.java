package com.colinparrott.parallelsimulator.programs;

import com.colinparrott.parallelsimulator.engine.simulator.programs.Program;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Class for reading program files (json files) from disk
 */
public class ProgramFileReader {
    public static final String PROGRAMS_DIR = "programs/";

    private static String[][] readNestedStringArray(JsonObject jsonObject, String topKey) {

        JsonArray topLevelArray = jsonObject.getAsJsonArray(topKey);
        String[][] returnArray = new String[topLevelArray.size()][];

        int j = 0;
        for (JsonElement e : topLevelArray) {
            JsonArray arr = e.getAsJsonArray();
            String[] seq = new String[arr.size()];

            for (int i = 0; i < arr.size(); i++) {
                seq[i] = arr.get(i).getAsString();

            }
            returnArray[j] = seq;
            j++;
        }

        return returnArray;
    }

    private static int[][] readNestedIntArray(JsonObject jsonObject, String topKey) {

        JsonArray topLevelArray = jsonObject.getAsJsonArray(topKey);
        int[][] returnArray = new int[topLevelArray.size()][];

        int j = 0;
        for (JsonElement e : topLevelArray) {
            JsonArray arr = e.getAsJsonArray();
            int[] seq = new int[arr.size()];

            for (int i = 0; i < arr.size(); i++) {
                seq[i] = arr.get(i).getAsInt();

            }
            returnArray[j] = seq;
            j++;
        }

        return returnArray;
    }


    private static ProgramFile readProgramFile(File f) throws IOException {

        // create JsonReader object
        JsonObject root = new JsonParser().parse(new String(Files.readAllBytes(Paths.get(f.getPath())))).getAsJsonObject();
        String name = root.get("name").getAsString();
        JsonObject sequenceData = root.get("sequenceData").getAsJsonObject();
        boolean desiredSequences = sequenceData.get("desired").getAsBoolean();
        JsonArray sequencesJson = sequenceData.getAsJsonArray("sequences");
        int[][] sequences = readNestedIntArray(sequenceData, "sequences");

        // Parse initial memory
        HashMap<String, Integer> initialMemory = new HashMap<>();
        JsonObject memoryObject = root.getAsJsonObject("initialMemory");
        for (String s : memoryObject.keySet()) {
            initialMemory.put(s, memoryObject.get(s).getAsInt());
        }

        // Parse high level code
        JsonArray highLevelCodeArray = root.get("highLevelCode").getAsJsonArray();
        String[] highLevelCode = new String[highLevelCodeArray.size()];
        int j = 0;
        for (JsonElement e : highLevelCodeArray) {
            highLevelCode[j] = e.getAsString();
            j++;
        }

        String[][] assemblyCodeLines = readNestedStringArray(root, "threadCode");


        return new ProgramFile(name, initialMemory, highLevelCode, assemblyCodeLines, desiredSequences, sequences);

    }

    public static ArrayList<Program> readPrograms() {
        ArrayList<Program> programs = new ArrayList<>();
        for (ProgramFile pf : Objects.requireNonNull(readProgramFiles())) {
            programs.add(pf.generateProgram());
        }

        return programs;
    }

    public static ArrayList<ProgramFile> readProgramFiles() {

        ArrayList<ProgramFile> programFiles = new ArrayList<>();
        Path programFolder = Paths.get(PROGRAMS_DIR);
        if (Files.exists(programFolder)) {
            for (File f : Objects.requireNonNull(new File(programFolder.toString()).listFiles())) {
                try {
                    programFiles.add(readProgramFile(f));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return programFiles;
        }

        return null;
    }
}

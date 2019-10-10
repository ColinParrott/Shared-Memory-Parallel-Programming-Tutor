package com.colinparrott.parallelsimulator.compiler;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class ProgramJsonProducer {

    public static void produceJsonFile(String name, String[] highLevelLines, String[][] assemblyLines)
    {
        JsonObject root = new JsonObject();
        root.addProperty("name", name);

        JsonObject sequenceData = new JsonObject();
        sequenceData.addProperty("desired", false);
        sequenceData.add("sequences", new JsonArray());
        root.add("sequenceData", sequenceData);

        JsonObject initialMemory = new JsonObject();
//        initialMemory.addProperty("a", 0);
        root.add("initialMemory", new JsonObject());

        JsonArray highLevelCode = new JsonArray();
        for (String s : highLevelLines)
            highLevelCode.add(s);

        root.add("highLevelCode", highLevelCode);

        JsonArray assemblyCodeArray = new JsonArray();


        for (String[] arr : assemblyLines)
        {

            JsonArray innerAssemblyArray = new JsonArray();
            for (String line : arr)
            {
                innerAssemblyArray.add(line);
            }
            assemblyCodeArray.add(innerAssemblyArray);
        }

        root.add("threadCode", assemblyCodeArray);

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
//        System.out.println(gson.toJson(root));
        try {
            Writer writer = new FileWriter("programs/" + name + ".json");
            gson.toJson(root, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

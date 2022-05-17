package com.gameisland.repositories;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class FileDB {
    private String filename = "steam_apps.json";
    private String directoryName = "data";

    public void writer(JsonObject jsonObject) {
        System.out.println(filename);

        File directory = new File(directoryName);
        if (!directory.exists()) {
            directory.mkdir();
        }

        try {
            File yourFile = new File(directoryName + "/" + filename);
            yourFile.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(yourFile));
            Gson gson = new Gson();
            String json = gson.toJson(jsonObject);
            writer.write(json);
            writer.close();

        } catch (Exception exception) {
            System.out.println("There is an issue with file writing");
            System.out.println(exception.getMessage());
        }
    }
}

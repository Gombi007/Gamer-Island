package com.gameisland.repositories;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;

public class FileDB {


    private File initFileWriter() {
        String fileName = "steam_apps.json";
        String directoryName = "data";
        try {

            File directory = new File(directoryName);
            if (!directory.exists()) {
                directory.mkdir();
            }

            File file = new File(directory, fileName);
            if (!file.exists()) {
                file.createNewFile();
            }

            return file;
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }

    public void writeAllSteamProductsIntoAFile(JsonObject jsonObject) {
        File file = initFileWriter();

        if (file != null) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
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

    public JsonObject getAllSteamProductsFromAFile() {
        File file = initFileWriter();
        String result = "";

        if (file != null) {
            String temp;
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                while ((temp = reader.readLine()) != null) {
                    result += temp;
                }
                reader.close();
            } catch (Exception exception) {
                System.out.println("There is an issue with file reading");
                System.out.println(exception.getMessage());
            }
        }
        JsonObject jsonObject = JsonParser.parseString(result).getAsJsonObject();
        return jsonObject;

    }
}

package com.gameisland.repositories;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

@Service
public class FileDB {


    private File initFileWriter(String fileNameByUser) {
        String fileName = fileNameByUser;
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
        File file = initFileWriter("steam_apps.json");

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
        File file = initFileWriter("steam_apps.json");
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

    public void CollectAllUnSuccessAndNonGameApp(Long removableAppFromList) {
        File file = initFileWriter("removable_apps_from_steam_apps.txt");

        if (file != null) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
                writer.write(removableAppFromList.toString());
                writer.newLine();
                writer.close();

            } catch (Exception exception) {
                System.out.println("There is an issue with file writing");
                System.out.println(exception.getMessage());
            }
        }
    }

    public Set<String> CollectedRemovableAppid() {
        File file = initFileWriter("removable_apps_from_steam_apps.txt");
        Set<String> result = new HashSet<>();

        if (file != null) {
            String temp;
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                while ((temp = reader.readLine()) != null) {
                    result.add(temp);
                }
                reader.close();

                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.close();

            } catch (Exception exception) {
                System.out.println("There is an issue with file reading");
                System.out.println(exception.getMessage());
            }
        }
        return result;
    }

    public void CollectedNewAppListWriteIntoSteamAppJson(String json) {
        File file = initFileWriter("steam_apps.json");

        if (file != null) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(json);
                writer.close();

            } catch (Exception exception) {
                System.out.println("There is an issue with file writing");
                System.out.println(exception.getMessage());
            }
        }
    }
}

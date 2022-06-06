package com.gameisland.services;

import com.gameisland.enums.StaticStrings;
import com.gameisland.exceptions.ResourceNotFoundException;
import com.gameisland.repositories.FileDB;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class SteamApiAllProductsService {

    private final String steamUrl = StaticStrings.STEAM_ALL_PRODUCTS_URL.getUrl();
    private final RestTemplate template = new RestTemplate();
    private final FileDB fileDB;

    @Autowired
    public SteamApiAllProductsService(FileDB fileDB) {
        this.fileDB = fileDB;
    }

    protected void getAllSteamProducts() {
        try {
            ResponseEntity<String> response = template.getForEntity(steamUrl, String.class);
            JsonObject responseBody = JsonParser.parseString(Objects.requireNonNull(response.getBody())).getAsJsonObject();
            fileDB.writeAllSteamProductsIntoAFile(responseBody);
        } catch (Exception exception) {
            throw new ResourceNotFoundException("The Steam API is not available");
        }
    }

    protected Set<Long> getAllSteamAppIdFromFileDB(Integer limit, Set<Long> existingAppIds) {
        Set<Long> appids = new HashSet<>();
        Iterator<JsonElement> iterator;
        JsonArray appsArrayWithinAppListObject;

        try {
            JsonObject steamAllProductsObjectFromFile = fileDB.getAllSteamProductsFromAFile();
            JsonObject appListObjectWithinProducts = steamAllProductsObjectFromFile.getAsJsonObject("applist");
            appsArrayWithinAppListObject = appListObjectWithinProducts.get("apps").getAsJsonArray();
        } catch (Exception exception) {
            throw new ResourceNotFoundException("Problem during the file database read " + exception.getMessage());
        }

        for (int i = 0; i < appsArrayWithinAppListObject.size(); i++) {
            if (limit == 0) {
                break;
            }
            JsonObject element = appsArrayWithinAppListObject.get(i).getAsJsonObject();
            Long appId = element.get("appid").getAsLong();
            if (!existingAppIds.contains(appId)) {
                appids.add(appId);
                fileDB.CollectAllUnSuccessAndNonGameApp(appId);
                limit--;
            }

            if (existingAppIds.contains(appId)) {
                fileDB.CollectAllUnSuccessAndNonGameApp(appId);
            }
        }
        return appids;
    }

    protected void removeAllSavedAndNonGameElementFromSteamAppJson() {
        try {
            Set<String> removableAppIds = fileDB.CollectedRemovableAppid();
            JsonObject steamAllProductsObjectFromFile = fileDB.getAllSteamProductsFromAFile();
            JsonArray appArray = steamAllProductsObjectFromFile.getAsJsonObject("applist").get("apps").getAsJsonArray();
            JsonArray deepCopyAppArray = appArray.deepCopy();

            for (JsonElement jsonElement : appArray) {
                JsonObject app = jsonElement.getAsJsonObject();
                String appId = app.get("appid").toString();
                if (removableAppIds.contains(appId)) {
                    deepCopyAppArray.remove(jsonElement);
                }
            }

            //create new steam_app_list without saved or unnecessary elements
            Gson gson = new GsonBuilder().disableHtmlEscaping().create();

            Map<String, HashMap<String, JsonArray>> newAppListObject = new HashMap<>();
            HashMap<String, JsonArray> newAppsArray = new HashMap<>();
            newAppsArray.put("apps", deepCopyAppArray);
            newAppListObject.put("applist", newAppsArray);
            fileDB.CollectedNewAppListWriteIntoSteamAppJson(gson.toJson(newAppListObject));

        } catch (Exception exception) {
            throw new ResourceNotFoundException("Problem during the file database read" + exception.getMessage());
        }
    }

}


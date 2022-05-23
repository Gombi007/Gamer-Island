package com.gameisland.services;

import com.gameisland.enums.StaticStrings;
import com.gameisland.exceptions.ResourceNotFoundException;
import com.gameisland.repositories.FileDB;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

@Service
public class SteamApiAllProductsService {

    private final String steamUrl = StaticStrings.STEAM_ALL_PRODUCTS_URL.getUrl();
    private final RestTemplate template = new RestTemplate();

    protected void getAllSteamProducts() {
        FileDB fileDB = new FileDB();
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
        FileDB fileDB = new FileDB();
        Iterator<JsonElement> iterator;

        try {
            JsonObject steamAllProductsObjectFromFile = fileDB.getAllSteamProductsFromAFile();
            JsonObject appListObjectWithinProducts = steamAllProductsObjectFromFile.getAsJsonObject("applist");
            JsonArray appsArrayWithinAppListObject = appListObjectWithinProducts.get("apps").getAsJsonArray();
            iterator = appsArrayWithinAppListObject.iterator();
        } catch (Exception exception) {
            throw new ResourceNotFoundException("Problem during the file database read");
        }

        while (iterator.hasNext() && limit > 0) {
            Long appId = iterator.next().getAsJsonObject().get("appid").getAsLong();
            if (!existingAppIds.contains(appId)){
            appids.add(appId);
            limit--;
            }
        }

        return appids;
    }
}


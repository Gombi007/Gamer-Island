package com.gameisland.services;

import com.gameisland.enums.StaticStrings;
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

        ResponseEntity<String> response = template.getForEntity(steamUrl, String.class);
        JsonObject responseBody = JsonParser.parseString(Objects.requireNonNull(response.getBody())).getAsJsonObject();
        fileDB.writeAllSteamProductsIntoAFile(responseBody);
    }

    protected Set<Long> getAllSteamAppIdFromFileDB(Integer limit) {
        Set<Long> appids = new HashSet<>();
        FileDB fileDB = new FileDB();

        JsonObject steamAllProductsObjectFromFile = fileDB.getAllSteamProductsFromAFile();
        JsonObject appListObjectWithinProducts = steamAllProductsObjectFromFile.getAsJsonObject("applist");
        JsonArray appsArrayWithinAppListObject = appListObjectWithinProducts.get("apps").getAsJsonArray();

        Iterator<JsonElement> iterator = appsArrayWithinAppListObject.iterator();
        while (iterator.hasNext() && limit > 0) {
            Long appId = iterator.next().getAsJsonObject().get("appid").getAsLong();
            appids.add(appId);
            limit--;
        }

        return appids;
    }
}


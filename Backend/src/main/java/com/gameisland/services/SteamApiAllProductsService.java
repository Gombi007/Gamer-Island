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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

@Service
public class SteamApiAllProductsService {

    private String steamUrl = StaticStrings.STEAM_ALL_PRODUCTS_URL.getUrl();
    private RestTemplate template = new RestTemplate();

    protected ArrayList<Long> getAllSteamProducts() {
        ArrayList<Long> appids = new ArrayList<>();
        FileDB fileDB = new FileDB();
        Boolean getSteamProductsFromTheSTeamAPI = false;

        if (getSteamProductsFromTheSTeamAPI) {
            ResponseEntity<String> response = template.getForEntity(steamUrl, String.class);
            JsonObject responseBody = JsonParser.parseString(Objects.requireNonNull(response.getBody())).getAsJsonObject();
            fileDB.writeAllSteamProductsIntoAFile(responseBody);
        }

        JsonObject steamAllProductsObjectFromFile = fileDB.getAllSteamProductsFromAFile();
        JsonObject responseBodyAppListObject = steamAllProductsObjectFromFile.getAsJsonObject("applist");
        JsonArray responseBodyAppsObjectWithinAppListObject = responseBodyAppListObject.get("apps").getAsJsonArray();

        Iterator<JsonElement> iterator = responseBodyAppsObjectWithinAppListObject.iterator();
        int i = Integer.parseInt(StaticStrings.PRODUCT_LIMIT.getUrl());
        while (iterator.hasNext() && i > 0) {
            appids.add(iterator.next().getAsJsonObject().get("appid").getAsLong());
            i--;
        }
        return appids;


    }
}


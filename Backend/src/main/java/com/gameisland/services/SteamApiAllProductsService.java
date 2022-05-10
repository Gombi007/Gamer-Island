package com.gameisland.services;

import com.gameisland.enums.StaticStrings;
import com.gameisland.models.dto.GameDto;
import com.gameisland.models.dto.SteamGameNameAndIdDto;
import com.google.gson.Gson;
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

    protected ArrayList<GameDto> getAllGameFromSteam() {
        ArrayList<GameDto> gameResult = new ArrayList<>();
        ResponseEntity<String> response = template.getForEntity(steamUrl, String.class);

        JsonObject responseBody = JsonParser.parseString(Objects.requireNonNull(response.getBody())).getAsJsonObject();
        JsonObject responseBodyAppListObject = responseBody.getAsJsonObject("applist");
        JsonElement responseBodyAppsObjectWithinAppListObject = responseBodyAppListObject.get("apps");

        Iterator<JsonElement> iterator = responseBodyAppsObjectWithinAppListObject.getAsJsonArray().iterator();
        int i = Integer.parseInt(StaticStrings.PRODUCT_LIMIT.getUrl());
        while (iterator.hasNext() && i > 0) {
            SteamGameNameAndIdDto dto = new Gson().fromJson(iterator.next(), SteamGameNameAndIdDto.class);
            if (!dto.getName().isEmpty() && !dto.getName().isBlank()) {
                String headerImgUrl = StaticStrings.STEAM_HEADER_IMAGES_URL_START.getUrl().concat(String.valueOf(dto.getAppid())).concat(StaticStrings.STEAM_HEADER_IMAGES_URL_END.getUrl());
                gameResult.add(new GameDto(dto.getAppid(), dto.getAppid(), dto.getName(), headerImgUrl));
            }
            i--;
        }
        return gameResult;
    }
}


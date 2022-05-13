package com.gameisland.services;

import com.gameisland.enums.StaticStrings;
import com.gameisland.models.dto.SteamGameDetailsDto;
import com.google.gson.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Iterator;
import java.util.Objects;

@Service
public class SteamApiDetailService {
    private final String steamUrl = StaticStrings.STEAM_GAME_DETAILS_URL.getUrl();
    private RestTemplate template = new RestTemplate();


    protected SteamGameDetailsDto getGameDetailsByAppId(Long appid) {
        ResponseEntity<String> response = template.getForEntity(steamUrl.concat(appid.toString()), String.class);

        JsonObject responseBody = JsonParser.parseString(Objects.requireNonNull(response.getBody())).getAsJsonObject();
        JsonObject responseBodyGameAppIdObject = responseBody.getAsJsonObject(appid.toString());
        //todo success false

        JsonObject responseBodyDataObjectWithinGameAppIdObject = responseBodyGameAppIdObject.getAsJsonObject("data");

        SteamGameDetailsDto dto = new Gson().fromJson(responseBodyDataObjectWithinGameAppIdObject.getAsJsonObject(), SteamGameDetailsDto.class);
/*
        JsonArray responseBodyScreenshotsObjectWithinDataObject = responseBodyDataObjectWithinGameAppIdObject.getAsJsonArray("screenshots");

        Iterator<JsonElement> screenShotsObject = responseBodyScreenshotsObjectWithinDataObject.iterator();
        while (screenShotsObject.hasNext()) {
            JsonElement temp = screenShotsObject.next();
            String urlPath = temp.getAsJsonObject().get("path_full").getAsString();
            Long pictureId = temp.getAsJsonObject().get("id").getAsLong();
            dto.getPictures().put(pictureId, urlPath);

        }
  */
        return dto;
    }
}


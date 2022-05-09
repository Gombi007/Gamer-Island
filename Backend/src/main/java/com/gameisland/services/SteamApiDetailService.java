package com.gameisland.services;

import com.gameisland.models.dto.SteamGameDetailsDto;
import com.google.gson.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Iterator;
import java.util.Objects;

@Service
public class SteamApiDetailService {
    private String steamUrl = "https://store.steampowered.com/api/appdetails/?appids=";

    private RestTemplate template = new RestTemplate();

    protected SteamGameDetailsDto getGameDetailsByAppId(Long appid) {
        ResponseEntity<String> response = template.getForEntity(steamUrl.concat(appid.toString()), String.class);

        JsonObject body = JsonParser.parseString(Objects.requireNonNull(response.getBody())).getAsJsonObject();
        JsonObject gameAppId = body.getAsJsonObject(appid.toString());
        //todo success false
        JsonObject data = gameAppId.getAsJsonObject("data");

        SteamGameDetailsDto dto = new Gson().fromJson(data.getAsJsonObject(), SteamGameDetailsDto.class);


        JsonArray screenshots = gameAppId.getAsJsonObject("data").getAsJsonArray("screenshots");
        Iterator<JsonElement> iterator = screenshots.iterator();
        while (iterator.hasNext()) {
            JsonElement temp = iterator.next();
            String urlPath = temp.getAsJsonObject().get("path_full").getAsString();
            Long pictureId = temp.getAsJsonObject().get("id").getAsLong();
            dto.getPictures().put(pictureId, urlPath);
        }
        System.out.println(dto);

        return dto;
    }
}


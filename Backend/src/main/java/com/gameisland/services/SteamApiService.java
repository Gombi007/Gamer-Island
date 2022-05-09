package com.gameisland.services;

import com.gameisland.models.dto.GameDto;
import com.gameisland.models.dto.SteamDto;
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
public class SteamApiService {
    // get all details from game
    //  private String steamUrl = "https://store.steampowered.com/api/appdetails/?appids=1091500";

    private String steamUrl = "https://api.steampowered.com/ISteamApps/GetAppList/v2/";
    private RestTemplate template = new RestTemplate();

    protected ArrayList<GameDto> getAllGameFromSteam() {
        ArrayList<GameDto> gameResult = new ArrayList<>();
        ResponseEntity<String> response = template.getForEntity(steamUrl, String.class);

        JsonObject body = JsonParser.parseString(Objects.requireNonNull(response.getBody())).getAsJsonObject();
        JsonObject applist = body.getAsJsonObject("applist");
        JsonElement apps = applist.get("apps");

        Iterator<JsonElement> iterator = apps.getAsJsonArray().iterator();
        int i = 1000;
        while (iterator.hasNext() && i > 0) {
            SteamDto dto = new Gson().fromJson(iterator.next(), SteamDto.class);
            gameResult.add(new GameDto(dto.getAppid(), dto.getAppid(), dto.getName()));
            i--;
        }

        return gameResult;
    }
}


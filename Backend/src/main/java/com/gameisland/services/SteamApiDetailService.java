package com.gameisland.services;

import com.gameisland.enums.StaticStrings;
import com.gameisland.models.dto.SteamGameDetailsDto;
import com.gameisland.models.entities.Game;
import com.gameisland.models.entities.GamePlatform;
import com.gameisland.models.entities.GamePrice;
import com.gameisland.models.entities.GameScreenshot;
import com.gameisland.repositories.FileDB;
import com.google.gson.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

@Service
public class SteamApiDetailService {
    private final String steamUrl = StaticStrings.STEAM_GAME_DETAILS_URL.getUrl();
    private RestTemplate template = new RestTemplate();

    protected boolean checkGameIsSuccessByAppId(Long appid) {
        ResponseEntity<String> response = template.getForEntity(steamUrl.concat(appid.toString()), String.class);
        JsonObject responseBody = JsonParser.parseString(Objects.requireNonNull(response.getBody())).getAsJsonObject();
        JsonObject responseBodyGameAppIdObject = responseBody.getAsJsonObject(appid.toString());
        JsonPrimitive gameIsSuccessOrNot = responseBodyGameAppIdObject.getAsJsonPrimitive("success");
        boolean result;
        if (gameIsSuccessOrNot.toString().equals("true")) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    public Game saveAGameIntoTheDatabase(Long appid) {
        ResponseEntity<String> response = template.getForEntity(steamUrl.concat(appid.toString()), String.class);
        JsonObject responseBody = JsonParser.parseString(Objects.requireNonNull(response.getBody())).getAsJsonObject();

        JsonObject responseBodyGameAppIdObject = responseBody.getAsJsonObject(appid.toString());
        String successField = responseBodyGameAppIdObject.getAsJsonPrimitive("success").getAsString();

        if (successField.equals("true")) {
            Boolean success = responseBodyGameAppIdObject.getAsJsonPrimitive("success").getAsBoolean();
            JsonObject gameData = responseBodyGameAppIdObject.getAsJsonObject("data");
            Boolean isSoundtrack = gameData.getAsJsonPrimitive("name").getAsString().toLowerCase(Locale.ROOT).contains("soundtrack");
            Boolean isBeta = gameData.getAsJsonPrimitive("name").getAsString().toLowerCase(Locale.ROOT).contains("beta");
            if (!isSoundtrack && !isBeta) {

                JsonObject gameDataPrice = responseBodyGameAppIdObject.getAsJsonObject("data").getAsJsonObject("price_overview");
                JsonObject gameDataPlatform = responseBodyGameAppIdObject.getAsJsonObject("data").getAsJsonObject("platforms");

                String developers = "";
                String publishers = "";
                JsonArray gameDataDevelopers = responseBodyGameAppIdObject.getAsJsonObject("data").getAsJsonArray("developers");
                JsonArray gameDataPublishers = responseBodyGameAppIdObject.getAsJsonObject("data").getAsJsonArray("publishers");

                if (gameDataDevelopers != null) {

                    for (int i = 0; i < gameDataDevelopers.size(); i++) {
                        developers += gameDataDevelopers.get(i).getAsString() + ",";
                    }
                }
                if (gameDataPublishers != null) {
                    for (int i = 0; i < gameDataPublishers.size(); i++) {
                        publishers += gameDataPublishers.get(i).getAsString() + ",";

                    }
                }
                Set<GameScreenshot> screenshots = new HashSet<>();
                JsonArray gameDataScreenshots = responseBodyGameAppIdObject.getAsJsonObject("data").getAsJsonArray("screenshots");
                if (gameDataScreenshots != null) {

                    for (int i = 0; i < gameDataScreenshots.size(); i++) {
                        JsonObject screenshotObject = gameDataScreenshots.get(i).getAsJsonObject();
                        String pathThumbnail = screenshotObject.get("path_thumbnail").getAsString();
                        String pathFull = screenshotObject.get("path_full").getAsString();
                        screenshots.add(new GameScreenshot(appid, pathThumbnail, pathFull));
                    }
                }
                GamePrice gamePrice = new GamePrice();
                if (gameDataPrice != null) {
                    gamePrice = new GamePrice(
                            appid,
                            gameDataPrice.getAsJsonPrimitive("currency").getAsString(),
                            gameDataPrice.getAsJsonPrimitive("initial").getAsString(),
                            gameDataPrice.getAsJsonPrimitive("final").getAsString(),
                            gameDataPrice.getAsJsonPrimitive("discount_percent").getAsString(),
                            gameDataPrice.getAsJsonPrimitive("initial_formatted").getAsString(),
                            gameDataPrice.getAsJsonPrimitive("final_formatted").getAsString()
                    );
                }
                String website = "";
                String supportedLanguages = "";
                Boolean isNullWebsite = gameData.get("website") instanceof JsonNull;
                Boolean isNullLanguages = gameData.get("supported_languages") instanceof JsonNull;

                if (!isNullWebsite && gameData.getAsJsonPrimitive("website") != null) {
                    website = gameData.getAsJsonPrimitive("website").getAsString();

                }
                if (!isNullLanguages && gameData.getAsJsonPrimitive("supported_languages") != null) {
                    supportedLanguages = gameData.getAsJsonPrimitive("supported_languages").getAsString();

                }

                GamePlatform gamePlatform = new GamePlatform(
                        appid,
                        gameDataPlatform.getAsJsonPrimitive("windows").getAsBoolean(),
                        gameDataPlatform.getAsJsonPrimitive("mac").getAsBoolean(),
                        gameDataPlatform.getAsJsonPrimitive("linux").getAsBoolean()
                );

                Game game = new Game(
                        appid,
                        success,
                        gameData.getAsJsonPrimitive("name").getAsString(),
                        gameData.getAsJsonPrimitive("required_age").getAsString(),
                        gameData.getAsJsonPrimitive("is_free").getAsBoolean(),
                        gameData.getAsJsonPrimitive("detailed_description").getAsString(),
                        gameData.getAsJsonPrimitive("about_the_game").getAsString(),
                        gameData.getAsJsonPrimitive("short_description").getAsString(),
                        supportedLanguages,
                        gameData.getAsJsonPrimitive("header_image").getAsString(),
                        website,
                        developers, publishers, gamePrice, gamePlatform, screenshots
                );

                return game;
            }
        }
        return null;
    }
}

/*
   GamePrice gamePrice = new GamePrice(1091500l, "Eur", "5999", "5999", "0", "", "59,99€");
        Game game = new Game(1091500l, true, "Cyberpunk 2077", "18", false, "Detail desciption", "This is a super game", "Short desc", "Englis, Hungary", "image url", "cp2077", gamePrice);
        Game game2 = new Game(1091500l, true, "Cyberpunk 2077", "18", false, "Detail desciption", "This is a super game", "Short desc", "Englis, Hungary", "image url", "cp2077", gamePrice);
        gameRepository.save(game);
        gameRepository.save(game2);
        Game gameById = gameRepository.findById(2l).get();
        Gson gson = new Gson();
*/


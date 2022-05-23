package com.gameisland.services;

import com.gameisland.enums.StaticStrings;
import com.gameisland.exceptions.GameDetailsAreNotSuccessException;
import com.gameisland.exceptions.ResourceNotFoundException;
import com.gameisland.models.entities.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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

    private JsonObject getGameDetailsIfThatIsSuccess(Long appid) throws Exception {
        JsonObject resultDataObject;
        JsonObject responseBodyGameAppIdObject;

        try {
            ResponseEntity<String> response = template.getForEntity(steamUrl.concat(appid.toString()), String.class);
            JsonObject responseBody = JsonParser.parseString(Objects.requireNonNull(response.getBody())).getAsJsonObject();
            responseBodyGameAppIdObject = responseBody.getAsJsonObject(appid.toString());
        } catch (Exception exception) {
            throw new ResourceNotFoundException("Steam API is not responding");

        }

        Boolean successField = responseBodyGameAppIdObject.getAsJsonPrimitive("success").getAsBoolean();
        if (successField) {
            resultDataObject = responseBodyGameAppIdObject.getAsJsonObject("data");
            return resultDataObject;
        }
        throw new GameDetailsAreNotSuccessException("Game is not done yet");
    }

    public Game saveAGameIntoTheDatabase(Long appid) {
        boolean isSoundtrack = true;
        boolean isBeta = true;
        JsonObject gameData = null;

        try {
            gameData = getGameDetailsIfThatIsSuccess(appid);
            isSoundtrack = gameData.getAsJsonPrimitive("name").getAsString().toLowerCase(Locale.ROOT).contains("soundtrack");
            isBeta = gameData.getAsJsonPrimitive("name").getAsString().toLowerCase(Locale.ROOT).contains("beta");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        if (!isSoundtrack && !isBeta && gameData != null) {

            String developers = "";
            JsonArray gameDataDevelopers = gameData.getAsJsonArray("developers");
            if (gameDataDevelopers != null) {

                for (int i = 0; i < gameDataDevelopers.size(); i++) {
                    developers += gameDataDevelopers.get(i).getAsString() + ",";
                }
            }

            String publishers = "";
            JsonArray gameDataPublishers = gameData.getAsJsonArray("publishers");
            if (gameDataPublishers != null) {
                for (int i = 0; i < gameDataPublishers.size(); i++) {
                    publishers += gameDataPublishers.get(i).getAsString() + ",";

                }
            }
            Set<GameScreenshot> screenshots = new HashSet<>();
            JsonArray gameDataScreenshots = gameData.getAsJsonArray("screenshots");
            if (gameDataScreenshots != null) {

                for (int i = 0; i < gameDataScreenshots.size(); i++) {
                    JsonObject screenshotObject = gameDataScreenshots.get(i).getAsJsonObject();
                    String pathThumbnail = screenshotObject.get("path_thumbnail").getAsString();
                    String pathFull = screenshotObject.get("path_full").getAsString();
                    screenshots.add(new GameScreenshot(appid, pathThumbnail, pathFull));
                }
            }

            Set<GameGenre> genres = new HashSet<>();
            JsonArray gameDataGenres = gameData.getAsJsonArray("genres");
            if (gameDataGenres != null) {
                for (int i = 0; i < gameDataGenres.size(); i++) {
                    JsonObject genreObject = gameDataGenres.get(i).getAsJsonObject();
                    String description = genreObject.get("description").getAsString();
                    genres.add(new GameGenre(appid, description));
                }
            }


            JsonObject gameDataPrice = gameData.getAsJsonObject("price_overview");
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

            JsonObject gameDataMetacritic = gameData.getAsJsonObject("metacritic");
            GameMetacritic gameMetacritic = new GameMetacritic();
            if (gameDataMetacritic != null) {
                gameMetacritic = new GameMetacritic(
                        appid,
                        gameDataMetacritic.getAsJsonPrimitive("score").getAsString(),
                        gameDataMetacritic.getAsJsonPrimitive("urls").getAsString());
            }

            String website = "";
            boolean isNullWebsite = gameData.get("website") instanceof JsonNull;
            if (!isNullWebsite && gameData.getAsJsonPrimitive("website") != null) {
                website = gameData.getAsJsonPrimitive("website").getAsString();
            }

            String supportedLanguages = "";
            boolean isNullLanguages = gameData.get("supported_languages") instanceof JsonNull;
            if (!isNullLanguages && gameData.getAsJsonPrimitive("supported_languages") != null) {
                supportedLanguages = gameData.getAsJsonPrimitive("supported_languages").getAsString();
            }

            JsonObject gameDataPlatform = gameData.getAsJsonObject("platforms");
            GamePlatform gamePlatform = new GamePlatform(
                    appid,
                    gameDataPlatform.getAsJsonPrimitive("windows").getAsBoolean(),
                    gameDataPlatform.getAsJsonPrimitive("mac").getAsBoolean(),
                    gameDataPlatform.getAsJsonPrimitive("linux").getAsBoolean()
            );

            Game game = new Game(
                    appid,
                    true,
                    gameData.getAsJsonPrimitive("name").getAsString(),
                    gameData.getAsJsonPrimitive("required_age").getAsString(),
                    gameData.getAsJsonPrimitive("is_free").getAsBoolean(),
                    gameData.getAsJsonPrimitive("detailed_description").getAsString(),
                    gameData.getAsJsonPrimitive("about_the_game").getAsString(),
                    gameData.getAsJsonPrimitive("short_description").getAsString(),
                    supportedLanguages,
                    gameData.getAsJsonPrimitive("header_image").getAsString(),
                    website,
                    developers, publishers, gamePrice, gamePlatform,gameMetacritic, screenshots, genres
            );

            return game;
        }
        return null;
    }

}


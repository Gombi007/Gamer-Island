package com.gameisland.services;

import com.gameisland.enums.StaticStrings;
import com.gameisland.exceptions.GameDetailsAreNotSuccessException;
import com.gameisland.exceptions.ResourceNotFoundException;
import com.gameisland.exceptions.SteamApiNotRespondingException;
import com.gameisland.models.entities.SteamGame;
import com.gameisland.repositories.FileDB;
import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Locale;
import java.util.Objects;

@Slf4j
@Service
public class SteamApiDetailService {
    private final String steamUrl = StaticStrings.STEAM_GAME_DETAILS_URL.getUrl();
    private RestTemplate template = new RestTemplate();
    private FileDB fileDB = new FileDB();

    private JsonObject getGameDetailsIfThatIsSuccess(Long appid) {
        JsonObject resultDataObject;
        JsonObject responseBodyGameAppIdObject;


        try {
            ResponseEntity<String> response = template.getForEntity(steamUrl.concat(appid.toString()), String.class);
            JsonObject responseBody = JsonParser.parseString(Objects.requireNonNull(response.getBody())).getAsJsonObject();
            responseBodyGameAppIdObject = responseBody.getAsJsonObject(appid.toString());
        } catch (Exception exception) {
            throw new ResourceNotFoundException("Steam API is not responding " + exception.getMessage());

        }

        Boolean successField = responseBodyGameAppIdObject.getAsJsonPrimitive("success").getAsBoolean();
        if (successField) {
            resultDataObject = responseBodyGameAppIdObject.getAsJsonObject("data");
            return resultDataObject;
        } else {
            //collect all unsuccess  appid
            Long unSuccessAppId = responseBodyGameAppIdObject.getAsJsonObject("data").getAsJsonPrimitive("steam_appid").getAsLong();
            fileDB.CollectAllUnSuccessAndNonGameApp(unSuccessAppId);
        }
        throw new GameDetailsAreNotSuccessException("Game is not done yet");
    }

    public SteamGame saveSteamGamesIntoTheDatabase(Long appid) {

        boolean onlyGames = false;
        JsonObject gameData = null;
        boolean isSoundtrack = true;
        boolean isBeta = true;
        boolean isPlayTest = true;
        boolean isAdultGame = true;
        boolean isHighPriceGame = false;
        boolean isFreeGame = false;
        boolean freeOrHighPrice = false;

        try {
            gameData = getGameDetailsIfThatIsSuccess(appid);
            onlyGames = gameData.getAsJsonPrimitive("type").getAsString().equalsIgnoreCase("game");
            isSoundtrack = gameData.getAsJsonPrimitive("name").getAsString().toLowerCase(Locale.ROOT).contains("soundtrack");
            isBeta = gameData.getAsJsonPrimitive("name").getAsString().toLowerCase(Locale.ROOT).contains("beta");
            isPlayTest = gameData.getAsJsonPrimitive("name").getAsString().toLowerCase(Locale.ROOT).contains("playtest");
            isAdultGame = gameData.getAsJsonPrimitive("required_age").getAsString().toLowerCase(Locale.ROOT).contains("18");
            isFreeGame = gameData.getAsJsonPrimitive("is_free").getAsBoolean();

            JsonObject gameDataPrice = gameData.getAsJsonObject("price_overview");
            String priceAbove40 = gameDataPrice.getAsJsonPrimitive("final_formatted").getAsString();
            String[] tmp = priceAbove40.split(",");
            Integer priceInNumber = Integer.parseInt(tmp[0]);

            if (priceInNumber > 3 || isFreeGame) {
                freeOrHighPrice = true;
            }
            // log.error("Filter game: gameData:{} onlyGames:{} isSoundtrack:{} isBeta:{} isPlayTest:{} isAdultGame:{} priceAbove30:{}", gameData.isJsonObject(), onlyGames, isSoundtrack, isBeta, isPlayTest, isAdultGame, isHighPriceGame);

        } catch (Exception exception) {
            // Game is not success
            if (exception.getMessage().contains("Steam API is not responding 429 Too Many Requests")) {
               // log.error("Error during game saving: {}", exception.getMessage());
                throw new SteamApiNotRespondingException(exception.getMessage());
            }
        }
        if (gameData != null && onlyGames && freeOrHighPrice && !isSoundtrack && !isBeta && !isPlayTest && !isAdultGame) {

            Long steamAppId = appid;
            Boolean success = true;
            String name = gameData.getAsJsonPrimitive("name").getAsString();
            String requiredAge = gameData.getAsJsonPrimitive("required_age").getAsString();
            Boolean isFree = gameData.getAsJsonPrimitive("is_free").getAsBoolean();
            String headerImage = gameData.getAsJsonPrimitive("header_image").getAsString();
            String website = ""; //ok
            String price = ""; //ok
            String developers = ""; //ok
            String publishers = ""; //ok
            String platforms = ""; //ok
            String screenshots = ""; //ok
            String genres = ""; //ok
            String detailedDescription = ""; //ok
            String aboutTheGame = ""; //ok
            String shortDescription = gameData.getAsJsonPrimitive("short_description").getAsString();
            String supportedLanguages = ""; //ok;
            String metacritic = ""; //ok;

            //website
            boolean isNullWebsiteJson = gameData.get("website") instanceof JsonNull;
            if (!isNullWebsiteJson) {
                website = gameData.getAsJsonPrimitive("website").getAsString();
            }

            //price
            JsonObject gameDataPrice = gameData.getAsJsonObject("price_overview");
            if (gameDataPrice != null) {
                price = gameDataPrice.getAsJsonPrimitive("final_formatted").getAsString();
            }

            //developers
            JsonArray gameDataDevelopers = gameData.getAsJsonArray("developers");
            if (gameDataDevelopers != null) {
                for (int i = 0; i < gameDataDevelopers.size(); i++) {
                    developers += gameDataDevelopers.get(i).getAsString() + ";";
                }
            }

            //publishers
            JsonArray gameDataPublishers = gameData.getAsJsonArray("publishers");
            if (gameDataPublishers != null) {
                for (int i = 0; i < gameDataPublishers.size(); i++) {
                    publishers += gameDataPublishers.get(i).getAsString() + ";";
                }
            }

            //platforms
            JsonObject gameDataPlatform = gameData.getAsJsonObject("platforms");
            boolean isRunningOnWindows = gameDataPlatform.getAsJsonPrimitive("windows").getAsBoolean();
            boolean isRunningOnMac = gameDataPlatform.getAsJsonPrimitive("mac").getAsBoolean();
            boolean isRunningOnLinux = gameDataPlatform.getAsJsonPrimitive("linux").getAsBoolean();
            if (isRunningOnWindows) {
                platforms += "Windows ";
            }
            if (isRunningOnMac) {
                platforms += "Mac ";
            }
            if (isRunningOnLinux) {
                platforms += "Linux ";
            }

            //screenshots
            JsonArray gameDataScreenshots = gameData.getAsJsonArray("screenshots");
            if (gameDataScreenshots != null) {
                for (int i = 0; i < gameDataScreenshots.size(); i++) {
                    JsonObject screenshotObject = gameDataScreenshots.get(i).getAsJsonObject();
                    screenshots += screenshotObject.get("path_full").getAsString() + ";";
                }
            }

            //genres
            JsonArray gameDataGenres = gameData.getAsJsonArray("genres");
            if (gameDataGenres != null) {
                for (int i = 0; i < gameDataGenres.size(); i++) {
                    JsonObject genreObject = gameDataGenres.get(i).getAsJsonObject();
                    genres += genreObject.get("description").getAsString() + ";";
                }
            }

            //detailed description
            int maxLengthText = 10000;
            detailedDescription = gameData.getAsJsonPrimitive("detailed_description").getAsString();
            if (detailedDescription.length() >= maxLengthText) {
                detailedDescription = detailedDescription.substring(0, maxLengthText);
            }

            //about the game
            aboutTheGame = gameData.getAsJsonPrimitive("about_the_game").getAsString();
            if (aboutTheGame.length() >= maxLengthText) {
                aboutTheGame = aboutTheGame.substring(0, maxLengthText);
            }

            //supported languages
            boolean isNullLanguagesJson = gameData.get("supported_languages") instanceof JsonNull;
            boolean isNullLanguagesString = gameData.get("supported_languages") == null;
            if (!isNullLanguagesJson && !isNullLanguagesString) {
                supportedLanguages = gameData.getAsJsonPrimitive("supported_languages").getAsString();
            }

            //metacritic
            JsonObject gameDataMetacritic = gameData.getAsJsonObject("metacritic");
            if (gameDataMetacritic != null) {
                metacritic += gameDataMetacritic.getAsJsonPrimitive("score").getAsString();
                metacritic += ";";
                metacritic += gameDataMetacritic.getAsJsonPrimitive("url").getAsString();
            }

            SteamGame steamGame = new SteamGame(
                    steamAppId,
                    success,
                    name,
                    requiredAge,
                    isFree,
                    headerImage,
                    website,
                    price,
                    developers,
                    publishers,
                    platforms,
                    genres,
                    metacritic,
                    screenshots,
                    detailedDescription,
                    aboutTheGame,
                    shortDescription,
                    supportedLanguages);
            return steamGame;

        } else {
            //Collect all non-game app.
            fileDB.CollectAllUnSuccessAndNonGameApp(appid);
        }
        return null;
    }

}


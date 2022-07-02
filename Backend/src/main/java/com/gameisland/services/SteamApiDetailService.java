package com.gameisland.services;

import com.gameisland.enums.StaticStrings;
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

import java.util.ArrayList;
import java.util.List;
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
        Boolean successField = false;


        try {
            ResponseEntity<String> response = template.getForEntity(steamUrl.concat(appid.toString()), String.class);
            JsonObject responseBody = JsonParser.parseString(Objects.requireNonNull(response.getBody())).getAsJsonObject();
            responseBodyGameAppIdObject = responseBody.getAsJsonObject(appid.toString());
            successField = responseBodyGameAppIdObject.getAsJsonPrimitive("success").getAsBoolean();
            if (successField) {
                resultDataObject = responseBodyGameAppIdObject.getAsJsonObject("data");
                return resultDataObject;
            } else {
                //collect all unsuccessful  appid
                fileDB.CollectAllUnSuccessAndNonGameApp(appid);
            }
        } catch (Exception exception) {
            if (exception.getMessage() != null && exception.getMessage().contains("429 Too Many Requests")) {
                log.error("STEAM API error: {}", exception.getMessage());
                throw new SteamApiNotRespondingException(exception.getMessage());
            }

            log.error("Error in game details is success method: {}", exception.getMessage());

        }
        return null;
    }


    private boolean customGameFilter(JsonObject gameData, Long appid) {
        boolean isSoundtrack = true;
        boolean isBeta = true;
        boolean isPlayTest = true;
        boolean onlyGames = false;
        boolean isFreeGame = false;
        boolean freeOrHighPrice = false;
        boolean isAdultGame = false;

        try {
            String gameType = gameData.getAsJsonPrimitive("type").getAsString();
            String gameName = gameData.getAsJsonPrimitive("name").getAsString().toLowerCase(Locale.ROOT);
            String gameDetailedDesc = gameData.getAsJsonPrimitive("detailed_description").getAsString().toLowerCase(Locale.ROOT);
            String gameShortDesc = gameData.getAsJsonPrimitive("short_description").getAsString().toLowerCase(Locale.ROOT);


            //publisher section currently just one
            JsonArray gameDataPublishers = gameData.getAsJsonArray("publishers");
            if (gameDataPublishers != null) {
                String publisherBan = "wasabi";
                for (int i = 0; i < gameDataPublishers.size(); i++) {
                    if (gameDataPublishers.get(i).getAsString().toLowerCase(Locale.ROOT).contains(publisherBan)) {
                        isAdultGame = true;
                    }
                }
            }

            //basic field section
            onlyGames = gameType.equalsIgnoreCase("game");
            isSoundtrack = gameName.contains("soundtrack");
            isBeta = gameName.contains("beta");
            isPlayTest = gameName.contains("playtest");
            isFreeGame = gameData.getAsJsonPrimitive("is_free").getAsBoolean();

            //adult section
            List<String> adultTags = new ArrayList<>();
            JsonObject adultJsonObject = fileDB.getAdultFilterJsonObject();
            JsonArray adultJsonArrayWithTags = adultJsonObject.getAsJsonArray("adult");
            adultJsonArrayWithTags.forEach(tag -> adultTags.add(tag.getAsString()));

            for (int i = 0; i < adultTags.size(); i++) {
                if (gameName.contains(adultTags.get(i)) || gameShortDesc.contains(adultTags.get(i)) || gameDetailedDesc.contains(adultTags.get(i))) {
                    //      log.error("ADULT: {} APPID: {} TAG: {}", gameName, appid, adultTags.get(i));
                    isAdultGame = true;
                    break;
                }
            }

            //price limit or free game section
            String priceString = "";
            double priceInDouble = 0.0;
            JsonObject dataPrice = gameData.getAsJsonObject("price_overview");
            if (dataPrice != null) {
                priceString = dataPrice.getAsJsonPrimitive("final_formatted").getAsString();
            }

            if (priceString.length() >= 3 && priceString.contains(",")) {
                priceString = priceString.replace(",", ".");
                priceString = priceString.replace("€", "");
            }

            if (!priceString.isEmpty()) {
                priceInDouble = Double.parseDouble(priceString);
                if (priceInDouble > 19.00) {
                    freeOrHighPrice = true;
                }
            }
/*
            if (isFreeGame) {
                freeOrHighPrice = true;
            }
*/
            //summary
            if (onlyGames && freeOrHighPrice && !isAdultGame && !isBeta && !isPlayTest && !isSoundtrack) {
                return true;
            }
            return false;


        } catch (Exception exception) {
            // Too many request from steam API (max about 160 request in every 6 min )
            if (exception.getMessage() != null && exception.getMessage().contains("Steam API is not responding 429 Too Many Requests")) {
                log.error("Error in custom game filter: {}", exception.getMessage());
                throw new SteamApiNotRespondingException(exception.getMessage());
            }

            if (exception.getMessage() != null && !exception.getMessage().contains("Steam API is not responding 429 Too Many Requests")) {
                log.error("Error in custom game filter: {}", exception.getMessage());
            }

            if (exception.getMessage() == null) {
                log.error("Error in in custom game filter, no exception message...");
            }
        }
        return false;
    }

    public SteamGame saveSteamGamesIntoTheDatabase(Long appid) {
        JsonObject gameData = getGameDetailsIfThatIsSuccess(appid);
        boolean customGameFilter = false;

        if (gameData != null) {
            customGameFilter = customGameFilter(gameData, appid);
        }

        if (gameData != null && customGameFilter) {
            Long steamAppId = appid;
            Boolean success = true;
            String name = gameData.getAsJsonPrimitive("name").getAsString();
            String requiredAge = gameData.getAsJsonPrimitive("required_age").getAsString();
            Boolean isFree = gameData.getAsJsonPrimitive("is_free").getAsBoolean();
            String headerImage = gameData.getAsJsonPrimitive("header_image").getAsString();
            String website = ""; //ok
            Double price = 0.0; //ok
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
            String priceWithDot = "";
            if (gameDataPrice != null) {
                priceWithDot = gameDataPrice.getAsJsonPrimitive("final_formatted").getAsString();
            }
            if (!priceWithDot.isEmpty() && priceWithDot.contains(",")) {
                priceWithDot = priceWithDot.replace(",", ".");
                priceWithDot = priceWithDot.replace("€", "");
            }
            try {
                if (!priceWithDot.isEmpty()) {
                    price = Double.parseDouble(priceWithDot);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }


            //developers
            JsonArray gameDataDevelopers = gameData.getAsJsonArray("developers");
            if (gameDataDevelopers != null) {
                for (int i = 0; i < gameDataDevelopers.size(); i++) {
                    developers += gameDataDevelopers.get(i).getAsString() + ";";
                    if (developers.length() > 180) {
                        break;
                    }
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


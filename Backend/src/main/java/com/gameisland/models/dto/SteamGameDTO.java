package com.gameisland.models.dto;

import com.gameisland.models.entities.SteamGame;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SteamGameDTO {
    private Long id;
    private Long steam_appid;
    private Boolean success;
    private String name;
    private String required_age;
    private Boolean is_free;
    private String detailed_description;
    private String about_the_game;
    private String short_description;
    private String supported_languages;
    private String header_image;
    private String website;
    private String developers;
    private String publishers;
    private Double price_in_final_formatted;
    private String platforms;
    private String metacritic;
    private List<String> screenshot_urls;
    private List<String> genres;


    public static SteamGameDTO convertToGameDto(SteamGame game) {
        //genres
        String genres = game.getGenres();
        List<String> genresList = new ArrayList<>();
        if (genres != null && !genres.isEmpty()) {
            String[] tmp = genres.split(";");
            for (int i = 0; i < tmp.length; i++) {
                genresList.add(tmp[i]);
            }
        }
        //screenshots
        String screenshots = game.getScreenshots();
        List<String> screenshotsList = new ArrayList<>();
        if (screenshots != null && !genres.isEmpty()) {
            String[] tmp = screenshots.split(";");
            for (int i = 0; i < tmp.length; i++) {
                screenshotsList.add(tmp[i]);
            }
        }

        //price
        String priceWithDot = "";
        if (!game.getPrice().isEmpty() && game.getPrice().contains(",")) {
            priceWithDot = game.getPrice().replace(",", ".");
            priceWithDot = priceWithDot.replace("€", "");
        }

        Double price = 0.0;
        try {
            if (!priceWithDot.isEmpty()) {
                price = Double.parseDouble(priceWithDot);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }


        SteamGameDTO dto = new SteamGameDTO(
                game.getId(),
                game.getSteamAppId(),
                game.getSuccess(),
                game.getName(),
                game.getRequiredAge(),
                game.getIsFree(),
                game.getDetailedDescription(),
                game.getAboutTheGame(),
                game.getShortDescription(),
                game.getSupportedLanguages(),
                game.getHeaderImage(),
                game.getWebsite(),
                game.getDevelopers(),
                game.getPublishers(),
                price,
                game.getPlatforms(),
                game.getMetacritic(),
                screenshotsList,
                genresList
        );
        return dto;
    }

}

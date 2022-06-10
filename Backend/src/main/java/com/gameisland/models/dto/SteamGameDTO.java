package com.gameisland.models.dto;

import com.gameisland.models.entities.SteamGame;

import java.util.ArrayList;
import java.util.List;

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
    private String price_in_final_formatted;
    private String platforms;
    private String metacritic;
    private List<String> screenshot_urls;
    private List<String> genres;

    public SteamGameDTO() {
    }

    public SteamGameDTO(Long id, Long steam_appid, Boolean success, String name, String required_age, Boolean is_free, String detailed_description, String about_the_game, String short_description, String supported_languages, String header_image, String website, String developers, String publishers, String price_in_final_formatted, String platforms, String metacritic, List<String> screenshot_urls, List<String> genres) {
        this.id = id;
        this.steam_appid = steam_appid;
        this.success = success;
        this.name = name;
        this.required_age = required_age;
        this.is_free = is_free;
        this.detailed_description = detailed_description;
        this.about_the_game = about_the_game;
        this.short_description = short_description;
        this.supported_languages = supported_languages;
        this.header_image = header_image;
        this.website = website;
        this.developers = developers;
        this.publishers = publishers;
        this.price_in_final_formatted = price_in_final_formatted;
        this.platforms = platforms;
        this.metacritic = metacritic;
        this.screenshot_urls = screenshot_urls;
        this.genres = genres;
    }

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

        SteamGameDTO dto = new SteamGameDTO(
                game.getId(),
                game.getSteamAppId(),
                game.getSuccess(),
                game.getName(),
                game.getRequiredAge(),
                game.getFree(),
                game.getDetailedDescription(),
                game.getAboutTheGame(),
                game.getShortDescription(),
                game.getSupportedLanguages(),
                game.getHeaderImage(),
                game.getWebsite(),
                game.getDevelopers(),
                game.getPublishers(),
                game.getPrice(),
                game.getPlatforms(),
                game.getMetacritic(),
                screenshotsList,
                genresList
        );
        return dto;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSteam_appid() {
        return steam_appid;
    }

    public void setSteam_appid(Long steam_appid) {
        this.steam_appid = steam_appid;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRequired_age() {
        return required_age;
    }

    public void setRequired_age(String required_age) {
        this.required_age = required_age;
    }

    public Boolean getIs_free() {
        return is_free;
    }

    public void setIs_free(Boolean is_free) {
        this.is_free = is_free;
    }

    public String getDetailed_description() {
        return detailed_description;
    }

    public void setDetailed_description(String detailed_description) {
        this.detailed_description = detailed_description;
    }

    public String getAbout_the_game() {
        return about_the_game;
    }

    public void setAbout_the_game(String about_the_game) {
        this.about_the_game = about_the_game;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public String getSupported_languages() {
        return supported_languages;
    }

    public void setSupported_languages(String supported_languages) {
        this.supported_languages = supported_languages;
    }

    public String getHeader_image() {
        return header_image;
    }

    public void setHeader_image(String header_image) {
        this.header_image = header_image;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDevelopers() {
        return developers;
    }

    public void setDevelopers(String developers) {
        this.developers = developers;
    }

    public String getPublishers() {
        return publishers;
    }

    public void setPublishers(String publishers) {
        this.publishers = publishers;
    }

    public String getPrice_in_final_formatted() {
        return price_in_final_formatted;
    }

    public void setPrice_in_final_formatted(String price_in_final_formatted) {
        this.price_in_final_formatted = price_in_final_formatted;
    }

    public String getPlatforms() {
        return platforms;
    }

    public void setPlatforms(String platforms) {
        this.platforms = platforms;
    }

    public String getMetacritic() {
        return metacritic;
    }

    public void setMetacritic(String metacritic) {
        this.metacritic = metacritic;
    }

    public List<String> getScreenshot_urls() {
        return screenshot_urls;
    }

    public void setScreenshot_urls(List<String> screenshot_urls) {
        this.screenshot_urls = screenshot_urls;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }
}

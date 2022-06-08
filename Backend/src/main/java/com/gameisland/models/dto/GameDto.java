package com.gameisland.models.dto;

import com.gameisland.models.entities.*;
import org.modelmapper.ModelMapper;

import java.util.*;

public class GameDto {
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

    private Map<String, Boolean> platforms;
    private Map<String, String> metacritics;
    private List<String> screenshot_urls;
    private List<String> genres;


    public static GameDto convertToGameDto(Game game) {
        // Platform select
        GamePlatform platform = game.getGamePlatform();
        Map<String, Boolean> platforms = new HashMap<>();
        if (platform != null) {
            platforms.put("windows", platform.getWindows());
            platforms.put("mac", platform.getMac());
            platforms.put("linux", platform.getLinux());
        }


        // Metacritic select
        GameMetacritic metacritic = game.getGameMetacritic();
        Map<String, String> metacritics = new HashMap<>();
        if (metacritic.getUrl() != null) {
            metacritics.put(metacritic.getScore(), metacritic.getUrl());
        }

        // Screenshot select
        Set<GameScreenshot> gameScreenshots = game.getGameScreenshots();
        List<String> screenshot_urls = new ArrayList<>();
        Iterator<GameScreenshot> screenshotIterator = gameScreenshots.iterator();
        while (screenshotIterator.hasNext()) {
            screenshot_urls.add(screenshotIterator.next().getPathFull());
        }

        // Genres select
        Set<GameGenre> genres_tmp = game.getGameGenres();
        List<String> genres = new ArrayList<>();
        Iterator<GameGenre> genreIterator = genres_tmp.iterator();
        while (genreIterator.hasNext()) {
            genres.add(genreIterator.next().getDescription());
        }
        return new GameDto(
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
                game.getGamePrice().getFinalFormatted(),
                platforms, metacritics, screenshot_urls, genres
        );
    }

    public static GameDto convertToGameDtoForShop(Game game) {
        // Platform select
        GamePlatform platform = game.getGamePlatform();
        Map<String, Boolean> platforms = new HashMap<>();
        if (platform != null) {
            platforms.put("windows", platform.getWindows());
            platforms.put("mac", platform.getMac());
            platforms.put("linux", platform.getLinux());
        }


        // Metacritic select
        GameMetacritic metacritic = game.getGameMetacritic();
        Map<String, String> metacritics = new HashMap<>();
        if (metacritic.getUrl() != null) {
            metacritics.put(metacritic.getScore(), metacritic.getUrl());
        }

        // Genres select
        Set<GameGenre> genres_tmp = game.getGameGenres();
        List<String> genres = new ArrayList<>();
        Iterator<GameGenre> genreIterator = genres_tmp.iterator();
        while (genreIterator.hasNext()) {
            genres.add(genreIterator.next().getDescription());
        }
        return new GameDto(
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
                game.getGamePrice().getFinalFormatted(),
                platforms, metacritics, null, genres
        );
    }

    public GameDto() {
    }

    public static GameDto convertToDtoWithModelMapper(Game game) {
        ModelMapper mapper = new ModelMapper();
        GameDto gameDto = mapper.map(game, GameDto.class);
        System.out.println(gameDto);
        return gameDto;
    }

    public GameDto(Long id, Long steam_appid, Boolean success, String name, String required_age, Boolean is_free, String detailed_description, String about_the_game, String short_description, String supported_languages, String header_image, String website, String developers, String publishers, String price_in_final_formatted, Map<String, Boolean> platforms, Map<String, String> metacritics, List<String> screenshot_urls, List<String> genres) {
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
        this.metacritics = metacritics;
        this.screenshot_urls = screenshot_urls;
        this.genres = genres;
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

    public Map<String, Boolean> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(Map<String, Boolean> platforms) {
        this.platforms = platforms;
    }

    public Map<String, String> getMetacritics() {
        return metacritics;
    }

    public void setMetacritics(Map<String, String> metacritics) {
        this.metacritics = metacritics;
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

    @Override
    public String toString() {
        return "GameDto{" +
                "id=" + id +
                ", steam_appid=" + steam_appid +
                ", success=" + success +
                ", name='" + name + '\'' +
                ", required_age='" + required_age + '\'' +
                ", is_free=" + is_free +
                ", detailed_description='" + detailed_description + '\'' +
                ", about_the_game='" + about_the_game + '\'' +
                ", short_description='" + short_description + '\'' +
                ", supported_languages='" + supported_languages + '\'' +
                ", header_image='" + header_image + '\'' +
                ", website='" + website + '\'' +
                ", developers='" + developers + '\'' +
                ", publishers='" + publishers + '\'' +
                ", price_in_final_formatted='" + price_in_final_formatted + '\'' +
                ", platforms=" + platforms +
                ", metacritics=" + metacritics +
                ", screenshot_urls=" + screenshot_urls +
                ", genres=" + genres +
                '}';
    }
}
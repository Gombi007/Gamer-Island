package com.gameisland.models.entities;

import com.gameisland.models.dto.GameLibraryDetailsDto;

import javax.persistence.*;
import java.util.Set;


@NamedNativeQuery(name = "Game.getLibraryDetails",
        query = "SELECT id as id, steam_app_id as appId, name as name, header_image as headerImage FROM games",
        resultSetMapping = "Mapping.GameLibraryDetailsDto")

@SqlResultSetMapping(name = "Mapping.GameLibraryDetailsDto",
        classes = @ConstructorResult(targetClass = GameLibraryDetailsDto.class,
                columns = {
                        @ColumnResult(name = "id", type = Long.class),
                        @ColumnResult(name = "appId", type = Long.class),
                        @ColumnResult(name = "name", type = String.class),
                        @ColumnResult(name = "headerImage", type = String.class)}))


@Entity()
@Table(name = "games")
public class Game extends BusinessObject {
    private Long steamAppId;
    private Boolean success;
    private String name;
    private String requiredAge;
    private Boolean isFree;

    @Column(length = 10000)
    private String detailedDescription;

    @Column(length = 10000)
    private String aboutTheGame;

    @Column(length = 1000)
    private String shortDescription;

    @Column(length = 1000)
    private String supportedLanguages;

    private String headerImage;
    private String website;
    private String developers;
    private String publishers;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private GamePrice gamePrice;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private GamePlatform gamePlatform;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private GameMetacritic gameMetacritic;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Set<GameScreenshot> gameScreenshots;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Set<GameGenre> gameGenres;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "ownedGames")
    private Set<User> users;


    public Game() {
    }

    public Game(Long steamAppId, Boolean success, String name, String requiredAge, Boolean isFree, String detailedDescription, String aboutTheGame, String shortDescription, String supportedLanguages, String headerImage, String website, String developers, String publishers, GamePrice gamePrice, GamePlatform gamePlatform, GameMetacritic gameMetacritic, Set<GameScreenshot> gameScreenshots, Set<GameGenre> gameGenres) {
        this.steamAppId = steamAppId;
        this.success = success;
        this.name = name;
        this.requiredAge = requiredAge;
        this.isFree = isFree;
        this.detailedDescription = detailedDescription;
        this.aboutTheGame = aboutTheGame;
        this.shortDescription = shortDescription;
        this.supportedLanguages = supportedLanguages;
        this.headerImage = headerImage;
        this.website = website;
        this.developers = developers;
        this.publishers = publishers;
        this.gamePrice = gamePrice;
        this.gamePlatform = gamePlatform;
        this.gameMetacritic = gameMetacritic;
        this.gameScreenshots = gameScreenshots;
        this.gameGenres = gameGenres;
    }

    public Long getSteamAppId() {
        return steamAppId;
    }

    public void setSteamAppId(Long steamAppId) {
        this.steamAppId = steamAppId;
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

    public String getRequiredAge() {
        return requiredAge;
    }

    public void setRequiredAge(String requiredAge) {
        this.requiredAge = requiredAge;
    }

    public Boolean getFree() {
        return isFree;
    }

    public void setFree(Boolean free) {
        isFree = free;
    }

    public String getDetailedDescription() {
        return detailedDescription;
    }

    public void setDetailedDescription(String detailedDescription) {
        this.detailedDescription = detailedDescription;
    }

    public String getAboutTheGame() {
        return aboutTheGame;
    }

    public void setAboutTheGame(String aboutTheGame) {
        this.aboutTheGame = aboutTheGame;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getSupportedLanguages() {
        return supportedLanguages;
    }

    public void setSupportedLanguages(String supportedLanguages) {
        this.supportedLanguages = supportedLanguages;
    }

    public String getHeaderImage() {
        return headerImage;
    }

    public void setHeaderImage(String headerImage) {
        this.headerImage = headerImage;
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

    public GamePrice getGamePrice() {
        return gamePrice;
    }

    public void setGamePrice(GamePrice gamePrice) {
        this.gamePrice = gamePrice;
    }

    public GamePlatform getGamePlatform() {
        return gamePlatform;
    }

    public void setGamePlatform(GamePlatform gamePlatform) {
        this.gamePlatform = gamePlatform;
    }

    public GameMetacritic getGameMetacritic() {
        return gameMetacritic;
    }

    public void setGameMetacritic(GameMetacritic gameMetacritic) {
        this.gameMetacritic = gameMetacritic;
    }

    public Set<GameScreenshot> getGameScreenshots() {
        return gameScreenshots;
    }

    public void setGameScreenshots(Set<GameScreenshot> gameScreenshots) {
        this.gameScreenshots = gameScreenshots;
    }

    public Set<GameGenre> getGameGenres() {
        return gameGenres;
    }

    public void setGameGenres(Set<GameGenre> gameGenres) {
        this.gameGenres = gameGenres;
    }
}
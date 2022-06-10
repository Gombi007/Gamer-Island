package com.gameisland.models.entities;

import com.gameisland.models.dto.GameLibraryDetailsDto;
import org.hibernate.annotations.NamedNativeQuery;

import javax.persistence.*;
import java.util.Set;

@NamedNativeQuery(name = "SteamGame.getLibraryDetails",
        query = "SELECT id as id, steam_app_id as appId, name as name, header_image as headerImage FROM steam_games",
        resultSetMapping = "Mapping.GameLibraryDetailsDto")

@SqlResultSetMapping(name = "Mapping.GameLibraryDetailsDto",
        classes = @ConstructorResult(targetClass = GameLibraryDetailsDto.class,
                columns = {
                        @ColumnResult(name = "id", type = Long.class),
                        @ColumnResult(name = "appId", type = Long.class),
                        @ColumnResult(name = "name", type = String.class),
                        @ColumnResult(name = "headerImage", type = String.class)}))

@Entity
@Table(name = "steam_games")
public class SteamGame extends BusinessObject {
    private Long steamAppId;
    private Boolean success;
    private String name;
    private String requiredAge;
    private Boolean isFree;
    private String headerImage;
    private String website;
    private String price;
    private String developers;
    private String publishers;
    private String platforms;
    private String genres;
    private String metacritic;

    @Column(length = 10000)
    private String screenshots;

    @Column(length = 10000)
    private String detailedDescription;

    @Column(length = 10000)
    private String aboutTheGame;

    @Column(length = 1000)
    private String shortDescription;

    @Column(length = 1000)
    private String supportedLanguages;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "ownedGames")
    private Set<User> users;

    public SteamGame() {
    }

    public SteamGame(Long steamAppId, Boolean success, String name, String requiredAge, Boolean isFree, String headerImage, String website, String price, String developers, String publishers, String platforms, String genres, String metacritic, String screenshots, String detailedDescription, String aboutTheGame, String shortDescription, String supportedLanguages) {
        this.steamAppId = steamAppId;
        this.success = success;
        this.name = name;
        this.requiredAge = requiredAge;
        this.isFree = isFree;
        this.headerImage = headerImage;
        this.website = website;
        this.price = price;
        this.developers = developers;
        this.publishers = publishers;
        this.platforms = platforms;
        this.genres = genres;
        this.metacritic = metacritic;
        this.screenshots = screenshots;
        this.detailedDescription = detailedDescription;
        this.aboutTheGame = aboutTheGame;
        this.shortDescription = shortDescription;
        this.supportedLanguages = supportedLanguages;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public String getPlatforms() {
        return platforms;
    }

    public void setPlatforms(String platforms) {
        this.platforms = platforms;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getMetacritic() {
        return metacritic;
    }

    public void setMetacritic(String metacritic) {
        this.metacritic = metacritic;
    }

    public String getScreenshots() {
        return screenshots;
    }

    public void setScreenshots(String screenshots) {
        this.screenshots = screenshots;
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

    @Override
    public String toString() {
        return "SteamGame{" +
                "steamAppId=" + steamAppId +
                ", success=" + success +
                ", name='" + name + '\'' +
                ", requiredAge='" + requiredAge + '\'' +
                ", isFree=" + isFree +
                ", headerImage='" + headerImage + '\'' +
                ", website='" + website + '\'' +
                ", price='" + price + '\'' +
                ", developers='" + developers + '\'' +
                ", publishers='" + publishers + '\'' +
                ", platforms='" + platforms + '\'' +
                ", genres='" + genres + '\'' +
                ", metacritic='" + metacritic + '\'' +
                ", screenshots='" + screenshots + '\'' +
                ", detailedDescription='" + detailedDescription + '\'' +
                ", aboutTheGame='" + aboutTheGame + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", supportedLanguages='" + supportedLanguages + '\'' +
                ", users=" + users +
                '}';
    }
}
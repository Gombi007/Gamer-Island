package com.gameisland.models.entities;

import com.gameisland.models.dto.GameLibraryDetailsDto;
import lombok.*;
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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}
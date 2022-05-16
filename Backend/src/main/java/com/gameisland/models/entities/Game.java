package com.gameisland.models.entities;

import javax.persistence.*;
import java.util.Set;

@Entity()
@Table(name = "games")
public class Game extends BusinessObject {
    private Long steamAppId;
    private Boolean success;
    private String name;
    private String requiredAge;
    private Boolean isFree;
    @Column(length = 5000)
    private String detailedDescription;
    @Column(length = 5000)
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
    @JoinTable(name = "game_price_join",
            joinColumns =
                    {@JoinColumn(name = "game_id", referencedColumnName = "id")},
            inverseJoinColumns =
                    {@JoinColumn(name = "game_prices_id", referencedColumnName = "id")})
    private GamePrice gamePrice;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "game_platform_join",
            joinColumns =
                    {@JoinColumn(name = "game_id", referencedColumnName = "id")},
            inverseJoinColumns =
                    {@JoinColumn(name = "game_platform_id", referencedColumnName = "id")})
    private GamePlatform gamePlatform;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "game_screenshot_join",
            joinColumns =
                    {@JoinColumn(name = "game_id", referencedColumnName = "id")},
            inverseJoinColumns =
                    {@JoinColumn(name = "game_screenshot_id", referencedColumnName = "id")})
    private Set<GameScreenshot> gameScreenshots;


    public Game() {
    }

    public Game(Long steamAppId, Boolean success, String name, String requiredAge, Boolean isFree, String detailedDescription, String aboutTheGame, String shortDescription, String supportedLanguages, String headerImage, String website, String developers, String publishers, GamePrice gamePrice, GamePlatform gamePlatform, Set<GameScreenshot> gameScreenshots) {
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
        this.gameScreenshots = gameScreenshots;
    }

    @Override
    public String toString() {
        return "Game{" +
                "steamAppId=" + steamAppId +
                ", success=" + success +
                ", name='" + name + '\'' +
                ", requiredAge='" + requiredAge + '\'' +
                ", isFree=" + isFree +
                ", detailedDescription='" + detailedDescription + '\'' +
                ", aboutTheGame='" + aboutTheGame + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", supportedLanguages='" + supportedLanguages + '\'' +
                ", headerImage='" + headerImage + '\'' +
                ", website='" + website + '\'' +
                ", developers='" + developers + '\'' +
                ", publishers='" + publishers + '\'' +
                ", gamePrice=" + gamePrice +
                ", gamePlatform=" + gamePlatform +
                ", gameScreenshots=" + gameScreenshots +
                '}';
    }
}

package com.gameisland.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity()
@Table(name = "game_metacritics")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class GameMetacritic extends BusinessObject {
    private Long steamAppId;
    private String score;
    private String url;

    @OneToOne(mappedBy = "gameMetacritic")
    private Game game;

    public GameMetacritic() {
    }

    public GameMetacritic(Long steamAppId, String score, String url) {
        this.steamAppId = steamAppId;
        this.score = score;
        this.url = url;
    }

    public Long getSteamAppId() {
        return steamAppId;
    }

    public void setSteamAppId(Long steamAppId) {
        this.steamAppId = steamAppId;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

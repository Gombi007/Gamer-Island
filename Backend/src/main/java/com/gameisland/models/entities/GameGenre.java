package com.gameisland.models.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity()
@Table(name = "game_genres")
public class GameGenre extends BusinessObject {
    private Long steamAppId;
    private String description;

    @ManyToOne()
    @JoinColumn(name = "game_id")
    private Game game;

    public GameGenre() {
    }


    public GameGenre(Long steamAppId, String description) {
        this.steamAppId = steamAppId;
        this.description = description;
    }

    public Long getSteamAppId() {
        return steamAppId;
    }

    public void setSteamAppId(Long steamAppId) {
        this.steamAppId = steamAppId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

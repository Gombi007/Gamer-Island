package com.gameisland.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity()
@Table(name = "game_screenshots")
public class GameScreenshot extends BusinessObject {
    private Long steamAppId;
    private String pathThumbnail;
    private String pathFull;

    @ManyToOne()
    @JoinColumn(name = "game_id")
    private Game game;

    public GameScreenshot() {
    }

    public GameScreenshot(Long steamAppId, String pathThumbnail, String pathFull) {
        this.steamAppId = steamAppId;
        this.pathThumbnail = pathThumbnail;
        this.pathFull = pathFull;
    }

    public Long getSteamAppId() {
        return steamAppId;
    }

    public void setSteamAppId(Long steamAppId) {
        this.steamAppId = steamAppId;
    }

    public String getPathThumbnail() {
        return pathThumbnail;
    }

    public void setPathThumbnail(String pathThumbnail) {
        this.pathThumbnail = pathThumbnail;
    }

    public String getPathFull() {
        return pathFull;
    }

    public void setPathFull(String pathFull) {
        this.pathFull = pathFull;
    }

}

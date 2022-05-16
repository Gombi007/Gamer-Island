package com.gameisland.models.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity()
@Table(name = "game_screenshot")
public class GameScreenshot extends BusinessObject {
    private Long steamAppId;
    private String pathThumbnail;
    private String pathFull;

    @ManyToOne()
    private Game game;

    public GameScreenshot() {
    }

    public GameScreenshot(Long steamAppId, String pathThumbnail, String pathFull) {
        this.steamAppId = steamAppId;
        this.pathThumbnail = pathThumbnail;
        this.pathFull = pathFull;
    }
}

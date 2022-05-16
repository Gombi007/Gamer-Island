package com.gameisland.models.entities;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity()
@Table(name = "game_platform")
public class GamePlatform extends BusinessObject {
    private Long steamAppId;
    private Boolean windows;
    private Boolean mac;
    private Boolean linux;


    @OneToOne(mappedBy = "gamePlatform")
    private Game game;

    public GamePlatform() {
    }

    public GamePlatform(Long steamAppId, Boolean windows, Boolean mac, Boolean linux) {
        this.steamAppId = steamAppId;
        this.windows = windows;
        this.mac = mac;
        this.linux = linux;
    }
}

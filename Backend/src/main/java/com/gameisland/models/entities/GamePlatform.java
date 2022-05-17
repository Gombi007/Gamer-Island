package com.gameisland.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity()
@Table(name = "game_platforms")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

    public Long getSteamAppId() {
        return steamAppId;
    }

    public void setSteamAppId(Long steamAppId) {
        this.steamAppId = steamAppId;
    }

    public Boolean getWindows() {
        return windows;
    }

    public void setWindows(Boolean windows) {
        this.windows = windows;
    }

    public Boolean getMac() {
        return mac;
    }

    public void setMac(Boolean mac) {
        this.mac = mac;
    }

    public Boolean getLinux() {
        return linux;
    }

    public void setLinux(Boolean linux) {
        this.linux = linux;
    }
}

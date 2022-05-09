package com.gameisland.models.entities;

import javax.persistence.Entity;

@Entity
public class Game extends  BusinessObject{
    private Long appid;
    private String name;
    private String headerImg;

    public Game() {
    }

    public Game(Long appid, String name, String headerImg) {
        this.appid = appid;
        this.name = name;
        this.headerImg = headerImg;
    }

}

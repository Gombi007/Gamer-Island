package com.gameisland.models.dto;

public class GameDto {
    private Long id;
    private Long appid;
    private String name;
    private String headerImg = "https://cdn.akamai.steamstatic.com/steam/apps/";


    public GameDto(Long id, Long appid, String name) {
        this.id = id;
        this.appid = appid;
        this.name = name;
        this.headerImg += appid.toString()+"/header.jpg";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAppid() {
        return appid;
    }

    public void setAppid(Long appid) {
        this.appid = appid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeaderImg() {
        return headerImg;
    }

    public void setHeaderImg(String headerImg) {
        this.headerImg = headerImg;
    }
}

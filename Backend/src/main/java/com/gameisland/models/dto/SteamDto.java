package com.gameisland.models.dto;

public class SteamDto {
    private Long appid;
    private String name;

    public SteamDto(Long appid, String name) {
        this.appid = appid;
        this.name = name;
    }

    public SteamDto() {
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
}



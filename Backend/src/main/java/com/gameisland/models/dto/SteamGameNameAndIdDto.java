package com.gameisland.models.dto;

public class SteamGameNameAndIdDto {
    private Long appid;
    private String name;

    public SteamGameNameAndIdDto() {
    }

    public SteamGameNameAndIdDto(Long appid, String name) {
        this.appid = appid;
        this.name = name;
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

    @Override
    public String toString() {
        return "SteamGameNameAndIdDto{" +
                "appid=" + appid +
                ", name='" + name + '\'' +
                '}';
    }
}



package com.gameisland.models.dto;

import java.util.HashMap;

public class SteamGameDetailsDto {
    private String name;
    private Long steam_appid;
    private String required_age;
    private String short_description;
    private HashMap<Long, String> pictures = new HashMap<>();

    public SteamGameDetailsDto(String name, Long steam_appid, String required_age, String short_description, HashMap<Long, String> pictures) {
        this.name = name;
        this.steam_appid = steam_appid;
        this.required_age = required_age;
        this.short_description = short_description;
        this.pictures = pictures;
    }

    public SteamGameDetailsDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSteam_appid() {
        return steam_appid;
    }

    public void setSteam_appid(Long steam_appid) {
        this.steam_appid = steam_appid;
    }

    public String getRequired_age() {
        return required_age;
    }

    public void setRequired_age(String required_age) {
        this.required_age = required_age;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public HashMap<Long, String> getPictures() {
        return pictures;
    }

    public void setPictures(HashMap<Long, String> pictures) {
        this.pictures = pictures;
    }

    @Override
    public String toString() {
        return "SteamGameDetailsDto{" +
                "name='" + name + '\'' +
                ", steam_appid=" + steam_appid +
                ", required_age='" + required_age + '\'' +
                ", short_description='" + short_description + '\'' +
                ", pictures=" + pictures +
                '}';
    }
}
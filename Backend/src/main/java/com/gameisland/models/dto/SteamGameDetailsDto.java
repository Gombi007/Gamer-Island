package com.gameisland.models.dto;

public class SteamGameDetailsDto {
    private String name;
    private Long steam_appid;
    private String required_age;
    private String short_description;
    private String header_image;
    private Object screenshots;
    private Object release_date;

    public SteamGameDetailsDto() {
    }

    public SteamGameDetailsDto(String name, Long steam_appid, String required_age, String short_description, String header_image, Object screenshots, Object release_date) {
        this.name = name;
        this.steam_appid = steam_appid;
        this.required_age = required_age;
        this.short_description = short_description;
        this.header_image = header_image;
        this.screenshots = screenshots;
        this.release_date = release_date;
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

    public String getHeader_image() {
        return header_image;
    }

    public void setHeader_image(String header_image) {
        this.header_image = header_image;
    }

    public Object getScreenshots() {
        return screenshots;
    }

    public void setScreenshots(Object screenshots) {
        this.screenshots = screenshots;
    }

    public Object getRelease_date() {
        return release_date;
    }

    public void setRelease_date(Object release_date) {
        this.release_date = release_date;
    }
}
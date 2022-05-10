package com.gameisland.models.dto;

public class GameDto {
    private Long id;
    private Long appId;
    private String name;
    private String headerImg;

    public GameDto() {
    }

    public GameDto(Long id, Long appId, String name, String headerImg) {
        this.id = id;
        this.appId = appId;
        this.name = name;
        this.headerImg = headerImg;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
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

    @Override
    public String toString() {
        return "GameDto{" +
                "id=" + id +
                ", appId=" + appId +
                ", name='" + name + '\'' +
                ", headerImg='" + headerImg + '\'' +
                '}';
    }
}

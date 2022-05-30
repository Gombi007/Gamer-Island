package com.gameisland.models.dto;

public class GameLibraryDetailsDto {
    private Long id;
    private Long appId;
    private String name;
    private String headerImage;

    public GameLibraryDetailsDto(Long id, Long appId, String name, String headerImage) {
        this.id = id;
        this.appId = appId;
        this.name = name;
        this.headerImage = headerImage;
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

    public String getHeaderImage() {
        return headerImage;
    }

    public void setHeaderImage(String headerImage) {
        this.headerImage = headerImage;
    }
}

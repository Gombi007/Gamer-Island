package com.gameisland.models.dto;

public class GameLibraryDetailsDto {
    private String name;
    private String headerImage;

    public GameLibraryDetailsDto(String name, String headerImage) {
        this.name = name;
        this.headerImage = headerImage;
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

    @Override
    public String toString() {
        return "GameLibraryDetailsDto{" +
                "name='" + name + '\'' +
                ", headerImage='" + headerImage + '\'' +
                '}';
    }
}

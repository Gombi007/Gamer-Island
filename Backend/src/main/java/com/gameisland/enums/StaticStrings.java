package com.gameisland.enums;

public enum StaticStrings {

    STEAM_HEADER_IMAGES_URL_START("https://cdn.akamai.steamstatic.com/steam/apps/"),
    STEAM_HEADER_IMAGES_URL_END("/header.jpg"),
    STEAM_GAME_DETAILS_URL("https://store.steampowered.com/api/appdetails/?appids="),
    STEAM_ALL_PRODUCTS_URL("https://api.steampowered.com/ISteamApps/GetAppList/v2/"),
    PRODUCT_LIMIT("200"),
    PRODUCTS_FROM_THE_WEB("yes");

    private String url;

    StaticStrings(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }


}

package com.gameisland.enums;

public enum StaticStrings {

    STEAM_GAME_DETAILS_URL("https://store.steampowered.com/api/appdetails/?appids="),
    STEAM_ALL_PRODUCTS_URL("https://api.steampowered.com/ISteamApps/GetAppList/v2/");

    private String url;

    StaticStrings(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }


}

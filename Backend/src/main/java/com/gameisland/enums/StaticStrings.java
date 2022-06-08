package com.gameisland.enums;

public enum StaticStrings {

    STEAM_GAME_DETAILS_URL("https://store.steampowered.com/api/appdetails/?appids="),
    STEAM_ALL_PRODUCTS_URL("https://api.steampowered.com/ISteamApps/GetAppList/v2/"),
    AZURE_TOKEN_URL("https://login.microsoftonline.com/3caeff68-27dd-4cf7-85c1-a587bfbd0b4f/oauth2/v2.0/token");

    private String url;

    StaticStrings(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }


}

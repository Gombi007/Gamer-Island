package com.gameisland.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GameStatDto {
    private String badges;
    private String spaceRequired;
    private Long playTimeInSecond;
    private Timestamp lastPlayed;
    private String gameName;
    private Long gameSteamAppId;
    private String gameHeaderImage;
    private List<String> gameScreenshot_urls;
    private List<String> gameGenres;
    private String gamePlatforms;
}

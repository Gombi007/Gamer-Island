package com.gameisland.services;

import com.gameisland.models.dto.GameDto;
import com.gameisland.models.dto.SteamGameDetailsDto;
import com.gameisland.models.entities.Game;

import java.util.ArrayList;

public interface GameLibraryService {
    ArrayList<GameDto> getAllGamesFromSteam();

    SteamGameDetailsDto getGameDetailsByAppIdFromSteam(Long appId);

    void test();

    ArrayList<Game> getAllGamesFromDatabase();
}

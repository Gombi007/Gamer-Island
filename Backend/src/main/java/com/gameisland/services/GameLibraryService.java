package com.gameisland.services;

import com.gameisland.models.dto.GameDto;
import com.gameisland.models.dto.SteamGameDetailsDto;

import java.util.ArrayList;

public interface GameLibraryService {
    ArrayList<GameDto> getAllGames();

    SteamGameDetailsDto getGameDetailsByAppId(Long appId);
}

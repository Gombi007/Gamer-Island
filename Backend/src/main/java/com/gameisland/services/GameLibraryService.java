package com.gameisland.services;

import com.gameisland.models.dto.GameDto;
import com.gameisland.models.entities.Game;

import java.util.ArrayList;

public interface GameLibraryService {

    GameDto getGameDetailsByAppId(Long appId);

    void saveProductsInAFileViaSteamApi();

    ArrayList<Game> getAllGamesFromDatabase();

    void removeAGamePermanentlyFromTheDatabaseById(Long id);

    void saveSteamProductsFromFileDBToDatabase(Integer limit);
}

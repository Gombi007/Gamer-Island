package com.gameisland.services;

import com.gameisland.models.dto.GameDto;
import com.gameisland.models.dto.GameLibraryDetailsDto;
import com.gameisland.models.entities.Game;

import java.util.ArrayList;
import java.util.Set;

public interface GameService {

    ArrayList<Game> getAllGamesFromDatabase();

    GameDto getGameDetailsByAppId(Long appId);


    //Amdin
    void saveProductsInAFileViaSteamApi();

    void removeAGamePermanentlyFromTheDatabaseById(Long id);

    void saveSteamProductsFromFileDBToDatabase(Integer limit);

    Set<GameLibraryDetailsDto> libraryDetails();
}

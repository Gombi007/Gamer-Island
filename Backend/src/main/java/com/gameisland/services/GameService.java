package com.gameisland.services;

import com.gameisland.models.dto.GameDto;
import com.gameisland.models.entities.Game;

import java.util.ArrayList;

public interface GameService {

    ArrayList<Game> getAllGamesFromDatabase();

    GameDto getGameDetailsByAppId(Long appId);


    //Amdin
    void saveProductsInAFileViaSteamApi();

    void removeAGamePermanentlyFromTheDatabaseById(Long id);

    void saveSteamProductsFromFileDBToDatabase(Integer limit);
}

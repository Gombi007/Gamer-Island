package com.gameisland.services;

import com.gameisland.models.dto.GameDto;
import com.gameisland.models.dto.GameLibraryDetailsDto;
import com.gameisland.models.entities.Game;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.Set;

public interface GameService {

    Page<GameDto> getAllGamesFromDatabaseAndConvertDto(int page, int size);

    GameDto getGameDetailsByAppId(Long appId);

    //Amdin
    void saveProductsInAFileViaSteamApi();

    void removeAGamePermanentlyFromTheDatabaseById(Long id);

    void saveSteamProductsFromFileDBToDatabase(Integer limit);

    Set<GameLibraryDetailsDto> libraryDetails();
}

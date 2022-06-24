package com.gameisland.services;

import com.gameisland.models.dto.GameLibraryDetailsDto;
import com.gameisland.models.dto.SteamGameDTO;
import org.springframework.data.domain.Page;

import java.util.Set;

public interface SteamGameService {

    Page<SteamGameDTO> getAllGamesFromDatabaseAndConvertDto(int page, int size);

    SteamGameDTO getGameDetailsByAppId(Long appId);

    public void saveProductsInAFileViaSteamApi();

    void saveSteamProductsFromFileDBToDatabase(Integer limit);

    void removeAGamePermanentlyFromTheDatabaseById(Long id);

    Set<GameLibraryDetailsDto> libraryDetails();

    Set<SteamGameDTO> getAllCartGames(Long[] steamAppIds);
}

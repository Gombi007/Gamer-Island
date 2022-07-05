package com.gameisland.services;

import com.gameisland.models.dto.SteamGameDTO;
import org.springframework.data.domain.Page;

import java.util.Map;
import java.util.Set;

public interface SteamGameService {

    Page<SteamGameDTO> getGamesByNameOrGenreOrDescriptionAndConvertDto(int page, int size, String attribute, String attributeVale);

    SteamGameDTO getGameDetailsByAppId(Long appId);

    public void saveProductsInAFileViaSteamApi();

    void saveSteamProductsFromFileDBToDatabase(Integer limit);

    void removeAGamePermanentlyFromTheDatabaseById(Long id);

    Set<SteamGameDTO> getAllCartGames(Long[] steamAppIds);

    Set<String> getAllGenres();

    Map<String, Double> getMinAndMaxPrice();
}

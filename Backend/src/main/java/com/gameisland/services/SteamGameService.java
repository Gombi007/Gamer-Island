package com.gameisland.services;

import com.gameisland.models.dto.SteamGameDTO;
import org.springframework.data.domain.Page;

import java.util.Map;
import java.util.Set;

public interface SteamGameService {

    SteamGameDTO getGameDetailsByAppId(Long appId);

    Set<SteamGameDTO> getAllCartGames(Long[] steamAppIds);

    Set<String> getAllGenres();

    Map<String, Double> getMinAndMaxPrice();

    Page<SteamGameDTO> getGamesByNameOrGenreOrDescriptionAndConvertDto(int page, int size, String attribute, String attributeVale);

    void saveProductsInAFileViaSteamApi();

    void saveSteamProductsFromFileDBToDatabase(Integer limit);

    Map<String, String> removeAGamePermanentlyFromTheDatabaseByAppId(Long id);


}

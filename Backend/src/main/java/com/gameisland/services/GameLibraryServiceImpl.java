package com.gameisland.services;

import com.gameisland.models.dto.GameDto;
import com.gameisland.models.dto.SteamGameDetailsDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class GameLibraryServiceImpl implements GameLibraryService {
    @Override
    public ArrayList<GameDto> getAllGamesFromSteam() {
        SteamApiAllProductsService data = new SteamApiAllProductsService();
        return data.getAllGameFromSteam();
    }

    @Override
    public SteamGameDetailsDto getGameDetailsByAppIdFromSteam(Long appId) {
        SteamApiDetailService data = new SteamApiDetailService();
        return data.getGameDetailsByAppId(appId);
    }
}

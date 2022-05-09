package com.gameisland.services;

import com.gameisland.models.dto.GameDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class GameLibraryServiceImpl implements GameLibraryService {
    @Override
    public ArrayList<GameDto> getAllGames() {
        SteamApiService data = new SteamApiService();
        return data.getAllGameFromSteam();
    }
}

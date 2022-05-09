package com.gameisland.services;

import com.gameisland.models.dto.GameDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class GameLibraryServiceImpl implements GameLibraryService {
    @Override
    public ArrayList<GameDto> getAllGames() {
        ArrayList<GameDto> gameDto = new ArrayList<>();
        gameDto.add(new GameDto(1L, 1234L, "Cyberpunk 2077", "https://cdn.akamai.steamstatic.com/steam/apps/1091500/header.jpg"));
        return gameDto;
    }
}

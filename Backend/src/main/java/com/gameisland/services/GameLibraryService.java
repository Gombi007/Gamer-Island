package com.gameisland.services;

import com.gameisland.models.dto.GameDto;

import java.util.ArrayList;

public interface GameLibraryService {
    ArrayList<GameDto> getAllGames();
}

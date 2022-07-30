package com.gameisland.services;

import com.gameisland.models.dto.GameStatDto;

import java.util.List;

public interface GameStatService {
    GameStatDto getGameStat(String uuid, Long steamAppId);

    List<GameStatDto> getUserAllGameStats(String uuid);
}

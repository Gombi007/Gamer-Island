package com.gameisland.services;

import com.gameisland.models.dto.GameStatDto;

public interface GameStatService {
    GameStatDto getGameStat(String uuid, Long steamAppId);
}

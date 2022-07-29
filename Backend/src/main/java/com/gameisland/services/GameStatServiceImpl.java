package com.gameisland.services;

import com.gameisland.models.dto.GameStatDto;
import com.gameisland.models.dto.SteamGameDTO;
import com.gameisland.models.entities.GameStat;
import com.gameisland.models.entities.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Set;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class GameStatServiceImpl implements GameStatService {
    private final UserService userService;
    private final SteamGameService steamGameService;

    @Override
    public GameStatDto getGameStat(String uuid, Long steamAppId) {
        User user = userService.getUserByUUID(uuid);
        Set<GameStat> gameStatSet = user.getGameStats();
        GameStatDto gameStatDto = new GameStatDto();

        for (GameStat gameStat : gameStatSet) {
            if (Objects.equals(gameStat.getSteamAppId(), steamAppId)) {
                SteamGameDTO steamGameDTO = steamGameService.getGameDetailsByAppId(steamAppId);
                gameStatDto.setGameGenres(steamGameDTO.getGenres());
                gameStatDto.setGameName(steamGameDTO.getName());
                gameStatDto.setGameSteamAppId(steamGameDTO.getSteam_appid());
                gameStatDto.setGamePlatforms(steamGameDTO.getPlatforms());
                gameStatDto.setGameScreenshot_urls(steamGameDTO.getScreenshot_urls());
                gameStatDto.setGameHeaderImage(steamGameDTO.getHeader_image());

                gameStatDto.setLastPlayed(gameStat.getLastPlayed());
                gameStatDto.setPlayTimeInSecond(gameStat.getPlayTimeInSecond());
                gameStatDto.setSpaceRequired(gameStat.getSpaceRequired());
                gameStatDto.setBadges(gameStat.getBadges());
            }

        }
        return gameStatDto;
    }
}

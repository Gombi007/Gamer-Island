package com.gameisland.services;

import com.gameisland.exceptions.ResourceNotFoundException;
import com.gameisland.models.dto.GameDto;
import com.gameisland.models.entities.Game;
import com.gameisland.repositories.GamePriceRepository;
import com.gameisland.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class GameLibraryServiceImpl implements GameLibraryService {
    private final GameRepository gameRepository;
    private final GamePriceRepository gamePriceRepository;
    private final SteamApiAllProductsService steamApiAllProductsService;
    private final SteamApiDetailService steamApiDetailService;

    @Autowired
    public GameLibraryServiceImpl(GameRepository gameRepository, GamePriceRepository gamePriceRepository, SteamApiAllProductsService steamApiAllProductsService, SteamApiDetailService steamApiDetailService) {
        this.gameRepository = gameRepository;
        this.gamePriceRepository = gamePriceRepository;
        this.steamApiAllProductsService = steamApiAllProductsService;
        this.steamApiDetailService = steamApiDetailService;
    }

    @Override
    public ArrayList<Game> getAllGamesFromDatabase() {
        Boolean isEmptyDatabase = gameRepository.findAll().isEmpty();
        if (!isEmptyDatabase) {
            return gameRepository.findAll();
        }
        return new ArrayList<>();
    }

    @Override
    public GameDto getGameDetailsByAppId(Long appId) {
        if (appId != null) {
            try {
                Game game = gameRepository.gameByAppId(appId).orElseThrow();
                return GameDto.convertToGameDto(game);
            } catch (NoSuchElementException exception) {
                throw new ResourceNotFoundException("No game in the database with this App ID: " + appId);
            }
        }
        throw new ResourceNotFoundException("App ID mustn't be NULL: " + appId);
    }


    // Services for admin only
    @Override
    public void saveProductsInAFileViaSteamApi() {
        steamApiAllProductsService.getAllSteamProducts();

    }

    @Override
    public void saveSteamProductsFromFileDBToDatabase(Integer limit) {
        Set<Long> existingAppIds = gameRepository.allExistingSteamAppId();
        Set<Long> appids = steamApiAllProductsService.getAllSteamAppIdFromFileDB(limit, existingAppIds);

        Iterator<Long> iterator = appids.iterator();
        int counter = 1;
        while (iterator.hasNext()) {
            Game game = steamApiDetailService.saveAGameIntoTheDatabase(iterator.next());
            if (game != null) {
                System.out.println(counter + ". Game saved: " + game.getName());
                gameRepository.save(game);
                counter++;
            }
        }

    }

    @Override
    @Transactional
    public void removeAGamePermanentlyFromTheDatabaseById(Long id) {
        boolean isExistingGame = gameRepository.existsById(id);
        if (isExistingGame) {
            Game game = gameRepository.findById(id).get();
            gameRepository.deleteUserGameEntriesByGameId(game.getId());
            gameRepository.delete(game);

        }

    }


}

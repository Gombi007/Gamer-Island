package com.gameisland.services;

import com.gameisland.models.entities.Game;
import com.gameisland.repositories.GamePriceRepository;
import com.gameisland.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
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
    public Game getGameDetailsByAppId(Long appId) {
        if (appId != null) {
            return gameRepository.gameByAppId(appId).get();
        }
        return null;
    }

    @Override
    public void saveProductsInAFileViaSteamApi() {
        steamApiAllProductsService.getAllSteamProducts();

    }

    public void saveSteamProductsFromFileDBToDatabase(Integer limit) {
        Set<Long> appids = steamApiAllProductsService.getAllSteamAppIdFromFileDB(limit);
        Set<Long> existingAppIds = gameRepository.allExistingSteamAppId();

        appids.removeAll(existingAppIds);

        Iterator<Long> iterator = appids.iterator();
        int counter = 1;
        while (iterator.hasNext()) {
            Game game = steamApiDetailService.saveAGameIntoTheDatabase(iterator.next());
            if (game != null) {
                System.out.println("Game saved: " + counter);
                gameRepository.save(game);
                counter++;
            }
        }

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
    public void removeAGamePermanentlyFromTheDatabaseById(Long id) {
        boolean isExistingGame = gameRepository.existsById(id);
        if (isExistingGame) {
            Game game = gameRepository.findById(id).get();
            gameRepository.delete(game);

        }

    }


}

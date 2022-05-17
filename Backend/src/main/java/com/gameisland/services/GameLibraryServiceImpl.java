package com.gameisland.services;

import com.gameisland.models.dto.SteamGameDetailsDto;
import com.gameisland.models.entities.Game;
import com.gameisland.repositories.GamePriceRepository;
import com.gameisland.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class GameLibraryServiceImpl implements GameLibraryService {
    private final GameRepository gameRepository;
    private final GamePriceRepository gamePriceRepository;

    @Autowired
    public GameLibraryServiceImpl(GameRepository gameRepository, GamePriceRepository gamePriceRepository) {
        this.gameRepository = gameRepository;
        this.gamePriceRepository = gamePriceRepository;
    }


    @Override
    public SteamGameDetailsDto getGameDetailsByAppIdFromSteam(Long appId) {
        return null;
    }

    @Override
    public void test() {
        SteamApiAllProductsService data = new SteamApiAllProductsService();
        SteamApiDetailService detail = new SteamApiDetailService();
        ArrayList<Long> appids = data.getAllSteamProducts();
        ArrayList<Long> existingAppIds = gameRepository.allExistingSteamAppId();
        for (int i = 0; i < existingAppIds.size(); i++) {
            if (appids.contains(existingAppIds.get(i))) {
                appids.remove(existingAppIds.get(i));
                System.out.println("SAME ID: " + existingAppIds.get(i));
            }

        }

        int counter = 0;

        for (int i = 0; i < appids.size(); i++) {
            Game game = detail.saveAGameIntoTheDatabase(appids.get(i));
            if (game != null) {
                counter++;
                System.out.println("Game saved: " + counter);
                gameRepository.save(game);
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
    public void remove(Long id) {
        boolean isExistingGame = gameRepository.existsById(id);
        if (isExistingGame) {
            Game game = gameRepository.findById(id).get();
            gameRepository.delete(game);

        }

    }


}

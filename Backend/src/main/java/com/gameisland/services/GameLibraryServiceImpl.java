package com.gameisland.services;

import com.gameisland.models.dto.GameDto;
import com.gameisland.models.dto.SteamGameDetailsDto;
import com.gameisland.models.entities.Game;
import com.gameisland.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class GameLibraryServiceImpl implements GameLibraryService {
    private final GameRepository gameRepository;

    @Autowired
    public GameLibraryServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public ArrayList<GameDto> getAllGamesFromSteam() {
        SteamApiAllProductsService data = new SteamApiAllProductsService();
        return data.getAllGameFromSteam();
    }

    @Override
    public SteamGameDetailsDto getGameDetailsByAppIdFromSteam(Long appId) {
        return null;
    }

    @Override
    public void test() {
        SteamApiAllProductsService data = new SteamApiAllProductsService();
        SteamApiDetailService detail = new SteamApiDetailService();
        ArrayList<Long> appids = data.getProducts();

        for (int i = 0; i < appids.size(); i++) {
            Game game = detail.saveAGameIntoTheDatabase(appids.get(i));
            System.out.println(game);
            if (game != null) {
                gameRepository.save(game);
            }
        }


    }

}

package com.gameisland.services;

import com.gameisland.exceptions.ResourceNotFoundException;
import com.gameisland.models.dto.GameDto;
import com.gameisland.models.dto.GameLibraryDetailsDto;
import com.gameisland.models.entities.Game;
import com.gameisland.repositories.GamePriceRepository;
import com.gameisland.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final GamePriceRepository gamePriceRepository;
    private final SteamApiAllProductsService steamApiAllProductsService;
    private final SteamApiDetailService steamApiDetailService;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, GamePriceRepository gamePriceRepository, SteamApiAllProductsService steamApiAllProductsService, SteamApiDetailService steamApiDetailService) {
        this.gameRepository = gameRepository;
        this.gamePriceRepository = gamePriceRepository;
        this.steamApiAllProductsService = steamApiAllProductsService;
        this.steamApiDetailService = steamApiDetailService;
    }

    @Override
    public Page<GameDto> getAllGamesFromDatabaseAndConvertDto(int page, int size) {
        Boolean isEmptyDatabase = gameRepository.findAll().isEmpty();
        if (!isEmptyDatabase) {

            Sort sort = Sort.by(Sort.Direction.ASC, "name");
            PageRequest pageRequest = PageRequest.of(page, size, sort);
            Page<Game> sortedAndPagedGames = gameRepository.findAll(pageRequest);
            List<Game> gamesInaPage = sortedAndPagedGames.getContent();


            ArrayList<GameDto> concertToDto = new ArrayList<>();
            for (int i = 0; i < gamesInaPage.size(); i++) {
                concertToDto.add(GameDto.convertToGameDtoForShop(gamesInaPage.get(i)));
            }

            Page<GameDto> resultPageWithDto = new PageImpl<>(concertToDto, sortedAndPagedGames.getPageable(), sortedAndPagedGames.getTotalPages());


            return resultPageWithDto;


        }
        throw new ResourceNotFoundException("Empty database");
    }

    @Override
    public GameDto getGameDetailsByAppId(Long appId) {
        if (appId != null) {
            try {
                Game game = gameRepository.gameByAppId(appId).get();
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
        steamApiAllProductsService.removeAllSavedAndNonGameElementFromSteamAppJson();

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

    @Override
    public Set<GameLibraryDetailsDto> libraryDetails() {
        boolean isEmptyGameSet = gameRepository.getLibraryDetails().isEmpty();
        if (!isEmptyGameSet) {
            return gameRepository.getLibraryDetails();
        }
        throw new ResourceNotFoundException("This user has not any game yet");
    }


}

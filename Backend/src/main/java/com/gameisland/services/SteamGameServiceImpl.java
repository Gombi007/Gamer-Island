package com.gameisland.services;

import com.gameisland.exceptions.ResourceNotFoundException;
import com.gameisland.models.dto.SteamGameDTO;
import com.gameisland.models.entities.SteamGame;
import com.gameisland.repositories.SteamGameRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
public class SteamGameServiceImpl implements SteamGameService {
    private final SteamApiAllProductsService steamApiAllProductsService;
    private final SteamApiDetailService steamApiDetailService;
    private final SteamGameRepository steamGameRepository;

    @Autowired
    public SteamGameServiceImpl(SteamApiAllProductsService steamApiAllProductsService, SteamApiDetailService steamApiDetailService, SteamGameRepository steamGameRepository) {
        this.steamApiAllProductsService = steamApiAllProductsService;
        this.steamApiDetailService = steamApiDetailService;
        this.steamGameRepository = steamGameRepository;
    }

    @Override
    public Page<SteamGameDTO> getAllGamesFromDatabaseAndConvertDto(int page, int size) {
        Boolean isEmptyDatabase = steamGameRepository.findAll().isEmpty();
        if (!isEmptyDatabase) {

            Sort sort = Sort.by(Sort.Direction.ASC, "name");
            PageRequest pageRequest = PageRequest.of(page, size, sort);
            Page<SteamGame> sortedAndPagedGames = steamGameRepository.findAll(pageRequest);
            List<SteamGame> gamesInaPage = sortedAndPagedGames.getContent();

            ArrayList<SteamGameDTO> concertToDto = new ArrayList<>();
            for (int i = 0; i < gamesInaPage.size(); i++) {
                concertToDto.add(SteamGameDTO.convertToGameDto(gamesInaPage.get(i)));
            }

            Page<SteamGameDTO> resultPageWithDto = new PageImpl<>(concertToDto, sortedAndPagedGames.getPageable(), sortedAndPagedGames.getTotalElements());

            return resultPageWithDto;

        }
        throw new ResourceNotFoundException("Empty database");
    }

    @Override
    public SteamGameDTO getGameDetailsByAppId(Long appId) {
        if (appId != null) {
            try {
                SteamGame game = steamGameRepository.gameByAppId(appId).get();
                return SteamGameDTO.convertToGameDto(game);
            } catch (NoSuchElementException exception) {
                throw new ResourceNotFoundException("No game in the database with this App ID: " + appId);
            }
        }
        throw new ResourceNotFoundException("App ID mustn't be NULL: " + appId);
    }

    @Override
    public Set<SteamGameDTO> getAllCartGames(Long[] steamAppIds) {
        Set<SteamGameDTO> result = new HashSet<>();

        if (steamAppIds != null && steamAppIds.length > 0) {
            for (int i = 0; i < steamAppIds.length; i++) {
                result.add(getGameDetailsByAppId(steamAppIds[i]));

            }
        }
        return result;
    }

    @Override
    public Set<String> getAllGenres() {
        Set<String> allGenresInDB = new TreeSet<>();
        List<String> genres = steamGameRepository.allGenres();
        for (int i = 0; i < genres.size(); i++) {
            System.out.println(genres.get(i));
            String[] tmp = genres.get(i).split(";");
            for (int j = 0; j < tmp.length; j++) {
                allGenresInDB.add(tmp[j]);
            }
        }
        return allGenresInDB;
    }

    // Services for admin only
    @Override
    public void saveProductsInAFileViaSteamApi() {
        steamApiAllProductsService.getAllSteamProducts();

    }

    @Override
    public void saveSteamProductsFromFileDBToDatabase(Integer limit) {
        Set<Long> existingAppIds = steamGameRepository.allExistingSteamAppId();
        Set<Long> appids = steamApiAllProductsService.getAllSteamAppIdFromFileDB(limit, existingAppIds);

        Iterator<Long> iterator = appids.iterator();
        int counter = 1;
        while (iterator.hasNext()) {
            SteamGame game = steamApiDetailService.saveSteamGamesIntoTheDatabase(iterator.next());
            if (game != null) {
                steamGameRepository.save(game);
                log.warn("{}. Game saved: {}", counter, game.getName());
                counter++;
            }
        }
        steamApiAllProductsService.removeAllSavedAndNonGameElementFromSteamAppJson();

    }

    @Override
    @Transactional
    public void removeAGamePermanentlyFromTheDatabaseById(Long id) {
        boolean isExistingGame = steamGameRepository.existsById(id);
        if (isExistingGame) {
            SteamGame game = steamGameRepository.findById(id).get();
            steamGameRepository.deleteUserGameEntriesByGameId(game.getId());
            steamGameRepository.delete(game);
        }
    }


}
